package mdc.ws;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import mdc.client.update.UpdateDocList;
import mdc.client.update.UpdateFapTable;
import mdc.client.update.UpdatePatientTable;
import mdc.client.update.UpdateServerInfo;
import mdc.client.update.UpdateServiceParams;
import mdc.db.connect.StartDB;
import mdc.engine.ReadConfigXml;
import mdc.gui.JFrame;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

public class TisClient {
    private static final Logger log = Logger.getLogger((Class)TisClient.class);
    private ReadConfigXml config = new ReadConfigXml();
    private UpdateServiceParams service;
    private String username;
    private String password;
    private String cookie = "";
    private UpdatePatientTable patient;
    private UpdateServerInfo serverInfo;
    private Connection con;
    private UpdateFapTable clinic;
    private UpdateDocList doctor;
    private GetResultsForTisServer result;
    private String gmt;
    private String uuidDevice;
    private int interval = 100;
    Timer t;
    private JFrame frame;

    public TisClient(Connection con, JFrame frame) {
        this.config.parse();
        this.con = con;
        this.username = this.config.getTisUser();
        this.password = this.config.getTisPassword();
        this.frame = frame;
        this.patient = new UpdatePatientTable();
        this.serverInfo = new UpdateServerInfo();
        this.service = new UpdateServiceParams();
        this.clinic = new UpdateFapTable();
        this.doctor = new UpdateDocList();
        this.result = new GetResultsForTisServer();
        frame.prg.setVisible(true);
        frame.jPrg.setValue(0);
        frame.jPrg.setMinimum(0);
    }

    public TisClient() {
    }

    public String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Cookie", this.cookie);
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter((OutputStream)wr, "UTF-8"));
            writer.write(urlParameters);
            writer.close();
            wr.close();
            String headerName = null;
            int i = 1;
            while ((headerName = connection.getHeaderFieldKey(i)) != null) {
                if (headerName.equals("set-cookie")) {
                    this.cookie = connection.getHeaderField(i);
                    this.cookie = this.cookie.substring(0, this.cookie.indexOf(";"));
                }
                ++i;
            }
            BufferedInputStream responseStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader((InputStream)responseStream, "UTF-8"));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = responseStreamReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            responseStreamReader.close();
            String response = stringBuilder.toString();
