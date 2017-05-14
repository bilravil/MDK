package mdc.engine.filltable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import mdc.engine.SessionData;
import org.apache.log4j.Logger;



public class FillPatientTable {
    private final PatientData patient;
    private static final Logger log = Logger.getLogger(FillPatientTable.class);
    private boolean dispFlag = false;
    private final SessionData session;
    
    public boolean isDispFlag() {
        return dispFlag;
    }
     
    
    public FillPatientTable(PatientData patient,SessionData session){
        this.patient = patient;
        this.session = session;
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
   }
        
    
   public int  getPatientServiceFromAgeTab(String age,String sex, Connection con){
       //<editor-fold defaultstate="collapsed" desc="Получение услуг из возрастной таблицы для пациента и добавление его в таблицу rezult">
        if(checkDispAvail(age, sex, con) == 0) return -1;
        String id = patient.getId();
        String event = patient.getUuid();
        String author = session.getAcusher();
        String query = "select ss.service_num,ss.service_code from service4age s inner join  age a on(s.uuidAge = a.uuid) " +
                    "inner join service ss on (s.uuid = ss.service_code)" +
                    " where a.age like '%"+age+"%' and (a.sex = 0 or a.sex = "+sex+") ";
        PreparedStatement post; 
        ResultSet rs;     
        String service_code;
        String service_num;        
        try{
            Statement pstmt = con.createStatement();
            post = con.prepareStatement(query);
            rs = post.executeQuery();          
            if(checkPatientInResultTab(id,con) == 0){
                while(rs.next()){        
                    service_code = rs.getString("service_code");
                    service_num = rs.getString("service_num");
                    String code = (id + service_num).replaceAll(" ","");
                    patient.getArr().add(code);
                    UUID uuid = UUID.randomUUID();
                    String research = uuid.toString();
                    String q = "Insert into rezult(code,id,service_code,date, eventid,research,author) "
                            + "values('"+code+"','"+id+"','"+service_code+"' , strftime('%d.%m.%Y','now'),'"+event+"','"+research+"','"+author+"')";
                    pstmt.executeUpdate(q);
               }           
            }else{
                if(checkPatientDispCase(id, con) == 0){
                    while(rs.next()){
                        service_code = rs.getString("service_code");
                        service_num = rs.getString("service_num");
                        String code = (id + service_num).replaceAll(" ","");
                        patient.getArr().add(code);
                        UUID uuid = UUID.randomUUID();
                        String research = uuid.toString();
                        String q = "Insert into rezult(code,id,service_code,date,eventid,research,author) "
                                + "values('"+code+"','"+id+"','"+service_code+"' , strftime('%d.%m.%Y','now'),'"+event+"','"+research+"','"+author+"')";
                        pstmt.executeUpdate(q);
                    }
                }else{                   
                    patient.setUuid(getEventID(con));
                }
            }
        }catch (SQLException ex) {
            log.info(ex,ex);
        }  
//</editor-fold>            
        return 0;
    }
   
   private int checkDispAvail(String age,String sex,Connection con){
       //<editor-fold defaultstate="collapsed" desc="Проверка, на возможность прохождение диспы">
        String query = "select ss.service_num,ss.service_code from service4age s inner join  age a on(s.uuidAge = a.uuid) " +
                    "inner join service ss on (s.uuid = ss.service_code)" +
                    " where a.age like '%"+age+"%' and (a.sex = 0 or a.sex = "+sex+") ";
        try{
            PreparedStatement post = con.prepareStatement(query);         
            ResultSet rs = post.executeQuery(); 
            if(!rs.isBeforeFirst()){
                return 0;
            }         
        }catch (SQLException ex) {
            log.info(ex,ex);
            return -1;
        }         
       return -1;
//</editor-fold>       
   }
   
   private int checkPatientInResultTab(String id,Connection con){
       //<editor-fold defaultstate="collapsed" desc="Проверка, имеется ли пациент в таблице rezult">
        String query = "select id from rezult  where id ="+ id;
        try{
            PreparedStatement post = con.prepareStatement(query);         
            ResultSet rs = post.executeQuery(); 
            if(!rs.isBeforeFirst()){
                return 0;
            }         
        }catch (SQLException ex) {
            log.info(ex,ex);
            return -1;
        }         
       return -1;
//</editor-fold>       
   }
   
