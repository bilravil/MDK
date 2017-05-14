package mdc.engine.filltable;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import mdc.gui.JFrame;

/**
 *
 * @author MDK
 */
public class FillFapTable {
    String base;
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(JFrame.class);

    public FillFapTable() {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
    }
    
    
    public ResultSet FillFapTable(Connection con,String crb){
           //<editor-fold defaultstate="collapsed" desc="Получение ФАПов опред ЦРБ">
       String query = "select name as 'Наименование' from fap where new_reg_cod like('"+crb+"%')";
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
   }
//</editor-fold>     
    
    public String getFapCode(Connection con,String fapName){
        String query = "select new_reg_cod from fap where name = '"+fapName+"'";
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            rs = pstmt.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        } catch (Exception ex) {
            log.info(ex,ex);
        }
        return "";
    }
}
