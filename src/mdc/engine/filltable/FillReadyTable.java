
package mdc.engine.filltable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import mdc.engine.SessionData;
import org.apache.log4j.Logger;



public class FillReadyTable {
    private final PatientData patient;
    private Connection con;
    private String base;
    private static final Logger log = Logger.getLogger(FillReadyTable.class);
    private final SessionData session;
    
    public FillReadyTable(Connection con,PatientData patient,SessionData session) {
        this.con = con;
        this.patient = patient;
        this.session = session;
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
    }
    
   
    public void FillResult(String fap){
        //<editor-fold defaultstate="collapsed" desc="Заполнение табилцы rezult данными из таблицы measures">
        AddInReadyTable(fap);
        Date now = new Date();
        String query1 = "";
        String query2 = "";
        String query3 = "";
        String query4 = "";
        String query5 = "";
        String query6 = "";
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(now);
        String id = patient.getId();
        String event = patient.getUuid();
        String code1 = getCode("A01.29.004");//Опрос (анкетирование)
        String code2 = getCode("A02.07.004");//Антропометрия
        String code3 = getCode("A02.12.002");//Измерение артериального давления
        String code4 = getCode("A06.09.006");//08.Флюорография легких
        String code5 = getCode("B03.016.006");//13.Общий анализ мочи
        String code6 = getCode("A02.26.015");//Измерение внутриглазного давления
        String performer = session.getAcusher();
        if(code1!= null){
            if(getState(code1).equals("Выполнено")||getState(code1).equals("Выполнено ранее")){
                query1 = "update rezult  set rezult = (select smoke from measures m where m.id ='"+id+"' and m.eventid = '"+event+"'), state = '"+getState(code1)+"',date = '"+date+"', performer = '"+performer+"'   where code ='"+code1+"' and eventid = '"+event+"'";
            }
        }
        if(code2!= null){
            if(getState(code2).equals("Выполнено")||getState(code2).equals("Выполнено ранее")){
                query2 = "update rezult  set rezult = (select  height ||';'|| waist ||';'|| weight from measures m where m.id ='"+id+"' and m.eventid = '"+event+"'), state = '"+getState(code2)+"',date = '"+date+"', performer = '"+performer+"' where code =  '"+code2+"' and eventid = '"+event+"'";
            }
        }
        if(code3!= null){
            if(getState(code3).equals("Выполнено")||getState(code3).equals("Выполнено ранее")){
                query3 = "update rezult  set rezult = (select  pulse ||';'|| toppress ||';'|| lowpress from measures m where m.id ='"+id+"' and m.eventid = '"+event+"'), state = '"+getState(code3)+"',date = '"+date+"', performer = '"+performer+"' where code =  '"+code3+"' and eventid = '"+event+"'";
            }
        }
        if(code4!= null){
            if(getState(code4).equals("Выполнено ранее")){
                query4 = "update rezult  set rezult = (select pathology from measures m where m.id ='"+id+"' and m.eventid = '"+event+"'), state = '"+getState(code4)+"', performer = '"+performer+"' where code =  '"+code4+"' and eventid = '"+event+"'";
            }
        }
        if(code5!= null){
            if(getState(code5).equals("Выполнено")||getState(code5).equals("Выполнено ранее")){
                query5 = "update rezult  set rezult = (select `glucose` ||';'|| `protein` ||';'|| `ketones` ||';'|| `redblodd` ||';'|| `whiteblood` ||';'|| `gravity` ||';'|| \n" +
                "`pH` ||';'|| `urob` ||';'|| `ascorbic` ||';'|| `nitrite` ||';'|| `biliruben` ||';'|| `color` ||';'|| `turbidity` from measures m where m.id ='"+id+"' and m.eventid = '"+event+"'), state = '"+getState(code5)+"',date = '"+date+"', performer = '"+performer+"' "
                        + "where code =  '"+code5+"' and eventid = '"+event+"'";
            }
        }
        if ((code6 != null) && ((getState(code6).equals("Выполнено")) || (getState(code6).equals("Выполнено ранее")))) {
            System.out.println("1111");
            query6 = "update rezult set rezult = (select  m.`right` ||';'|| m.`left` from measures m where m.id ='" + id + "' and m.eventid = '" + event + "'), state = '" + getState(code6) + "',date = '" + date + "', performer = '" + performer + "' "
                    + "where code = '" + code6 + "' and eventid = '" + event + "'";
          }         
        try { Statement stmt = con.createStatement(); 
            try { stmt.executeUpdate(query1); } catch (SQLException e) { log.info(e,e); }
            try { stmt.executeUpdate(query2); } catch (SQLException e) { log.info(e,e); }
            try { stmt.executeUpdate(query3); } catch (SQLException e) { log.info(e,e); }
            try { stmt.executeUpdate(query4); } catch (SQLException e) { log.info(e,e); }
            try { stmt.executeUpdate(query5); } catch (SQLException e) { log.info(e,e); }
            try { stmt.executeUpdate(query6); } catch (SQLException e) { log.info(e,e); }         
        } catch (SQLException e) { log.info(e,e); }
        fillDates();
//</editor-fold>      
    }
    
    
    private void fillDates(){
        String id = patient.getId();
        String event = patient.getUuid();
        String query = "update rezult set `date`  =  strftime('%d.%m.%Y','now')"
                + " where id = '"+id+"' and `date` = '' and eventid = '"+event+"'";
        try {
         Statement stmt = con.createStatement(); 
         stmt.executeUpdate(query);        
        } catch (SQLException e) {
            log.info(e,e);
        }
    }
    
