package mdc.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mdc.db.connect.StartDB;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class GetResultsForMdcServer {
    private String filename; 
    private ArrayList<String> idList = new ArrayList<>(); 
    private byte [] arr;
    private String xml_string;
    private static final Logger log = Logger.getLogger(GetResultsForMdcServer.class);
    private ArrayList<Object> xmlList = new ArrayList<>(); 

    public ArrayList<Object> getXmlList() {
        return xmlList;
    }

    public void setXmlList(ArrayList<Object> xmlList) {
        this.xmlList = xmlList;
    }   
    
    public void setXml_string(String xml_string) {
        this.xml_string = xml_string;
    }

    public String getXml_string() {
        return xml_string;
    }
    public void setArr(byte[] arr) {
        this.arr = arr;
    }

    public byte[] getArr() {
        return arr;
    }   
    
    public ArrayList<String> getIdList() {
        return idList;
    }
    
    
    public void GetDataFromDB(Connection conn){
        String query = "select distinct x.id from xml_view x ";
        Statement post; 
        ResultSet rs;
        try{
            post = conn.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
               idList.add(rs.getString(1));
            }  
            System.out.println(idList.size());
        }catch (SQLException ex) {
            log.info(ex,ex);
        } 
    }
    

    
    public void writeXML(Connection conn) throws SQLException {
        GetDataFromDB(conn);
        Statement post; 
        ResultSet rs;
        if(idList.isEmpty()) {
            setArr("<PATIENT_RESULT></PATIENT_RESULT>".getBytes());
            log.info("Нет пациентов для отправки");
            return;
        }
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // patient_list
            Document doc = docBuilder.newDocument();
            doc.setXmlVersion("1.0");
            Element list = doc.createElement("PATIENT_RESULT");                
            doc.appendChild(list);
            for (int i = 0; i < idList.size(); i++) {
                String query = "select *from xml_view  x where x.id = '"+idList.get(i)+"'";

                post = conn.createStatement();
                rs = post.executeQuery(query);
                
                //<editor-fold defaultstate="collapsed" desc="xml creating">
                Element pat = doc.createElement("patient");
                list.appendChild(pat);
                Element transm_date = doc.createElement("transm");
                pat.appendChild(transm_date);

                Element eventid = doc.createElement("eventid");
 
                boolean flag = false;

                while(rs.next()){                 
                    if(flag == false){

                    //fap.appendChild(doc.createTextNode(rs.getString("fap")));
                    //pat.appendChild(fap);

                    eventid.appendChild(doc.createTextNode(rs.getString("eventid")));
                    pat.appendChild(eventid);


                    flag = true;
                }
                    Attr attr0 = doc.createAttribute("DATE");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                   // String d = df.format(rs.getDate("transm_date")); 
                    attr0.setValue(rs.getString("transm_date"));
                    transm_date.setAttributeNode(attr0);

                    Attr attr1 = doc.createAttribute("ID");
                    attr1.setValue(rs.getString("id"));
                    pat.setAttributeNode(attr1);

                    Element serv = doc.createElement("service");
                    pat.appendChild(serv);

                    Attr attr2 = doc.createAttribute("CODE");
                    attr2.setValue(rs.getString("code"));
                    serv.setAttributeNode(attr2);
                    // services
                    Element pat_code = doc.createElement("serv_code");
                    pat_code.appendChild(doc.createTextNode(rs.getString("service_code")));
                    serv.appendChild(pat_code);


                    Element state = doc.createElement("state");
                    state.appendChild(doc.createTextNode(rs.getString("state")));
                    serv.appendChild(state);

                    Element date = doc.createElement("date");
                    String value0 = rs.getString("date");
                    if(rs.wasNull()){value0 = "";}
                    date.appendChild(doc.createTextNode(value0));
                    serv.appendChild(date);

                    Element compl = doc.createElement("completed");
                    String value = rs.getString("complied");
                    if(rs.wasNull()){ value = ""; }
                    compl.appendChild(doc.createTextNode(value));
                    serv.appendChild(compl);

                    Element result = doc.createElement("result");
                    String value1 = "";
                    if(rs.getString("service_code").equals("A05.10.002") && rs.getString("state").equals("Выполнено")){
                        byte[] data = rs.getBytes("pdf_bin");
                        Base64 bas = new Base64();
                        String message = bas.encodeToString(data);
                        value1 = message;

                        //Element ecg_bin = doc.createElement("ecg_bin");
                        //byte[] data1 = rs.getBytes("ecg_bin");
                        //Base64 bas1 = new Base64();
                        //String message1 = bas1.encodeToString(data1);
                       // ecg_bin.appendChild(doc.createTextNode(message1));
                       // serv.appendChild(ecg_bin);
                    }else{
                        value1 = rs.getString("rezult");
                    }
                    if(rs.wasNull()){value1 = "";}
                    result.appendChild(doc.createTextNode(value1));
                    serv.appendChild(result);
                }    
                //</editor-fold>
                if(i !=0 && (i % 10 == 0 || i == idList.size()-1)){
                    System.out.println(i);
                    createXml(i,doc);
                    doc = docBuilder.newDocument();
                    doc.setXmlVersion("1.0");
                    list = doc.createElement("PATIENT_RESULT");                
                    doc.appendChild(list);
                    
                } 
            }  
            // write the content into xml file
           
                
            
        }catch (ParserConfigurationException pce) {
              log.info(pce, pce);
        }
    }
   
    private void createXml(int i,Document doc){
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();                
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            filename = "patResult"+i;
            filename = filename.trim();
            StreamResult res = new StreamResult(new File("C:\\tmp\\"+filename+".xml"));
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            StreamResult result=new StreamResult(bos);
            transformer.transform(source, res);
            setArr(bos.toByteArray());
            log.info("XML was succ saved");
        }catch (TransformerException pce) {
              log.info(pce, pce);
        }
    }
        
    public static void main(String[] args) throws SQLException {
        GetResultsForMdcServer xml = new GetResultsForMdcServer();
        StartDB db = new StartDB();
        db.Start();
        //xml.test(db.getConnection());
        xml.writeXML(db.getConnection());
    }
   
    
}

