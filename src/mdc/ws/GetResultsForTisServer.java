package mdc.ws;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import mdc.client.GetResultsForMdcServer;
import mdc.db.connect.StartDB;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;



public class GetResultsForTisServer {
    private ArrayList<String> idList; 
    private JSONObject json ;
    private static final Logger log = Logger.getLogger(GetResultsForMdcServer.class);
    private ReadConfigXml config;
    
    public GetResultsForTisServer(){
        idList = new ArrayList();
        config = new ReadConfigXml();
    }
    
    public ArrayList<String> getIdList() {
        return idList;
    }
    
    
    public void GetDataFromDB(Connection conn){
        String query = "select distinct x.id from tis_xml_view x where x.on_server = 'not'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = conn.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()){
               idList.add(rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        } 
    }
    
    public void writeXML(Connection conn, TisClient client,String url, String gmt,String uuidDevice,String clinic) throws SQLException {
        GetDataFromDB(conn);
        PreparedStatement post; 
        ResultSet rs;
        if(idList.isEmpty()) return;
        try {
            for (int i = 0; i < idList.size(); i++) {
                json = new JSONObject();
                String query = "select *from tis_xml_view  x where x.id = '"+idList.get(i)+"'";
                int flag = 0;
                post = conn.prepareStatement(query);
                rs = post.executeQuery();
                while(rs.next()){ 
                    if(flag == 0){
                        json.put("uuidPatient", rs.getString("tis_id"));
                        json.put("uuidUnion", rs.getString("eventid").toLowerCase());                     
                        String oldstring = rs.getString("date");
                        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(oldstring);
                        long millis = date.getTime();
                        json.put("date",millis );
                        json.put("type","Диспансеризация");
                        json.put("parser","tis.clinicalexamination");
                        json.put("state","registered");
                        json.put("datePerform",millis);
                        json.put("gmt",gmt);
                        json.put("mkb10","Z00.0");
                        json.put("uuidAuthor",rs.getString("author"));
                        json.put("uuidClinic",getClinicUuid(conn, clinic));
                        String performer = rs.getString("performer");
                        if(rs.wasNull()){ 
                             performer = "null";
                        }
                        json.put("uuidPerformer",performer);
                        json.put("uuidDevice",uuidDevice);  
                        client.executePost(url, json.toString());
                        flag = 1;
                    }
                    json.put("uuidPatient", rs.getString("tis_id"));
                    json.put("uuidUnion", rs.getString("eventid").toLowerCase());
                    json.put("uuid", rs.getString("research"));                       
                    String oldstring = rs.getString("date");
                    Date date = new SimpleDateFormat("dd.MM.yyyy").parse(oldstring);
                    long millis = date.getTime();
                    json.put("date",millis );
                    json.put("parser",getServiceParser(conn, rs.getString("service_code")));
                    json.put("type",getServiceType(conn, rs.getString("service_code")));
                    json.put("state",getState(rs.getString("state")));
                    json.put("datePerform",millis);
                    json.put("gmt",gmt);
                    json.put("mkb10","Z00.0");
                    json.put("uuidService",getServiceUuid(conn,rs.getString("service_code")));
                    json.put("uuidAuthor",rs.getString("author"));
                    json.put("uuidClinic",getClinicUuid(conn, clinic));
                    String performer = rs.getString("performer");
                    if(rs.wasNull()){ 
                         performer = "null";
                    }
                    json.put("uuidPerformer",performer);
                    json.put("uuidDevice",uuidDevice);
                      
                    if(getServiceUuid(conn, rs.getString("service_code")).equals("00000000-0000-0000-0000-000000000006"))
                        sendEcg(rs.getBytes("ecg_bin"), rs.getString("tis_id"));
                    if(!getServiceUuid(conn, rs.getString("service_code")).equals("00000000-0000-0000-0000-000000000006")){
                        Object result = rs.getObject("rezult");           
                        if(result != null){
                            json.put("test",createResult(conn,rs.getString("rezult"),rs.getString("service_code"))); 
                        } 
                        client.executePost(url, json.toString());
                    }
                        
                                     
                }   
                //updateStatus(conn,idList.get(i));
            }                               
        }catch (SQLException | JSONException | ParseException pce) {
            log.info(pce, pce);
        }
    }
    
    private String getState(String state){
        switch(state){
            case "Назначено" : return "registered";
            case "Выполнено" : return "performed";
            case "Выполнено ранее" : return "earlie performed";
            case "Отказ" : return "refused";
        }
        return null;
    }
    
