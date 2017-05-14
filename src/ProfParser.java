import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import mdc.db.connect.StartDB;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ProfParser {
    private String code;
    private String name;
    private Document doc;
    private static final Logger log = Logger.getLogger(ProfParser.class);
    private Connection con;
    
    @Override
    public String toString() {
        return "ProfParser{" + "code=" + code + ", name=" + name + '}';
    }
    
    
    
    public void parse(){
        try {      
         doc.getDocumentElement().normalize();         
         NodeList nList = doc.getElementsByTagName("zap");
         for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {                      
                code = eElement.getAttribute("CODE");
                try {
                name = eElement.getAttribute("NAME");  
                } catch (Exception e) {
                    
                }              
               }
                }  FillDirectory();
 
         }
         
      } catch (Exception e) {
         log.info(e,e);
      }
    }
    
    private void FillDirectory(){
        String query = "Insert into mdk_server.v015_directory values (?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,code);
            pstmt.setString(2,name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            log.info(e,e);
        }
    }
    
    public void run() {
        try {
            StartDB db = new StartDB();
            db.Start();
            con = db.getConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String path = "V015.xml";
            doc = builder.parse(path);           
            parse();            
        } catch (Exception e) {
            System.out.println(e);
        }            
    }
    
    public static void main(String[] args) {
        String pth = "patients.xml";
        ProfParser parser = new ProfParser();
        parser.run();
    }
}
