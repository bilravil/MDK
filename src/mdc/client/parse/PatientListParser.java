package mdc.client.parse;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import mdc.client.update.UpdatePatientTable;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PatientListParser {
    private ArrayList<String> dbList = new ArrayList<>();
    private String id;
    private String surname;
    private String name;
    private String middlename;
    private String birthdate;
    private String sex;
    private String address;
    private String lpu;
    private String crb;
    private Connection con;
    private static final Logger log = Logger.getLogger(PatientListParser.class);
    
    public PatientListParser(Connection con) {
        this.con = con;
    }
    
    public PatientListParser() {
      
    }
    private  UpdatePatientTable patient = new UpdatePatientTable();
        
    private Document byteToXML(byte[] documentoXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(documentoXml));
}
    
    
    public  void parse(byte[] arr){
        try {
            getPatientIdFromDb();
            Document doc = byteToXML(arr);
            doc.getDocumentElement().normalize();        
            NodeList nList = doc.getElementsByTagName("patient");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);            
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    id = eElement.getAttribute("ID");
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            surname = eElement
                              .getElementsByTagName("surname")
                              .item(0)
                              .getTextContent();

                            name = eElement
                              .getElementsByTagName("name")
                              .item(0)
                              .getTextContent();
                            middlename =  eElement
                              .getElementsByTagName("middle_name")
                              .item(0)
                              .getTextContent();
                            birthdate = eElement
                              .getElementsByTagName("birthdate")
                              .item(0)
                              .getTextContent();

                            sex =  eElement
                              .getElementsByTagName("sex")
                              .item(0)
                              .getTextContent(); 

                            address = eElement
                              .getElementsByTagName("address")
                              .item(0)
                              .getTextContent(); 

                            lpu = eElement
                              .getElementsByTagName("lpu")
                              .item(0)
                              .getTextContent();         

                            crb = eElement
                              .getElementsByTagName("crb")
                              .item(0)
                              .getTextContent();         
                        }   
                }
                if(!search(id)){
                     patient.UpdatePatientTable(con,id, surname, name, middlename,birthdate, sex, address,lpu,crb);
                }          
            }
        }catch (Exception e) {
            log.info(e,e);
        }
   }  
   
    private boolean search(String value){
        for (int i = 0; i < dbList.size(); i++) {
            if(value.equals(dbList.get(i)))
                return true;
        }
        return false;
    }
       
    private void getPatientIdFromDb(){
        String query = "Select id FROM mdk_base.patient";
        PreparedStatement post;
        ResultSet rs;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
                dbList.add(rs.getString(1));
            }   
        }catch (SQLException ex) {
            log.info(ex,ex);
        }
    }   
}
