package mdc.client.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

public class UpdatePatientTable
{
  private static final Logger log = Logger.getLogger(UpdatePatientTable.class);
  
  public UpdatePatientTable()
  {
    ReadConfigXml config = new ReadConfigXml();
    config.parse();
  }
  
  public void UpdatePatientTable(Connection con, String tisID, String surname, String name, String midlname, String birthdate, int sex, String crb)
  {
    try
    {
      String id = getPatientID(con, tisID);
      if (CheckTisPatientInPatientTab(tisID, con) == 0)
      {
        id = getID(String.valueOf(getNextID(con, crb) + 1), crb);
        String query = "insert into patient values (?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, surname);
        pstmt.setString(3, name);
        pstmt.setString(4, midlname);
        pstmt.setString(5, birthdate);
        pstmt.setString(6, getSex(sex));
        pstmt.setString(7, "");
        pstmt.setString(8, "");
        pstmt.setString(9, crb);
        pstmt.executeUpdate();
        insertTisId(con, id, tisID);
      }
      else
      {
        String query = "update  patient set  surname = ? , name = ?, middle_name = ? , crb_id = ? where id = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, surname);
        pstmt.setString(2, name);
        pstmt.setString(3, midlname);
        pstmt.setString(4, crb);
        pstmt.setString(5, id);
        pstmt.executeUpdate();
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
  }
  
  public void UpdatePatientTable(Connection con, String id, String surname, String name, String midlname, String birthdate, String sex, String address, String lpu, String crb)
  {
    try
    {
      if (CheckPatientInPatientTab(id, con) == 0)
      {
        String query = "insert into patient values (?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, surname);
        pstmt.setString(3, name);
        pstmt.setString(4, midlname);
        pstmt.setString(5, birthdate);
        pstmt.setString(6, sex);
        pstmt.setString(7, address);
        pstmt.setString(8, lpu);
        pstmt.setString(9, crb);
        pstmt.executeUpdate();
      }
      else
      {
        String query = "update  patient set  surname = ? , name = ?, middle_name = ? , birth_date = ?, sex = ?, address = ? , lpu_id = ?, crb_id = ? where id = ?";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        
        pstmt.setString(1, surname);
        pstmt.setString(2, name);
        pstmt.setString(3, midlname);
        pstmt.setString(4, birthdate);
        pstmt.setString(5, sex);
        pstmt.setString(6, address);
        pstmt.setString(7, lpu);
        pstmt.setString(8, crb);
        pstmt.setString(9, id);
        pstmt.executeUpdate();
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
  }
  
  private void insertTisId(Connection con, String id, String tisId)
  {
    String query = "Insert into tis_patient_id values(?,?)";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, id);
      post.setString(2, tisId);
      post.executeUpdate();
    }
    catch (Exception ex)
    {
      log.info(ex, ex);
    }
  }
  
  private String getSex(int sex)
  {
    switch (sex)
    {
    case 1: 
      return "М";
    case 2: 
      return "Ж";
    }
    return "";
  }
  
  private int CheckTisPatientInPatientTab(String id, Connection con)
  {
    String query = "select id from tis_patient_id  where tis_id = '" + id + "'";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      ResultSet rs = post.executeQuery();
      if (!rs.isBeforeFirst()) {
        return 0;
      }
      return -1;
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return -1;
  }
  
  public int CheckPatientInPatientTab(String id, Connection con)
  {
    String query = "select id from patient r where `r`.`id` =" + id;
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      ResultSet rs = post.executeQuery();
      if (!rs.isBeforeFirst()) {
        return 0;
      }
      return -1;
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return -1;
  }
  
  private String getID(String txt, String crb)
  {
    String crbID = crb.substring(2, 6);
    txt = crbID + txt;
    if (txt.length() != 10) {
      while (txt.length() != 10) {
        txt = txt.substring(0, 4) + "0" + txt.substring(4, txt.length());
      }
    }
    return txt;
  }
  
  private int getNextID(Connection con, String crb)
  {
    String crbID = crb.split("\\.")[0];
    String query = "SELECT count(id) FROM patient where crb_id like ('" + crbID + "%');";
    try
    {
      Statement post = con.createStatement();
      ResultSet rs = post.executeQuery(query);
      if (rs.next()) {
        return rs.getInt(1);
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return 0;
  }
  
  private String getPatientID(Connection con, String tisID)
  {
    String query = "SELECT id FROM tis_patient_id WHERE tis_id = '" + tisID + "'";
    try
    {
      Statement pstmt = con.createStatement();
      ResultSet rs = pstmt.executeQuery(query);
      if (rs.next()) {
        return rs.getString(1);
      }
    }
    catch (Exception e)
    {
      log.info(e);
    }
    return "";
  }
}
