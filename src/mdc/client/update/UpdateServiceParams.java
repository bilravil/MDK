package mdc.client.update;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class UpdateServiceParams
{
  private static final Logger log = Logger.getLogger(UpdateServiceParams.class);
  
  public void UpdateServiceParamsTable(Connection con, String id, String mnemonic, String print)
  {
    try
    {
      String query = "insert into service_params values ('" + id + "','" + mnemonic + "','" + print + "')";
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
  }
  
  public void InsertServiceUuid(Connection con, String code, String uuid, String parser)
  {
    try
    {
      String query = "update service set service_uuid = '" + uuid + "', parser = '" + parser + "' where service_code = '" + code + "'";
      
      Statement stmt = con.createStatement();
      stmt.executeUpdate(query);
    }
    catch (SQLException ex)
    {
      log.info(ex);
    }
  }
}