//            JSONObject jsonObj = new JSONObject(response);
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            String rJsonOBJ = gson.toJson((Object)jsonObj);
            String string = response;
            return string;
        }
        catch (IOException e) {
            log.info((Object)e, (Throwable)e);
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при обмене данными  с сервером.Повторите попытку.");
            this.frame.prg.setVisible(false);
            String wr = null;
            return wr;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void login() throws ParseException, JSONException {
        String auth = this.config.getLoginURL();
        String loginMsg = "{ \"login\" : \"" + this.username + "\" , \"password\" : \"" + this.password + "\" }";
        this.parseResponce(this.executePost(auth, loginMsg));
    }

    private void getClinic() {
        String getClinic = this.config.getGetClinicURL();
        String clinicCode = this.config.getCrbID().split("\\.")[0];
        String msg2 = "{\"code\" : \"" + clinicCode + "\"}";
        String res = this.executePost(getClinic, msg2);
        try {
            this.parseClinicJSON(res);
        }
        catch (JSONException | ParseException e) {
            log.info((Object)e, (Throwable)e);
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при обмене данными  с сервером.Повторите попытку.");
            this.frame.prg.setVisible(false);
        }
    }

    private void getPatient() {
        String getPatient = this.config.getGetPatientURL();
        String clinicCode = this.config.getCrbID().split("\\.")[0];
        String lastRequestDate = this.serverInfo.getLastRequireDate(this.con);
        if (this.config.getDownAllPatient().equals("true")) {
            lastRequestDate = "1970-01-01 00:00:00.000+03";
        }
        String msg2 = "{\"clinic\" : {\"name%\" : \"" + clinicCode + "\"},\"patient\" : {\"updatedAtBegin\" : \"" + lastRequestDate + "\"}, \"paging\": { \"all\": \"0\", \"current\": \"0\", \"show\": \"500\" }  }";
        String res = this.executePost(getPatient, msg2);
        String lastUpdate = "";
        try {
            JSONObject obj = new JSONObject(res);
            int length = obj.getInt("all");
            log.info((Object)length);
            this.interval = length;
            this.frame.jPrg.setMaximum(this.interval);
            int i = 0;
            while (i < length) {
                msg2 = "{\"clinic\" : {\"name%\" : \"" + clinicCode + "\"},\"patient\" : {\"updatedAtBegin\" : \"" + lastRequestDate + "\"}, \"paging\": { \"all\": \"0\", \"current\": \"" + i + "\", \"show\": \"500\" }  }";
                res = this.executePost(getPatient, msg2);
                lastUpdate = this.parsePatientsJSON(res);
                log.info((Object)("STEP " + (i += 500)));
                int percent = i;
                SwingUtilities.invokeLater(() -> {
                    this.frame.jPrg.setValue(percent);
                }
                );
            }
            this.serverInfo.updateRequireDate(this.con, lastUpdate);
            if (this.config.getDownAllPatient().equals("true")) {
                this.config.EditPAtientDownloadingFlag();
            }
        }
        catch (JSONException | ParseException e) {
            log.info((Object)e, (Throwable)e);
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при обмене данными  с сервером.Повторите попытку.");
            this.frame.prg.setVisible(false);
        }
    }

    private void getServices() {
        String getServices = this.config.getGetServiceURL();
        String msg2 = "";
        String res = this.executePost(getServices, msg2);
        try {
            this.parseServicesJSON(res);
        }
        catch (JSONException | ParseException e) {
            log.info((Object)e, (Throwable)e);
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при обмене данными  с сервером.Повторите попытку.");
            this.frame.prg.setVisible(false);
        }
    }

    private void getDoctor() {
        String getDoctor = this.config.getGetDoctorURL();
        String clinicCode = this.clinic.getClinicUuid(this.con, this.config.getCrbID());
        String msg2 = "{\"doctor4clinic\" : \"" + clinicCode + "\" ,\"speciality\" :{\"code\": \"8\" } }";
        String res = this.executePost(getDoctor, msg2);
        try {
            this.parseDoctorJSON(res);
        }
        catch (JSONException | ParseException e) {
            log.info((Object)e, (Throwable)e);
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при обмене данными  с сервером.Повторите попытку.");
            this.frame.prg.setVisible(false);
        }
    }

    public boolean parseResponce(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        return obj.getBoolean("success");
    }

    private void sendResult() throws SQLException {
        String getMdkInfo = this.config.getGetMdkInfoURL();
        String mdkCode = this.config.getMdkID();
        String msg2 = "{\"number\" : \"" + mdkCode + "\"}";
        String res = this.executePost(getMdkInfo, msg2);
        String url = this.config.getPutResultURL();
        try {
            this.parseMdkInfoJSON(res);
            this.result.writeXML(this.con, this, url, this.gmt, this.uuidDevice,this.config.getCrbID().split("\\.")[0]);
        }
        catch (JSONException | ParseException e) {
            this.frame.jPrg.setValue(this.interval);
            JOptionPane.showMessageDialog(null, "Ошибка при отправке результатов. Повторите попытку.");
            this.frame.prg.setVisible(false);
        }
    }

    private void parseMdkInfoJSON(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("rows");
        for (int i = 0; i < arr.length(); ++i) {
            this.gmt = arr.getJSONObject(i).getString("gmt");
            this.uuidDevice = arr.getJSONObject(i).getString("uuid");
        }
    }

    private void parseDoctorJSON(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("rows");
        String crbCode = this.config.getCrbID().split("\\.")[0];
        for (int i = 0; i < arr.length(); ++i) {
            String uuid = arr.getJSONObject(i).getString("doctor.uuid");
            String surname = arr.getJSONObject(i).get("doctor.first").toString();
            String name = arr.getJSONObject(i).get("doctor.middle").toString();
            String middle = arr.getJSONObject(i).get("doctor.last").toString();
            if (surname.equals("null")) {
                surname = "";
            }
            if (name.equals("null")) {
                name = "";
            }
            if (middle.equals("null")) {
                middle = "";
            }
            String fio = surname + " " + name + " " + middle;
            this.doctor.AddNewDoctor(this.con, uuid, fio, crbCode);
            log.info((Object)("Doctor add  " + crbCode + "  --- " + fio));
        }
    }

    private void parseServicesJSON(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("rows");
        for (int i = 0; i < arr.length(); ++i) {
            String code = arr.getJSONObject(i).getString("code");
            String uuid = arr.getJSONObject(i).getString("uuid");
            String parser = arr.getJSONObject(i).getString("parser");
            service.InsertServiceUuid(con, code, uuid, parser);
        }
    }

    private void parseClinicJSON(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("rows");
        for (int i = 0; i < arr.length(); ++i) {
            String uuid = arr.getJSONObject(i).getString("uuid");
            String code = arr.getJSONObject(i).getString("code");
            String name = arr.getJSONObject(i).getString("name");
            this.clinic.updateClinicTable(this.con, uuid, code, name);
            this.clinic.updateFapTable(this.con, "", name, code);
            log.info((Object)("Clinic add  " + code + "  --- " + name));
        }
    }

    private String parsePatientsJSON(String json) throws ParseException, JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("rows");
        String lastUpdatedAt = "";
        for (int i = 0; i < arr.length(); ++i) {
            String uuid = arr.getJSONObject(i).getString("patient.uuid");
            String first = arr.getJSONObject(i).get("patient.first").toString();
            String middle = arr.getJSONObject(i).get("patient.middle").toString();
            String last = arr.getJSONObject(i).get("patient.last").toString();
            int sex = arr.getJSONObject(i).getInt("patient.sex");
            int birthday = arr.getJSONObject(i).getInt("patient.birthday");
            String clinicCode = arr.getJSONObject(i).getString("clinic.code");
            lastUpdatedAt = arr.getJSONObject(i).getString("patient.updatedAt");
            String birth = getBirthday(birthday);
            if (first.equals("null")) {
                first = "";
            }
            if (middle.equals("null")) {
                middle = "";
            }
            if (last.equals("null")) {
                last = "";
            }
            if(!birth.equals("null")) {
                this.patient.UpdatePatientTable(this.con, uuid, first, middle, last, birth, sex, clinicCode);
                log.info((Object)("Patient add  " + first + "  " + middle + "  " + last + " - "+ birth));
            }
        }
        return lastUpdatedAt;
    }

    public void run() throws JSONException, SQLException, ParseException {
        this.login();
        this.getServices();
        this.getClinic();
        this.getDoctor();
        this.getPatient();
        this.sendResult();
        this.frame.jPrg.setValue(this.interval);
        JOptionPane.showMessageDialog(null, "Обновление с сервером завершено.");
        this.frame.prg.setVisible(false);
    }

    public static void main(String[] args) throws JSONException, SQLException, ParseException {
        StartDB start = new StartDB();
        start.Start();
        TisClient auth = new TisClient(start.getConnection(), null);
        auth.run();
    }

    private String getBirthday(int birthday) {
        String date = String.valueOf(birthday);
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        if( checkAge(Integer.parseInt(year))) return day + "." + month + "." + year;
        return "null";
    }
    
    private static boolean checkAge(int birthYear){
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        int curYear = calendar.get(java.util.Calendar.YEAR);
        int age = curYear - birthYear;
        int[] arr = {21,24,27,30,33,36,39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99};
        for (int n : arr) {
            if (age == n) {
               return true;
            }
        }
        return false;
    }
}
