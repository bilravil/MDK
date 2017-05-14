/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.engine.filltable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.PatientData;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

/**
 *
 * @author Равиль
 */
public class FillBarcodeTable {
    String base;
    PatientData patient;
    private static final Logger log = Logger.getLogger(FillBarcodeTable.class);
    
    public FillBarcodeTable(PatientData patient) {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
        this.patient = patient;
    }
      
      
    public ResultSet FillBar(Connection con){
        //<editor-fold defaultstate="collapsed" desc="Вывод наименований услуг для печати штрихкодов">
        String event = patient.getUuid();
        String id = patient.getId();
        String query = "select service_name as 'Наименование' , button as '',button as 'Печать'  from for_print s "
        + "inner join rezult r on (s.service_code = r.service_code) "
        + "inner join service_barcode sb on (sb.service_bar = r.service_code) "
        + "where `r`.`id`='"+id+"' and `r`.`state` = 'Назначено' and r.eventid = '"+event+"'"
        + "order by s.service_name";
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

    public ResultSet FillDirect(Connection con){
        //<editor-fold defaultstate="collapsed" desc="Вывод наименований услуг для пеати направлений">
        String event = patient.getUuid();
        String id = patient.getId();
        String query = "select service_name as 'Наименование', button as '',button as 'Печать'  from for_print s "
        + "inner join rezult r on (s.service_code = r.service_code) "
        + "inner join service_barcode sb on (sb.service_direct = r.service_code) "
        + "where `r`.`id`='"+id+"' and `r`.`state` = 'Назначено' and r.eventid = '"+event+"'";
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
 
}