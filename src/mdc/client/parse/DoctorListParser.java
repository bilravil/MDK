package mdc.client.parse;



import mdc.client.update.UpdateDocList;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DoctorListParser {
   
    private String id;
    private String name;
    private String snils;
    private String v002;
    private String v015;
    private String crbID;
    private Connection con;

    public DoctorListParser(Connection con) {
        this.con = con;
    }
    
    public DoctorListParser() {
      
    }
    private  UpdateDocList doctor = new UpdateDocList();
        
    private Document byteToXML(byte[] documentoXml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(documentoXml));
}
    
    
   public  void parse(byte[] arr){
      try {
         Document doc = byteToXML(arr);
         doc.getDocumentElement().normalize();
         
         NodeList nList = doc.getElementsByTagName("doctor");
         for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               id = eElement.getAttribute("ID");
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                name = eElement
                  .getElementsByTagName("name")
                  .item(0)
                  .getTextContent();
               snils = eElement
                  .getElementsByTagName("snils")
                  .item(0)
                  .getTextContent();
               v002 =  eElement
                  .getElementsByTagName("v002")
                  .item(0)
                  .getTextContent();
               v015 = eElement
                  .getElementsByTagName("v015")
                  .item(0)
                  .getTextContent();
                crbID = eElement
                  .getElementsByTagName("crb")
                  .item(0)
                  .getTextContent(); 
                      
                    } 
                }  
            doctor.AddNewDoctor(con, id, name, snils, v002, v015, crbID);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}
