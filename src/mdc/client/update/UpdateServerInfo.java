package mdc.client.update;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

public class UpdateServerInfo
{
  private static final Logger log = Logger.getLogger(UpdateServerInfo.class);
  
  public UpdateServerInfo()
  {
    ReadConfigXml config = new ReadConfigXml();
    config.parse();
  }
  
  public void updateRequireDate(Connection con, String date)
  {
    try
    {
      String query = "update server_info set last_request = '" + date + "' where id =1";
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
    }
    catch (SQLException e)
    {
      log.info(e, e);
    }
  }
  
  public String getLastRequireDate(Connection con)
  {
    String query = "select last_request from server_info where id = 1";
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
      log.info(e, e);
    }
    return "";
  }
}
