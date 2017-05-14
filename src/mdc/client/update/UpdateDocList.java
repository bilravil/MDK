package mdc.client.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class UpdateDocList
{
  private static final Logger log = Logger.getLogger(UpdateDocList.class);
  
  public void AddNewDoctor(Connection con, String id, String docName, String snils, String v002, String v015, String crb)
  {
    try
    {
      if (CheckDocInDocTab(id, con) == 0)
      {
        String query = "INSERT INTO `doctor_directory` VALUES (?,?,?,?,?,?) ;";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, docName);
        pstmt.setString(3, snils);
        pstmt.setString(4, v002);
        pstmt.setString(5, v015);
        pstmt.setString(6, crb);
        pstmt.executeUpdate();
      }
      else
      {
        String query = "update  `doctor_directory` set docName = ? ,SNILS = ? , V002 = ?, V015 = ? , crbID = ? where docID = ? ;";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, docName);
        pstmt.setString(2, snils);
        pstmt.setString(3, v002);
        pstmt.setString(4, v015);
        pstmt.setString(5, crb);
        pstmt.setString(6, id);
        pstmt.executeUpdate();
      }
    }
    catch (SQLException|NumberFormatException e)
    {
      log.info(e, e);
    }
  }
  
  public void AddNewDoctor(Connection con, String id, String docName, String crb)
  {
    try
    {
      if (CheckDocInDocTab(id, con) == 0)
      {
        String query = "INSERT INTO `doctor_directory`(docID,docName,crbID) VALUES (?,?,?) ;";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, id);
        pstmt.setString(2, docName);
        pstmt.setString(3, crb);
        pstmt.executeUpdate();
      }
      else
      {
        String query = "update  `doctor_directory` set docName = ? , crbID = ? where docID = ? ;";
        
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, docName);
        pstmt.setString(2, crb);
        pstmt.setString(3, id);
        pstmt.executeUpdate();
      }
    }
    catch (SQLException|NumberFormatException e)
    {
      log.info(e, e);
    }
  }
  
  public int CheckDocInDocTab(String id, Connection con)
  {
    String query = "select * from doctor_directory  where docID = '" + id + "'";
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
}
