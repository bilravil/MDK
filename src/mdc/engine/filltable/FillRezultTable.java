
package mdc.engine.filltable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import mdc.engine.SessionData;
import org.apache.log4j.Logger;


public class FillRezultTable {
    private PatientData patient;
    private static final Logger log = Logger.getLogger(FillRezultTable.class);
       
    public FillRezultTable(PatientData patient) {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        this.patient = patient;
    }
    
    
    public ResultSet fillServiceListTable(String id,Connection con){
        //<editor-fold defaultstate="collapsed" desc="Получение индивид услуг для пациента и для заполения ими таблицы в 3-й вкладке ">
        String event = patient.getUuid();
        String query = "select  Наименование from service_list sl "
                + "where `sl`.`id`='"+id+"' and sl.eventid = '"+event+"' order by `№`";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            return rs;
        }catch (SQLException ex) {
            log.info(ex,ex);
    }        
       return null;
//</editor-fold>       
   }
     
     public ResultSet fillServiceParam(String id,Connection con){
         //<editor-fold defaultstate="collapsed" desc="Получение параметров для услуги и заполнение ими резулть таблицы">
         String query = "select service_p as 'Параметр' from service_prop sp "
               + "inner join rezult r on( sp.service_code = r.service_code) "
               + "inner join service s on(sp.service_code = s.service_code)"
               + " where service_name ='"+id+"'";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            return rs;
        }catch (SQLException ex) {
            log.info(ex,ex);
    }        
       return null;
//</editor-fold>       
   }
        
    public String getServiceCode(Connection con, String id,String name ){
        //<editor-fold defaultstate="collapsed" desc="Получение индивид кода (code) у пациента по услуге и id ">
        String code = "";
        String event = patient.getUuid();
        String query = "select r.code from  service s "
               + "inner join rezult r on( s.service_code  = r.service_code)"
               + " where service_name = '"+name+"' and r.id ='"+id+"' and r.eventid = '"+event+"'";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
               code = rs.getString("code");
            }
            return code;
        }catch (SQLException ex) {
            log.info(ex,ex);
        }        
       return null; 
//</editor-fold>    
    } 
    
    public String getServiceCodeFromPrint(Connection con, String id,String name ){
        //<editor-fold defaultstate="collapsed" desc="Получение кода услуги ,которая идет на печать">
        String code = "";
        String event = patient.getUuid();
        String query = "select r.code from  for_print s "
               + "inner join rezult r on( s.service_code  = r.service_code) "
               + "where service_name = '"+name+"' and r.id ='"+id+"' and r.eventid = '"+event+"'";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
               code = rs.getString("code");
            }
            return code;
        }catch (SQLException ex) {
            log.info(ex,ex);
    }        
       return null; 
//</editor-fold>       
    }  
    
    
    public ResultSet fillParamTable(String code,String name,Connection con){
        //<editor-fold defaultstate="collapsed" desc="Получение параметра услуги и соотв резултьат для резулть. таблицы">
        String event = patient.getUuid();
        String query = "select service_p as 'Параметр', r.`rezult` as 'Значение' from service_prop sp "
                + "inner join rezult r on( sp.service_code = r.service_code) "
                + "inner join service s on(sp.service_code = s.service_code)"
                + " where service_name = '"+name+"' and  r.code = '"+code+"' and r.eventid = '"+event+"'";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            return rs;
        }catch (SQLException ex) {
            log.info(ex,ex);
    }   
        return null;
//</editor-fold>      
    }
    
   public void updateAcusherDoctor(Connection con, String date, String doc,String id){     
        //<editor-fold defaultstate="collapsed" desc="!Добавление доктора , даты в Осмотр фельдшером окушеркой!">
       String event = patient.getUuid();
       String query = "update  rezult  set complied = '"+doc+"',`date` ='"+date+"' , state = 'Выполнено'"
               + " where id = '"+id+"' and service_code = 'A11.20.024' and eventid = '"+event+"'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);         
        }catch (SQLException ex) {
            log.info(ex,ex);
    }   
//</editor-fold>      
   }  
    
   public void updateMeasures(Connection con, String column, String rez,String id){        
       //<editor-fold defaultstate="collapsed" desc="Добавление результатов в промежуточную таблицу(measures)">
       String event = patient.getUuid();
       String query = "update  measures  set `"+column+"` = '"+rez+"' "
               + "where id = '"+id+"' and eventid = '"+event+"'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);         
        }catch (SQLException ex) {
            log.info(ex,ex);
    }  
