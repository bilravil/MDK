package mdc.client.parse;



import mdc.client.update.UpdateFapTable;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FapListParser {
   
    private String old_cod;
    private String name;
    private String new_cod;

    private Connection con;

    public FapListParser(Connection con) {
        this.con = con;
    }
    
    public FapListParser() {
      
    }
    private  UpdateFapTable fap = new UpdateFapTable();
        
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
         
         NodeList nList = doc.getElementsByTagName("fap");
         for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                old_cod = eElement
                  .getElementsByTagName("old_reg_cod")
                  .item(0)
                  .getTextContent();        
                name = eElement
                  .getElementsByTagName("name")
                  .item(0)
                  .getTextContent();
               new_cod = eElement
                  .getElementsByTagName("new_reg_cod")
                  .item(0)
                  .getTextContent();

                        }
                    
                }  
           fap.updateFapTable(con, old_cod, name, new_cod);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}
