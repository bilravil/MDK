package mdc.engine;



import java.io.File;
import java.io.IOException;
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

/**
 *
 * @author Равиль
 */
public class ReadConfigXml {
    private String mdkID;
    private String crbID;
    
    private String dbUser;
    private String dbPassword;
    private String dbHost;
    private String dbDriver;
    
    private String tisUser;
    private String tisPassword;
    private String loginURL;
    private String getPatientURL;
    private String getDoctorURL;
    private String getClinicURL;
    private String putResultURL;
    private String getServiceURL;
    private String getMdkInfoURL;
    
    private String rabbitUser;
    private String rabbitPassword;
    private String rabbitHost;
    private String rabbitPort;
    private String rabbitVH;
    private String rabbitExchange;
    private String rabbitQueue;
    private String rabbitRouting;

    private String serverType;
    private String addPatSwitcher ;
    private String downAllPatient;
    private String typeOfUrino;
    private String urinoName;
    
    private String baseName;
    private String ecgAppPath;
    private String printerName;
    private String GUIFontSize;
 
    public void parse(){
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(new File("mdk_config.xml"));
            // читаем настройки чемодана
            NodeList conf = doc.getElementsByTagName("MDK");
            for (int i = 0; i < conf.getLength(); i++) {
               Node nNode = conf.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  mdkID = eElement.getElementsByTagName("ID").item(0).getTextContent();
                  crbID = eElement.getElementsByTagName("crbID").item(0).getTextContent();                
               }
            }           
            // читаем настройки Базы Данных
            NodeList db = doc.getElementsByTagName("database");
            for (int i = 0; i < db.getLength(); i++) {
               Node nNode = db.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  dbUser = eElement.getElementsByTagName("login").item(0).getTextContent();
                  dbPassword = eElement.getElementsByTagName("password").item(0).getTextContent();
                  dbHost = eElement.getElementsByTagName("host").item(0).getTextContent();
                  dbDriver = eElement.getElementsByTagName("driver").item(0).getTextContent();  
                  baseName = eElement.getElementsByTagName("name").item(0).getTextContent(); 
               }
            }
            
            NodeList tis = doc.getElementsByTagName("tisServer");
            for (int i = 0; i < tis.getLength(); i++) {
               Node nNode = tis.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  tisUser = eElement.getElementsByTagName("login").item(0).getTextContent();
                  tisPassword = eElement.getElementsByTagName("password").item(0).getTextContent();
                  loginURL = eElement.getElementsByTagName("auth").item(0).getTextContent();
                  getPatientURL = eElement.getElementsByTagName("getPatient").item(0).getTextContent();
                  getDoctorURL = eElement.getElementsByTagName("getDoctor").item(0).getTextContent();
                  getClinicURL = eElement.getElementsByTagName("getClinic").item(0).getTextContent();
                  putResultURL = eElement.getElementsByTagName("putResult").item(0).getTextContent();
                  getServiceURL = eElement.getElementsByTagName("getService").item(0).getTextContent();
                  getMdkInfoURL = eElement.getElementsByTagName("getMdkInfo").item(0).getTextContent();
               }
            }
            
            NodeList rab = doc.getElementsByTagName("rabbitMQ");
            for (int i = 0; i < rab.getLength(); i++) {
               Node nNode = rab.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  rabbitUser = eElement.getElementsByTagName("login").item(0).getTextContent();
                  rabbitPassword = eElement.getElementsByTagName("password").item(0).getTextContent();
                  rabbitHost = eElement.getElementsByTagName("host").item(0).getTextContent();
                  rabbitPort = eElement.getElementsByTagName("port").item(0).getTextContent(); 
                  rabbitExchange = eElement.getElementsByTagName("exchange").item(0).getTextContent();
                  rabbitQueue = eElement.getElementsByTagName("queue").item(0).getTextContent();
                  rabbitVH = eElement.getElementsByTagName("virtualHost").item(0).getTextContent();
                  rabbitRouting = eElement.getElementsByTagName("routingKey").item(0).getTextContent(); 
               }
            }
            
            NodeList sw = doc.getElementsByTagName("switch");
            for (int i = 0; i < sw.getLength(); i++) {
               Node nNode = sw.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  serverType = eElement.getElementsByTagName("serverType").item(0).getTextContent();
                  downAllPatient = eElement.getElementsByTagName("allPatFlag").item(0).getTextContent();  
                  addPatSwitcher = eElement.getElementsByTagName("addPatBut").item(0).getTextContent();   
                  typeOfUrino = eElement.getElementsByTagName("typeOfUrino").item(0).getTextContent();  
                  urinoName = eElement.getElementsByTagName("urinoName").item(0).getTextContent();  
               }
            }
            
            NodeList pat = doc.getElementsByTagName("paths");
            for (int i = 0; i < pat.getLength(); i++) {
               Node nNode = pat.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  ecgAppPath = eElement.getElementsByTagName("pathToEcgApp").item(0).getTextContent();                
               }
            }
            
            NodeList pr = doc.getElementsByTagName("printer");
            for (int i = 0; i < pr.getLength(); i++) {
               Node nNode = pr.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  printerName = eElement.getElementsByTagName("name").item(0).getTextContent();                
               }
            }
            
            NodeList gui = doc.getElementsByTagName("GUI");
            for (int i = 0; i < gui.getLength(); i++) {
               Node nNode = gui.item(i);            
               if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                  Element eElement = (Element) nNode;
                  GUIFontSize = eElement.getElementsByTagName("fontScale").item(0).getTextContent();                
               }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }            
    }
       
    public void EditPAtientDownloadingFlag(){
        try {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("mdk_config.xml");
		Node staff = doc.getElementsByTagName("switch").item(0);


		NodeList list = staff.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			
                   Node node = list.item(i);

		   // get the salary element, and update the value
		   if ("allPatFlag".equals(node.getNodeName())) {
			node.setTextContent("false");
		   }
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("mdk_config.xml"));
		transformer.transform(source, result);

		System.out.println("Done");

	   } catch (ParserConfigurationException | TransformerException | IOException | SAXException pce) {
               System.out.println(pce);
	   }
    }
    
    public static void main(String[] args) {
        ReadConfigXml read = new ReadConfigXml();
        read.EditPAtientDownloadingFlag();
    }

    public String getGetServiceURL() {
        return getServiceURL;
    }

    public String getGetMdkInfoURL() {
        return getMdkInfoURL;
    }
    
    
    
    public String getServerType() {
        return serverType;
    }   
    
    public String getTisUser() {
        return tisUser;
    }

    public String getTisPassword() {
        return tisPassword;
    }    
    
    public String getTypeOfUrino() {
        return typeOfUrino;
    }

    public String getUrinoName() {
        return urinoName;
    }
    
    public String getMdkID() {
        return mdkID;
    }

    public String getCrbID() {
        return crbID;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getRabbitUser() {
        return rabbitUser;
    }

    public String getRabbitPassword() {
        return rabbitPassword;
    }

    public String getRabbitHost() {
        return rabbitHost;
    }

    public String getRabbitPort() {
        return rabbitPort;
    }

    public String getRabbitVH() {
        return rabbitVH;
    }

    public String getRabbitExchange() {
        return rabbitExchange;
    }

    public String getRabbitQueue() {
        return rabbitQueue;
    }

    public String getRabbitRouting() {
        return rabbitRouting;
    }

    public String getAddPatSwitcher() {
        return addPatSwitcher;
    }

    public String getDownAllPatient() {
        return downAllPatient;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getEcgAppPath() {
        return ecgAppPath;
    }

    public String getPrinterName() {
        return printerName;
    }

    public String getGUIFontSize() {
        return GUIFontSize;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public String getGetPatientURL() {
        return getPatientURL;
    }

    public String getGetDoctorURL() {
        return getDoctorURL;
    }

    public String getGetClinicURL() {
        return getClinicURL;
    }

    public String getPutResultURL() {
        return putResultURL;
    }
}