    private void AddInReadyTable(String fap){
        //<editor-fold defaultstate="collapsed" desc="Добавление id и даты прохождения диспы">
        String id = patient.getId();
        String event = patient.getUuid();
        if(checkPatientInRPTable(id,event,con) != 0){return;}
        String query = "Insert into ready_patient (id,date,fap,eventid)"
                + " values('"+id+"',strftime('%Y-%m-%d','now') , '"+fap+"' , '"+event+"') ";
        try {
            Statement stmt = con.createStatement(); 
            stmt.executeUpdate(query);        
        } catch (SQLException e) {
            log.info(e,e);
        }
        AddInEventTable();
//</editor-fold>      
    }
   
    private void AddInEventTable(){
        //<editor-fold defaultstate="collapsed" desc="Добавление кода случая диспы для пациента для ВИТЫ">
        
        String id = patient.getId();
        String event = patient.getUuid();
        if(checkPatientInEventTable(id,event,con) != 0){return;}
        String query = "Insert into vita_add_info values('"+id+"','"+event+"' , strftime('%d.%m.%Y','now')) ";
        try {
            Statement stmt = con.createStatement(); 
            stmt.executeUpdate(query);        
        } catch (SQLException e) {
            log.info(e,e);
        }
//</editor-fold>      
    }
     
    
    private String getCode(String service_code){
         //<editor-fold defaultstate="collapsed" desc="Получение кода услуги">
        String id = patient.getId();
        String event = patient.getUuid();
        String query = "select code from service s "
                + "inner join  rezult r on(s.service_code = r.service_code)"
                + " where s.service_code='"+service_code+"' and r.id = '"+id+"' and r.eventid = '"+event+"'";
        Statement post; 
        ResultSet rs;
        try{
           post = con.createStatement();
           rs = post.executeQuery(query);
           while (rs.next()){
              String code;
              code = rs.getString("code");
              return code;
           }       
        }catch (SQLException ex) {
               log.info(ex,ex);
        }return null; 
//</editor-fold> 
    }
  
    private String getState(String code){
        //<editor-fold defaultstate="collapsed" desc="Получение состояния услуги для пациента">
        String event = patient.getUuid();
        String query = "select r.state,r.service_code from  rezult r "
             + "where r.code = '"+code+"' and r.eventid = '"+event+"'" ;
        Statement post; 
        ResultSet rs;
        try{
           post = con.createStatement();
           rs = post.executeQuery(query);
           while (rs.next()){
              String state;
              state = rs.getString("state");
              if(state.equals("Назначено")){
                  state = "Выполнено";
              }
              return state;
           }       
        }catch (SQLException ex) {
               log.info(ex,ex);
        }return null; 
 }
//</editor-fold>
    
    public void insertPhoneNum(String phNum){
        String id = patient.getId();
        String query ;
        try {
            PreparedStatement pstmt ; 
            if(checkPatientInDocsTab(id,con) == 0){
                query = "INSERT INTO `patient_docs` (`id`, `phoneNum`) VALUES (?, ?); ";   
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, id);
                pstmt.setString(2,phNum);
            }else{
                query = "UPDATE `patient_docs` set phoneNum = ? where id = ?";
                pstmt = con.prepareStatement(query);
                pstmt.setString(2, id);
                pstmt.setString(1,phNum);
            }
                pstmt.executeUpdate();        
        } catch (SQLException e) {
            log.info(e);
        }
    }
    
    public int checkPatientInDocsTab(String id,Connection con){       
        //<editor-fold defaultstate="collapsed" desc="Проверка, есть ли пациент в таблице с документами">
        String query = "select id from patient_docs  where `id` = '"+id+"'";
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
    
    public int checkPatientInRPTable( String id, String eventid, Connection con){       
        //<editor-fold defaultstate="collapsed" desc="Проверка, есть ли пациент в таблице">
        String query = "select id from ready_patient  where `id` = '"+id+"' and eventid = '"+eventid+"'";
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
    
    public int checkPatientInEventTable( String id, String eventid, Connection con){       
        //<editor-fold defaultstate="collapsed" desc="Проверка, есть ли пациент в таблице">
        String query = "select id from vita_add_info  where `ID` = '"+id+"' and EVENTID = '"+eventid+"'";
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
    
    public int checkPatientInPITable( String id, String eventid, Connection con){       
        //<editor-fold defaultstate="collapsed" desc="Проверка, есть ли пациент в таблице">
        String query = "select id from personal_info_change  where `id` = '"+id+"' and eventid = '"+eventid+"'";
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
    
    public void insertPersonalInfoChange(String passport, String policy, String address,String phone){
        String id = patient.getId();
        String event = patient.getUuid();
        if(checkPatientInPITable(id,event,con) == 0){           
            String query = "INSERT INTO `personal_info_change`  VALUES (? , ? , ? , ? , ? , ?); ";
            try {
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, id);
                pstmt.setString(2,passport);
                pstmt.setString(3,policy);
                pstmt.setString(4,address);
                pstmt.setString(5,phone);
                pstmt.setString(6,event);
                pstmt.executeUpdate();        
            } catch (SQLException e) {
                log.info(e,e);
            }
        } 
    }
}
