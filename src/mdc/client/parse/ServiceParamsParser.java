package mdc.client.parse;



import java.io.ByteArrayInputStream;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import mdc.client.update.UpdateServiceParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ServiceParamsParser {
   
    private String id;
    private String mnemonic;
    private String print;

    private Connection con;

    public ServiceParamsParser(Connection con) {
        this.con = con;
    }
    
    public ServiceParamsParser() {
      
    }
    private  UpdateServiceParams serv = new UpdateServiceParams();
        
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
         
         NodeList nList = doc.getElementsByTagName("service");
         for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               id = eElement.getAttribute("ID");
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                mnemonic = eElement
                  .getElementsByTagName("mnemonic")
                  .item(0)
                  .getTextContent();
               print = eElement
                  .getElementsByTagName("print")
                  .item(0)
                  .getTextContent();

                        }
                    
                }  
           //serv.UpdateServiceParamsTable(con, id, mnemonic, print);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}
