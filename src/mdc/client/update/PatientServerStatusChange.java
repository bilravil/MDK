package mdc.client.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

public class PatientServerStatusChange
{
  private Connection con;
  private ReadConfigXml config = new ReadConfigXml();
  String base = this.config.getBaseName();
  private static final Logger log = Logger.getLogger(PatientServerStatusChange.class);
  
  public PatientServerStatusChange(Connection con)
  {
    this.con = con;
    this.config.parse();
  }
  
  public void UpdateStatus(String id)
  {
    try
    {
      String eventid = getEventID(id);
      String query = "UPDATE `ready_patient`  SET `on_server` = 'yes' WHERE `id`= '" + id + "' and eventid = '" + eventid + "';";
      Statement stmt = this.con.createStatement();
      stmt.executeUpdate(query);
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
  }
  
  public String getEventID(String id)
  {
    String query = "SELECT eventid FROM ready_patient where id = '" + id + "' and strftime('%Y',date) = strftime('%Y','now');";
    try
    {
      PreparedStatement post = this.con.prepareStatement(query);
      ResultSet rs = post.executeQuery();
      if (rs.next()) {
        return rs.getString(1);
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
      return "";
    }
    ResultSet rs;
    PreparedStatement post;
    return null;
  }
}