    private int checkPatientDispCase(String id,Connection con){
        //<editor-fold defaultstate="collapsed" desc="Проверка,есть ли случай дисп. в текущем году.">
        String query = "SELECT * FROM rezult  where id = '"+id+"' and (substr(date,7) = strftime('%Y','now'))";
        try{
            PreparedStatement post = con.prepareStatement(query);         
            ResultSet rs = post.executeQuery(); 
            if(!rs.isBeforeFirst()){
                return 0;
            }
        }catch (SQLException ex) {
            log.info(ex,ex);
        }         
       return -1;
        //</editor-fold>
   }
   
   public ResultSet showPatientTable(Connection con){
       //<editor-fold defaultstate="collapsed" desc="Заполнение таблицы с услугами диспы человека в 1-й вкладке Услуг">
        String id = patient.getId();
        String event = patient.getUuid();
        String query = "select service_num as №, service_name as 'Наименование', state as 'Состояние',`r`.`date` "
        + " AS `Дата` from rezult r inner join service s on( r.service_code = s.service_code) "
        + "where `r`.`id` = '"+id+"' and r.eventid = '"+event+"' ";
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
   
   public String getEventID(Connection con){
        String id = patient.getId();
        String query = "SELECT eventid FROM rezult "
                + "where id = '"+id+"' and (substr(date,7) = strftime('%Y','now')) limit 1 ;";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            log.info(ex,ex);
            return "";
        }
       return null;
   }
   
   public void updateServiceState(String code, String state,Connection con){
       //<editor-fold defaultstate="collapsed" desc="Обновление состояния услуги у пациента ">
        String event = patient.getUuid();
        try{
            Statement stmt = con.createStatement();
            String q = "Update rezult SET state = '"+state+"' where rezult.code ='"+code+"' and rezult.eventid = '"+event+"'";
            stmt.executeUpdate(q);
       }catch (SQLException ex) {
            log.info(ex,ex);
        }
//</editor-fold>
   }
   
 public void updateServiceDate(String code, String date,Connection con){
     //<editor-fold defaultstate="collapsed" desc="Обновление даты у услуги в таблице rezult">
    try{
        Statement stmt = con.createStatement();
        String event = patient.getUuid();
        String q = "Update rezult set `date` = '"+date+"' where rezult.code ='"+code+"' and rezult.eventid = '"+event+"'";
        stmt.executeUpdate(q);
    }catch (SQLException ex) {
        log.info(ex,ex);
    }
//</editor-fold>       
   }

 public String getCodeFromRezult(String service_name, Connection con){
     //<editor-fold defaultstate="collapsed" desc="Получение кода (code) пациента">
     String id = patient.getId();
     String event = patient.getUuid();
     String query = "select code from service s inner join  rezult r "
             + "on(s.service_code = r.service_code)"
             + " where s.service_name ='"+service_name+"' and r.id = '"+id+"' and r.eventid = '"+event+"'";
     Statement post; 
     ResultSet rs;
     try{
        post = con.createStatement();
        rs = post.executeQuery(query);
        while (rs.next()){
           return rs.getString("code");
        }       
     }catch (SQLException ex) {
            log.info(ex,ex);
    }return null;
//</editor-fold>     
 }

 public void removeAllPatientResult(Connection con){
     //<editor-fold defaultstate="collapsed" desc="Удаление пациента из таблицы rezult при выходе">
    String event = patient.getUuid();
    String id = patient.getId();
    String query = "Delete from rezult where id ='"+id+"' and eventid = '"+event+"'";    
    String query1 = "Delete from measures where id ='"+id+"' and eventid = '"+event+"'"; 
    try{
        Statement stmt = con.createStatement();
        stmt.execute(query);     
        stmt.execute(query1);  
    }catch (SQLException ex) {
        log.info(ex,ex);
    }
 }
//</editor-fold>
}