//</editor-fold>        
   } 
   
   public void insertIntoMeasures(Connection con, String id){
       //<editor-fold defaultstate="collapsed" desc="Добавление нового человека в таблицу(measures)">
        String event = patient.getUuid();
        String query = "insert into measures (id,eventid) values('"+id+"','"+event+"')";
        try{
            if(checkPatientInMeasuresTab(id,con) == 0){
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);  
            }
        }catch (SQLException ex) {
            log.info(ex,ex);
    }  
//</editor-fold>       
   } 
      
    public int checkPatientInMeasuresTab(String id,Connection con){       
      //<editor-fold defaultstate="collapsed" desc="Проверка, есть ли пациент в промежут.таблице(measures)">
        String event = patient.getUuid();
        String query = "select id from measures  where `id` = '"+id+"' and eventid = '"+event+"'";
        Statement post; 
        ResultSet rs;
            try{
                post = con.createStatement();
                rs = post.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    return 0;
                } 
                return -1;
            }catch (SQLException ex) {
                log.info(ex,ex);
        }         
           return -1;
//</editor-fold>     
   }
      
    public String showMeasuresParam(Connection con,String id,String name){
        //<editor-fold defaultstate="collapsed" desc="Вывод столбцов из таблицы (measures)">
        String event = patient.getUuid();
        String query = "select `"+name+"` from measures  where id= '"+id+"' and eventid = '"+event+"'";
        String value = "";
        Statement post; 
        ResultSet rs;
            try{
                post = con.createStatement();
                rs = post.executeQuery(query);
                while(rs.next()){
                   value = rs.getString(name);
                }
                return value;
            }catch (SQLException ex) {
                log.info(ex,ex);
                } 
              return null;
      }