    private String getServiceUuid(Connection con,String code){
        String query = "select service_uuid from service  where service_code = '"+code+"' ";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()) {
                return rs.getString(1);                
              }
            return null;
        }catch (SQLException ex) {
            log.info(ex , ex);
        }
        return null;
    }
    
    private JSONObject createResult(Connection con,String result,String code){
        String service = getServiceIncode(con, code);
        String [] arr = result.split(";");
        JSONObject res = new JSONObject();
        try {
            switch(service){
                case "A001" : {
                   switch(arr[0]){
                       case "Нет" : res.put("smoke", "Нет"); break;
                       case "Да" : res.put("smoke", "Да"); break;
                       case "Курил ранее" : res.put("smoke", "Курил ранее"); break;
                   }                   
                   return res;
                }
                case "A002" : {
                    res.put("height",Integer.parseInt(arr[0]));
                    res.put("weight", Integer.parseInt(arr[2]));
                    res.put("waist", Integer.parseInt(arr[1]));
                    double w = Integer.parseInt(arr[2]);
                    double h = Integer.parseInt(arr[0]); 
                    double index =  w / ((h * h)/10000.0) ;
                    DecimalFormat df=new DecimalFormat("0.00");
                    String formate = df.format(index); 
                    try {
                        double finalValue = (Double)df.parse(formate) ;
                        res.put("index", finalValue);
                    } catch (ParseException ex) { }
                                       
                   return res;
                }
                case "A003" : {
                   res.put("top", Integer.parseInt(arr[1]));
                   res.put("lower", Integer.parseInt(arr[2]));
                   res.put("pulse", Integer.parseInt(arr[0]));
                   return res;
                }
                case "A016" : {
                   res.put("right", Integer.parseInt(arr[0]));
                   res.put("left", Integer.parseInt(arr[1]));
                   return res;
                }
                case "A013" : {
                   res.put("glucose", arr[0]);
                   res.put("protein", arr[1]);
                   res.put("ketones", arr[2]);
                   res.put("eritr", arr[3]);
                   res.put("lejk", arr[4]);
                   res.put("gravity", arr[5]);
                   res.put("pH", arr[6]);
                   res.put("urob", arr[7]);
                   res.put("ascorb", arr[8]);
                   res.put("nitrite", arr[9]);
                   res.put("bilirub", arr[10]);
                   switch(arr[11]){
                       case "соломенно-желтый" : res.put("color", "соломенно-желтый"); break;
                       case "желтый" : res.put("color", "желтый"); break;
                       case "темно-желтый" : res.put("color", "темно-желтый"); break;
                   }  
                   switch(arr[12]){
                       case "прозрачная" : res.put("turbidity", "прозрачная"); break;
                       case "мутная" : res.put("turbidity", "мутная"); break;
                   }  
                   return res;
                }
                default : {
                    res.put("barcode", Long.parseLong(arr[0]));
                    return res;
                }
            }
        } catch (JSONException | NumberFormatException e) {
            log.info(e,e);
        }
        
        return null;
    }
       
    private String getServiceIncode(Connection con,String code){
        String query = "select service_incode  from service where service_code = '"+code+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()){
               return (rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
        return "";
    }
    
    private String getServiceType(Connection con,String code){
        String query = "select service_name from service where service_code = '"+code+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()){
               return (rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
        return "";
    }
    
    private String getServiceParser (Connection con,String code){
        String query = "select parser from service where service_code = '"+code+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()){
               return (rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
        return "";
    }
    
    private String getClinicUuid (Connection con,String clinic){
        String query = "select uuid from clinic where code = '"+clinic+"'";
        PreparedStatement post; 
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery();
            while(rs.next()){
               return (rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
        return "";
    }
    
    private void updateStatus(Connection conn,String patientId){
        String query = "update ready_patient set on_server = 'yes' where id = "+patientId+" ";
        try {
            Statement stmt = conn.createStatement(); 
            stmt.executeUpdate(query);        
        } catch (SQLException e) {
            log.info(e,e);
        }
    }
    
     private void sendEcg(byte data[], String uuidPatient){
         try {
            HttpURLConnection connection;
            String server;
            String login;
            String pass;
            String mdcUrl;
            String device;
            connection = null;
            config.parse();
            server = "http://localhost:8090/tis/ates/upload";
            login = config.getTisUser();
            pass = config.getTisPassword();
            mdcUrl = config.getPutResultURL();
            device = config.getMdkID();
            String targetURL = (new StringBuilder()).append(server).append("?login=").append(login).append("&password=").append(pass).append("&serial=").append(device).append("&uuidPatient=").append(uuidPatient).append("&url=").append(mdcUrl).append("ecg").toString();
            URL url = new URL(targetURL);
            System.out.println(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(data);
            wr.close();
            InputStream responseStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();
            while((line = responseStreamReader.readLine()) != null) 
                stringBuilder.append(line).append("\n");
            responseStreamReader.close();
            String response = stringBuilder.toString();
            log.info(response);
         } catch (Exception e) {
             log.info(e,e);
         }
    }

    
}
