package mdc.client.update;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class UpdateFapTable
{
  private static final Logger log = Logger.getLogger(UpdateFapTable.class);
  
  public void updateFapTable(Connection con, String old_cod, String name, String new_cod)
  {
    try
    {
      if (checkFapInFapTab(new_cod, con) == 0)
      {
        String query = "insert into fap values ('" + old_cod + "','" + name + "','" + new_cod + "')";
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query);
      }
      else
      {
        String query = "update fap set old_reg_cod = ?, name = ?  where new_reg_cod = ?";
        
        PreparedStatement post = con.prepareStatement(query);
        post.setString(1, old_cod);
        post.setString(2, name);
        post.setString(3, new_cod);
        post.executeUpdate();
      }
    }
    catch (SQLException ex)
    {
      System.out.println(ex);
    }
  }
  
  public void updateClinicTable(Connection con, String uuid, String code, String name)
  {
    try
    {
      String query = "insert into clinic values (?,?,?)";
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, uuid);
      post.setString(2, code);
      post.setString(3, name);
      post.executeUpdate();
    }
    catch (SQLException ex)
    {
      log.info(ex);
    }
  }
  
  private int checkFapInFapTab(String id, Connection con)
  {
    String query = "select f.new_reg_cod from fap f where `f`.`new_reg_cod` =" + id;
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
  
  public String getClinicUuid(Connection con, String code)
  {
    String query = "select uuid from clinic  where code like ('" + code + "%') ";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      ResultSet rs = post.executeQuery();
      if (rs.next()) {
        return rs.getString(1);
      }
      return null;
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return null;
  }
}