//</editor-fold>
   
        // используется код услуги 06.ЭКГ в коде!!!
      public void insertPDF(Connection conn,FileInputStream fis,FileInputStream ecgFis,File file,String id, SessionData session) {
        //<editor-fold defaultstate="collapsed" desc="Добавление pdf с ЭКГ">
        int len;      
        String query = "";
        String event = patient.getUuid();
        PreparedStatement pstmt;
        String author = session.getAcusher();
        String insert = "update rezult  set state = 'Выполнено', `rezult` = 'Файл',`date` = DATE_FORMAT(NOW(),'%d.%m.%Y') , performer = '"+author+"'"
                + " where id = '"+id+"' and service_code = 'A05.10.002' and eventid = '"+event+"'";
        try {
            len = (int)file.length();
            if(checkPatientInPdfTab(id,conn) == 0){
                // если человека нет в таблице с pdf 
                query = ("insert into pdf_base VALUES(?,?,?,?,?)");
                pstmt = conn.prepareStatement(query);            
                pstmt.setString(1,id);
                pstmt.setInt(2, len);            
                pstmt.setBinaryStream(3, fis, len); 
                pstmt.setBinaryStream(4, ecgFis); 
                pstmt.setString(5, event);
                pstmt.executeUpdate();   
                pstmt = conn.prepareStatement(insert);    
                pstmt.executeUpdate();   
            }else{
                // если он уже там есть,обновляем 
                query = ("update pdf_base set pdf_bin = ? , pdf_len = ?, ecg_bin = ? where id = ?");  
                pstmt = conn.prepareStatement(query);
                pstmt.setString(4,id);
                pstmt.setInt(2, len);            
                //method to insert a stream of bytes
                pstmt.setBinaryStream(1, fis, len); 
                pstmt.setBinaryStream(3, ecgFis); 
                pstmt.executeUpdate();  
                pstmt = conn.prepareStatement(insert);    
                pstmt.executeUpdate();
            }
                   
        } catch (Exception e) {
            log.info(e,e);
        }
//</editor-fold>       
    }
      public void insertPdfBytes(SessionData session, String id, Connection conn, String pdf, String ecg)  throws FileNotFoundException, IOException {
        int s = 0;
        byte[] pdfByte = null;
        byte[] ecgByte = null;
        File pdfFile = new File(pdf);
        File ecgFile = new File(ecg);
        FileInputStream fisPdf = new FileInputStream(pdfFile);
        ByteArrayOutputStream bosPdf = new ByteArrayOutputStream();
        FileInputStream fisEcg = new FileInputStream(ecgFile);
        ByteArrayOutputStream bosEcg = new ByteArrayOutputStream();
        byte[] bufPdf = new byte[1024];
        byte[] bufEcg = new byte[1024];
        try {
            int readNum;
            while ((readNum = fisPdf.read(bufPdf)) != -1) {
                bosPdf.write(bufPdf, 0, readNum);
            }
            while ((readNum = fisEcg.read(bufEcg)) != -1) {
              bosEcg.write(bufEcg, 0, readNum);
            }
        }
        catch (IOException ex){ log.info(ex, ex); }
        pdfByte = bosPdf.toByteArray();
        ecgByte = bosEcg.toByteArray();

        String query = "";
        String event = this.patient.getUuid();

        String author = session.getAcusher();
        String insert = "update rezult  set state = 'Выполнено', `rezult` = 'Файл',`date` = strftime('%d.%m.%Y','now') , performer = '" + author + "' where id = '" + id + "' and service_code = 'A05.10.002' and eventid = '" + event + "'";
        try{
          if (checkPatientInPdfTab(id, conn) == 0)
          {
            query = "insert into pdf_base VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setInt(2, 0);
            pstmt.setBytes(3, pdfByte);
            pstmt.setBytes(4, ecgByte);
            pstmt.setString(5, event);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(insert);
            pstmt.executeUpdate();
          }
          else {
            query = "update pdf_base set pdf_bin = ? , pdf_len = ?, ecg_bin = ? where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setBytes(1, pdfByte);
            pstmt.setInt(2, 0);
            pstmt.setBytes(3, ecgByte);
            pstmt.setString(4, id);
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement(insert);
            pstmt.executeUpdate();
          }
        }
        catch (SQLException e) {log.info(e, e); }
  }
           
    public int checkPatientInPdfTab(String id,Connection con){
          //<editor-fold defaultstate="collapsed" desc="Проверка ,есть ли человек в таблице с pdf ">
            String event = patient.getUuid();
            String query = "select * from pdf_base where id = '"+id+"' and eventid = '"+event+"'";
            Statement post; 
            ResultSet rs;
            try{
                post = con.createStatement();
                rs = post.executeQuery(query);
                if(!rs.isBeforeFirst()){
                    return 0;
                }   
                return -1;
            }catch (SQLException ex) {
                log.info(ex,ex);
        }         
           return -1; 
//</editor-fold>     
   }
            
    public void addBarcode(Connection con,String bar, String id,String date,String code){
          //<editor-fold defaultstate="collapsed" desc="Добавление штрихокда в таблицу rezult ">
        String event = patient.getUuid();
        String query  = "Update  rezult  set `rezult` = '"+bar+"', `date` = '"+date+"'"
                  + " where `id` = '"+id+"' and `code` = '"+code+"' and eventid = '"+event+"'";
        try{
            Statement pstmt = con.createStatement();
            pstmt.executeUpdate(query);         
        }catch (SQLException ex) {
            log.info(ex,ex);
            } 
//</editor-fold>          
      }
      
    public void addBarcodeForBlood(Connection con,String bar, String id,String date,String code){
          //<editor-fold defaultstate="collapsed" desc="Добавление штрихкода для крови">
        String event = patient.getUuid();
        String query  = "Update  rezult  set `rezult` = '"+bar+"', `date` = '"+date+"' "
                  + "where `id` = '"+id+"' and `code` = '"+code+"' and eventid = '"+event+"'";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);         
        }catch (SQLException ex) {
            log.info(ex,ex);
            } 
//</editor-fold>          
    }
   
    public void updateBarcodeCount(Connection con,String value){       
        //<editor-fold defaultstate="collapsed" desc="Обновление кол-ва штрихкодов ">
        String query = "update barcode_count set id = '"+value+"' "
                + "where id = (select id from barcode_count) ";
        try{
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);         
        }catch (SQLException ex) {
            log.info(ex,ex);
        } 
//</editor-fold>   
    }
    
    public String getBarcodeCount(Connection con){
        //<editor-fold defaultstate="collapsed" desc="Получение кол-ва для штрихкода">
        String query = "select id from barcode_count";
        String count = "0";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
               count = rs.getString("id");
            }
            return count;
        }catch (SQLException ex) {
            log.info(ex,ex);
        }        
       return null; 
