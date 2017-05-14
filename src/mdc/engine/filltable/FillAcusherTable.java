package mdc.engine.filltable;

import mdc.engine.ReadConfigXml;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FillAcusherTable {
   
    String base;

    public FillAcusherTable() {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
    }
    
    public ResultSet FillAcusher(Connection con,String crb){
        //<editor-fold defaultstate="collapsed" desc="Заполенение таблицы с акушерами ">
        String query = "select docID,docName  from doctor_directory where crbID like ('"+crb+"%')";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            
            return rs;
        }catch (SQLException ex) {
            System.out.println(ex);
    }        
       return null;
//</editor-fold>      
   }
    
    public String getAcusherID(Connection con,String id){
        //<editor-fold defaultstate="collapsed" desc="Получение docID для акушера">
        String query = "select docID from doctor_directory where docID = '"+id+"'";
        Statement post; 
        ResultSet rs;
        try{
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex);
    }        
       return null;
   }
//</editor-fold>       
}
