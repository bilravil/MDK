package mdc.device.ecg;

import java.io.File;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mdc.engine.PatientData;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFileForECG
{
  private String filename;
  private static final Logger log = Logger.getLogger(WriteXMLFileForECG.class);
  
  public void writeXML(PatientData patient)
  {
    try
    {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      
      Document doc = docBuilder.newDocument();
      doc.setXmlVersion("1.0");
      
      Element request = doc.createElement("REQUEST");
      doc.appendChild(request);
      
      Attr attr0 = doc.createAttribute("Type");
      attr0.setValue("PATIENT_ADD");
      request.setAttributeNode(attr0);
      
      Element patient_data = doc.createElement("PATIENT_DATA");
      request.appendChild(patient_data);
      
      Attr attr1 = doc.createAttribute("XML_VERSION");
      attr1.setValue("1.0");
      patient_data.setAttributeNode(attr1);
      
      Element pat_db = doc.createElement("PAT_DB_ID");
      pat_db.appendChild(doc.createTextNode("-1"));
      patient_data.appendChild(pat_db);
      
      Element pat_code = doc.createElement("PAT_CODE");
      pat_code.appendChild(doc.createTextNode(patient.getId().trim()));
      patient_data.appendChild(pat_code);
      
      Element pat_fname = doc.createElement("PAT_FULL_NAME");
      pat_fname.appendChild(doc.createTextNode(patient.getName().trim()));
      patient_data.appendChild(pat_fname);
      
      Element pat_lname = doc.createElement("PAT_LAST_NAME");
      pat_lname.appendChild(doc.createTextNode(patient.getSurname()));
      patient_data.appendChild(pat_lname);
      
      Element pat_name = doc.createElement("PAT_FIRST_NAME");
      pat_name.appendChild(doc.createTextNode(patient.getNname()));
      patient_data.appendChild(pat_name);
      
      Element pat_mname = doc.createElement("PAT_MIDDLE_NAME");
      pat_mname.appendChild(doc.createTextNode(patient.getMiddleName()));
      patient_data.appendChild(pat_mname);
      
      Element pat_sex = doc.createElement("PAT_SEX");
      pat_sex.appendChild(doc.createTextNode(patient.getSex()));
      patient_data.appendChild(pat_sex);
      
      Element pat_birth = doc.createElement("PAT_BIRTH_DATE");
      pat_birth.appendChild(doc.createTextNode(patient.getBirth_date()));
      patient_data.appendChild(pat_birth);
      
      Element pat_age = doc.createElement("PAT_AGE");
      pat_age.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_age);
      
      Element pat_prof = doc.createElement("PAT_PROFESSION");
      pat_prof.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_prof);
      
      Element pat_city = doc.createElement("PAT_CITY");
      pat_city.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_city);
      
      Element pat_reg = doc.createElement("PAT_REGION");
      pat_reg.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_reg);
      
      Element pat_zip = doc.createElement("PAT_ZIP");
      pat_zip.appendChild(doc.createTextNode("zip"));
      patient_data.appendChild(pat_zip);
      
      Element pat_address = doc.createElement("PAT_ADDRESS");
      pat_address.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_address);
      
      Element pat_phone = doc.createElement("PAT_PHONE");
      pat_phone.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_phone);
      
      Element pat_weight = doc.createElement("PAT_WEIGHT");
      pat_weight.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_weight);
      
      Element pat_weight_units = doc.createElement("PAT_WEIGHT_UNITS");
      pat_weight_units.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_weight_units);
      
      Element pat_height = doc.createElement("PAT_HEIGHT");
      pat_height.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_height);
      
      Element pat_height_units = doc.createElement("PAT_HEIGHT_UNITS");
      pat_height_units.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_height_units);
      
      Element pat_syst = doc.createElement("PAT_SYST");
      pat_syst.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_syst);
      
      Element pat_diast = doc.createElement("PAT_DIAST");
      pat_diast.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_diast);
      
      Element pat_notes = doc.createElement("PAT_NOTES");
      pat_notes.appendChild(doc.createTextNode(" "));
      patient_data.appendChild(pat_notes);
      
      Element file_format = doc.createElement("EXAM_FILE_FORMAT");
      file_format.appendChild(doc.createTextNode("ECG"));
      request.appendChild(file_format);
      
      Element do_exam = doc.createElement("DO_EXAM");
      do_exam.appendChild(doc.createTextNode("TRUE"));
      request.appendChild(do_exam);
      
      Element start_acq = doc.createElement("START_ACQ");
      start_acq.appendChild(doc.createTextNode("TRUE"));
      request.appendChild(start_acq);
      
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty("indent", "yes");
      transformer.setOutputProperty("encoding", "windows-1251");
      transformer.setOutputProperty("omit-xml-declaration", "no");
      DOMSource source = new DOMSource(doc);
      this.filename = patient.getId();
      this.filename = this.filename.trim();
      StreamResult result = new StreamResult(new File("C:\\ECGReceiver\\XML\\In\\" + this.filename + ".xml"));
      
      transformer.transform(source, result);
      
      System.out.println("File saved!");
    }
    catch (ParserConfigurationException|TransformerException pce)
    {
      log.info(pce, pce);
    }
  }
}