//</editor-fold>    
    }
    
    public void addDocUReaderAnalis(Connection con,ArrayList arr,PatientData patient){
        //<editor-fold defaultstate="collapsed" desc="Добавление полученных данных с уриноанализатора в таблицу measures">
          String event  =   patient.getUuid();
          String gl     =   arr.get(14).toString();
          String pr     =   arr.get(15).toString();
          String cet    =   arr.get(12).toString();
          String er     =   arr.get(16).toString();
          String lejk   =   arr.get(19).toString();
          String gr     =   "0";
          if(arr.size() >= 21){
             gr = arr.get(20).toString();  
          }          
          String pH     =   arr.get(17).toString();
          String urob   =   arr.get(11).toString();
          String asc    =   arr.get(13).toString();
          String nit    =   arr.get(18).toString();
          String bil    =   arr.get(10).toString();
          String col    =   arr.get(8).toString();
          String tur    =   arr.get(9).toString();          
          String id     =   patient.getId();
          
          String query = "UPDATE `measures` SET `glucose`=?, `protein`=?, `ketones`=?, "
                  + "`redblodd`=?, `whiteblood`=?, `gravity`=?, `pH`=?, `urob`=?, `ascorbic`=?, "
                  + "`nitrite`=?, `biliruben`=?, `color`=?, `turbidity`=? WHERE `id`=? and eventid = '"+event+"'";
          PreparedStatement preparedStatement = null;
          try {
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, gl);
                preparedStatement.setString(2, pr);
                preparedStatement.setString(3, cet);
                preparedStatement.setString(4, er);
                preparedStatement.setString(5, lejk);
                preparedStatement.setString(6, gr);
                preparedStatement.setString(7, pH);
                preparedStatement.setString(8, urob);
                preparedStatement.setString(9, asc);
                preparedStatement.setString(10, nit);
                preparedStatement.setString(11, bil);
                preparedStatement.setString(12, col);
                preparedStatement.setString(13, tur);
                preparedStatement.setString(14, id);
                // execute update SQL stetement
                preparedStatement.executeUpdate();
          } catch (Exception e) {
              log.info(e,e);
          }          
      }
//</editor-fold>
      
      
    public void addBC01Analis(Connection con,String [] arr,PatientData patient){
        //<editor-fold defaultstate="collapsed" desc="Добавление полученных данных с уриноанализатора в таблицу measures">
          String event  =   patient.getUuid();
          String id     =   patient.getId();  
          
          String gl     =   arr[5];
          String pr     =   arr[6];
          String cet    =   arr[3];
          String er     =   arr[1];
          String lejk   =   arr[4];
          String gr     =   arr[9];       
          String pH     =   arr[7];
          String urob   =   arr[0];
          String asc    =   arr[10];
          String nit    =   arr[8];
          String bil    =   arr[2];
          String col    =   "ВЫБЕРИТЕ ЦВЕТ";
          String tur    =   "ВЫБЕРИТЕ МУТНОСТЬ";         
          
          
          String query = "UPDATE `measures` SET `glucose`=?, `protein`=?, `ketones`=?, "
                  + "`redblodd`=?, `whiteblood`=?, `gravity`=?, `pH`=?, `urob`=?, `ascorbic`=?, "
                  + "`nitrite`=?, `biliruben`=?, `color`=?, `turbidity`=? WHERE `id`=? and eventid = '"+event+"'";
          PreparedStatement preparedStatement = null;
          try {
                preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, gl);
                preparedStatement.setString(2, pr);
                preparedStatement.setString(3, cet);
                preparedStatement.setString(4, er);
                preparedStatement.setString(5, lejk);
                preparedStatement.setString(6, gr);
                preparedStatement.setString(7, pH);
                preparedStatement.setString(8, urob);
                preparedStatement.setString(9, asc);
                preparedStatement.setString(10, nit);
                preparedStatement.setString(11, bil);
                preparedStatement.setString(12, col);
                preparedStatement.setString(13, tur);
                preparedStatement.setString(14, id);
                // execute update SQL stetement
                preparedStatement.executeUpdate();
          } catch (Exception e) {
              log.info(e,e);
          }          
      }
//</editor-fold>
}
