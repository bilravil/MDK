package mdc.update;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import mdc.engine.filltable.FillReadyTable;
import org.apache.log4j.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Update {
    String base;
    private static final Logger log = Logger.getLogger(FillReadyTable.class);

    public Update() {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();        
    }
    
    public static void main(String[] args) {
        Update up = new Update();
        up.writeConfig();
        
    }
    public void update(Connection con){
        if(isUpdate()){
            transferFile();
            deleteAllDisPTables(con);
            createTableAge(con);
            createTableService4Age(con);
            insertIntoTableAge(con);
            insertIntoTableService4Age(con);
            createPatientDataView(con);
            dropXmlView(con);
            createXmlView(con);
            addParserToService(con);
            writeConfig();
        }
    }
    
    public void writeConfig(){
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("mdk_config.xml");
            Node conf = doc.getElementsByTagName("config").item(0);

            Element pat = doc.createElement("update");
            pat.appendChild(doc.createTextNode("true"));
            conf.appendChild(pat);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("mdk_config.xml"));
            transformer.transform(source, result);

            System.out.println("Done");
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            log.info(e,e);
        }       
    }
    
    private boolean isUpdate(){
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("mdk_config.xml");
            Node staff = doc.getElementsByTagName("update").item(0);
            try {
                NodeList list = staff.getChildNodes();
                return false;
            } catch (Exception e) {
                System.out.println("Done");
                return true;
            }
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            log.info(pce);
        }
        return false;
    }
    
    private void deleteAllDisPTables(Connection con){
        for (int i = 21; i <= 99; i+=3) {
            String table = "m"+i;
            String query = "drop table "+table;
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);         
            }catch (SQLException ex) {
                log.info(ex,ex);
            }           
        }
        for (int i = 21; i <= 99; i+=3) {
            String table = "j"+i;
            String query = "drop table "+table;
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);         
            }catch (SQLException ex) {
                log.info(ex,ex);
            }                            
        }            
    }
      
    private void createTableAge(Connection con){
        String query = "CREATE TABLE age (uuid CHAR (40) PRIMARY KEY, age  VARCHAR (255),  sex  INT (1) );";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void createTableService4Age(Connection con){
        String query = "CREATE TABLE service4age (uuid CHAR (25) PRIMARY KEY, uuidAge CHAR (40));";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void insertIntoTableAge(Connection con){
        Statement post; 
        try{
            String query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000001', '{21,24,27,30,33,36,39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000004', '{21,24,27,30,33,36,42,48,54,60,66,72,78,84,90,96}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000006', '{36,39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 1);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000007', '{39,42,45,48,51,54,57}', 2);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000008', '{21,24,27,30,33,36,60,63,66,69}', 2);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000009', '{21,24,27,30,33,36,39,42,45,48,51,54,57,60,63,66,69}', 2);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000013', '{21,24,27,30,33,36,42,48,54,60,66,72,78,84,90,96}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000014', '{39,45,51,57,63,69,75,81,87,93,99}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000016', '{21,24,27,30,33,36,39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000017', '{45,48,51,54,57,60,63,66,69,72,75}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000018', '{39,45,51,57,63,69,75,81,87,93,99}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000019', '{39,42,45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000020', '{51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 1);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-000000000022', '{21,24,27,30,33,36,39,42,45,48,51,54,57,60,63}', 0);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO age (uuid, age, sex) VALUES ('00000000-0000-0000-0000-100000000006', '{45,48,51,54,57,60,63,66,69,72,75,78,81,84,87,90,93,96,99}', 2);";
            post = con.createStatement();
            post.executeUpdate(query);
            
            
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void insertIntoTableService4Age(Connection con){
        Statement post; 
        try{
            String query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A01.29.004', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A02.07.004', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A02.12.002', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A09.05.026', '00000000-0000-0000-0000-000000000004');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A09.05.023', '00000000-0000-0000-0000-000000000004');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A05.10.002', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A08.20.013/2', '00000000-0000-0000-0000-000000000007');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A08.20.013/1', '00000000-0000-0000-0000-000000000008');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A11.20.024', '00000000-0000-0000-0000-000000000009');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A06.09.006', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A06.20.004', '00000000-0000-0000-0000-000000000011');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A06.30.002', '00000000-0000-0000-0000-000000000011');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('B03.016.002', '00000000-0000-0000-0000-000000000013');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('B03.016.003', '00000000-0000-0000-0000-000000000014');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('B03.016.004', '00000000-0000-0000-0000-000000000014');";
            post = con.createStatement();
            post.executeUpdate(query);
                       
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('B03.016.006', '00000000-0000-0000-0000-000000000016');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A09.19.001/1', '00000000-0000-0000-0000-000000000017');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A04.16.001', '00000000-0000-0000-0000-000000000018');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A02.26.015', '00000000-0000-0000-0000-000000000019');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A09.05.130', '00000000-0000-0000-0000-000000000020');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A11.12.009', '00000000-0000-0000-0000-000000000021');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('A12.30.005', '00000000-0000-0000-0000-000000000022');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "INSERT INTO service4age (uuid, uuidAge) VALUES ('B01.047.005', '00000000-0000-0000-0000-000000000001');";
            post = con.createStatement();
            post.executeUpdate(query);
            
            query = "update  service set service_incode = 'A006' where service_num = 6 ";
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void createPatientDataView(Connection con){
        String query = "CREATE VIEW patient_data AS SELECT `pat`.`id` AS `id`, \n" +
"       `pat`.`surname` AS `surname`, \n" +
"       `pat`.`name` AS `name`, \n" +
"       `pat`.`middle_name` AS `middle_name`, \n" +
"       `pat`.`birth_date` AS `birth_date`, \n" +
"       `pat`.`sex` AS `sex`, \n" +
"       `pat`.`address` AS `address`, \n" +
"       `pat`.`lpu_id` AS `lpu_id`, \n" +
"       `pat`.`crb_id` AS `crb_id`, \n" +
"       `pd`.`phoneNum` AS `phoneNum`\n" +
"FROM   (`patient` `pat`\n" +
"       JOIN `patient_docs` `pd` ON ((`pat`.`id` = `pd`.`id`)));";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void dropXmlView(Connection con){
        String query = "drop view xml_view";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void createXmlView(Connection con){
        String query = "CREATE VIEW xml_view AS SELECT `r`.`id` AS `id`, \n" +
"       `r`.`eventid` AS `eventid`, \n" +
"       `r`.`service_code` AS `service_code`, \n" +
"       `r`.`code` AS `code`, \n" +
"       `r`.`state` AS `state`, \n" +
"       `r`.`rezult` AS `rezult`, \n" +
"       `r`.`complied` AS `complied`, \n" +
"       `r`.`date` AS `date`, \n" +
"       `r`.`pdf_bin` AS `pdf_bin`, \n" +
"       `r`.`ecg_bin` AS `ecg_bin`, \n" +
"       `ps`.`on_server` AS `on_server`, \n" +
"       `ps`.`transm_date` AS `transm_date`, \n" +
"       `ps`.`fap` AS `fap`, \n" +
"       `pd`.`surname` AS `surname`, \n" +
"       `pd`.`name` AS `name`, \n" +
"       `pd`.`birth_date` AS `birth_date`, \n" +
"       `pd`.`middle_name` AS `middle_name`, \n" +
"       `pd`.`sex` AS `sex`, \n" +
"       `pd`.`address` AS `address`, \n" +
"       `pd`.`crb_id` AS `crb_id`, \n" +
"       `pd`.`lpu_id` AS `lpu_id`, \n" +
"       `pd`.`phoneNum` AS `phoneNum`\n" +
"FROM   ((`full_result_view` `r`\n" +
"        JOIN `patient_data` `pd` ON ((`r`.`id` = `pd`.`id`)))\n" +
"        JOIN `full_patient_status` `ps` ON (((`r`.`id` = `ps`.`id`)\n" +
"        AND (`r`.`eventid` = `ps`.`eventid`))));";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void addParserToService(Connection con){
        String query = "alter table service add parser VARCHAR";
        Statement post; 
        try{
            post = con.createStatement();
            post.executeUpdate(query);
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }
    
    private void transferFile(){
        try {
            File fl = new File("sqlite-jdbc-3.16.1.jar");
            File newFolder = new File("lib/sqlite-jdbc-3.16.1.jar");
            fl.renameTo(newFolder);
            try{    		
                File dfile = new File("lib/sqlite-jdbc-3.14.2.jar");
                if(dfile.delete()){
                        log.info(" is deleted!");
                }else{
                        log.info("Delete operation is failed.");
                }
            }catch(Exception e){
                   log.info(e,e);
            }
        } catch (Exception e) {
            log.info(e,e);
        }       
    }
    
}
