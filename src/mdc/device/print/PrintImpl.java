package mdc.device.print;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import mdc.engine.CheckServiceState;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import mdc.engine.SessionData;
import mdc.engine.filltable.FillRezultTable;

/**
 *
 * @author Равиль
 */
public class PrintImpl {
    private String serv_name;
    private Connection con;
    private JTable table;
    private PatientData patient;  
    private FillRezultTable fill;
    private SessionData session;
    private ReadConfigXml config = new ReadConfigXml();
    private String crbID;
    private CheckServiceState serv_incode;
    
    public PrintImpl(Connection con, JTable table,FillRezultTable fill, PatientData patient,SessionData session) {
        config.parse();
        this.con = con;
        this.table = table;
        this.patient = patient;
        this.fill = fill;
        this.session = session;
        serv_incode = new CheckServiceState(null);
        crbID = config.getCrbID();
        crbID = crbID.substring(2, 6);
    }
    
    public void print(){
        String sv1 = null;
        String sv2 = null;

        int row = table.getSelectedRow();
        serv_name = table.getValueAt(row, 0).toString();
        Date now = new Date();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(now); 
        if (table.getName().equals("PaperTable") || table.getName().equals("DirectTable")){
            PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),"null",serv_name,date);  
            print.PrintBarcode();    
        }else{
           // String code =fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
            String code = generateBarcode();
            if(serv_name.equals("04.Холестерин,глюкоза в крови (К)")){
                String s_name = "04.Глюкоза/Холестерин в кр (К)";           
                String code1 = fill.getServiceCode(con, patient.getId(),"04.Холестерин в крови (К)");
                PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,s_name,date);  
                print.PrintBarcode();  
                sv1 = "04.Холестерин в крови (К)";
                sv2 = "05.Глюкоза в крови (К)";   
                String code12 = fill.getServiceCode(con, patient.getId(), sv1);
                String code2 = generateBarcode();
                fill.addBarcode(con,code2, patient.getId(), date,code12);  
                fill.addBarcodeForBlood(con,code2, patient.getId(), date,fill.getServiceCode(con, patient.getId(), sv2)); 
                return;
            }
            if(serv_name.contains("07.Мазок на онкоц (Ж)") || serv_name.contains("07.Мазок на онкоц (С)")){
                PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,serv_name,date);  
                print.PrintBarcode();  
                String code12 = fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
                String code2 = generateBarcode();
                fill.addBarcode(con,code2, patient.getId(), date,code12);             
                fill.updateAcusherDoctor(con, date, session.getAcusher(), patient.getId());

                return;
            }
            else{
            PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,serv_name,date);  
            print.PrintBarcode();
            String code12 = fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
            String code2 = generateBarcode();
            fill.addBarcode(con,code2, patient.getId(), date,code12);
            }
        } 
    }
    
    public void print(int row){
    String sv1 = null;
    String sv2 = null;
    serv_name = table.getValueAt(row, 0).toString();
    Date now = new Date();
    DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    String date = formatter.format(now); 
    if (table.getName().equals("PaperTable")){
        PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),"null",serv_name,date);  
        print.PrintBarcode();              
    }
    else if(table.getName().equals("DirectTable")){
        String code = generateBarcode();
        PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),"null",serv_name,date);  
        print.PrintBarcode();  
        String code12 = fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
        //String code2 = generateBarcode();
        fill.addBarcode(con,code, patient.getId(), date, code12);     
        } 
    else{
        //String code =fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
        String code = generateBarcode();
        if(serv_name.equals("04.Холестерин,глюкоза в крови (К)")){
            String s_name = "04.Глюкоза/Холестерин в кр (К)";           
            //String code1 = fill.getServiceCode(con, patient.getId(),"04.Холестерин в крови (К)");
            PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,s_name,date);  
            print.PrintBarcode();  
            sv1 = "04.Холестерин в крови (К)" ;
            sv2 = "05.Глюкоза в крови (К)" ;    
            String code12 = fill.getServiceCode(con, patient.getId(), sv1);
           // String code2 = generateBarcode();
            fill.addBarcode(con,code, patient.getId(), date,code12);  
            fill.addBarcodeForBlood(con,code, patient.getId(), date,fill.getServiceCode(con, patient.getId(), sv2)); 
            return;
            
        }
        if(serv_name.contains("07.Мазок на онкоц (Ж)") || serv_name.contains("07.Мазок на онкоц (С)")){
            PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,serv_name,date);  
            print.PrintBarcode();  
            String code12 = fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
           // String code2 = generateBarcode();
            fill.addBarcode(con,code, patient.getId(), date,code12);  
            
            fill.updateAcusherDoctor(con, date, session.getAcusher(), patient.getId());
              
            return;
        }
        else{
            PrintBarcode print= new PrintBarcode(patient.getId(),patient.getName(),patient.getBirth_date(),code,serv_name,date);  
            print.PrintBarcode();  
            String code12 = fill.getServiceCodeFromPrint(con, patient.getId(), serv_name);
            //String code2 = generateBarcode();
            fill.addBarcode(con,code, patient.getId(), date, code12);
        }
    } 
    }
    
    
    private String generateBarcode(){
        String mdkID = config.getMdkID();
        mdkID = mdkID.replaceAll("-","");
        String count = fill.getBarcodeCount(con);
        if(count.equals("100000")){
            count = "1";
        }
        String result = mdkID+count;
        System.out.println(result);
        if(result.length()!=13){
            while(result.length()!=13){
                result = result.substring(0, 6) + "0" + result.substring(6, result.length());
            }
        }
        count = String.valueOf(Integer.parseInt(count) + 1);
        fill.updateBarcodeCount(con,count);
        return result;
    }

}
