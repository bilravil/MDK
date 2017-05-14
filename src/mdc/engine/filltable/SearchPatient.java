package mdc.engine.filltable;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mdc.engine.ReadConfigXml;
import org.apache.log4j.Logger;

public class SearchPatient
{
  String base;
  private static final Logger log = Logger.getLogger(SearchPatient.class);
  ReadConfigXml config;
  public SearchPatient()
  {
    config = new ReadConfigXml();
    config.parse();
    this.base = config.getBaseName();
  }
  
  public ResultSet searchPatient(String txt, Connection con){
    txt = txt.toUpperCase();
    String query = "Select *from patient_view where \"ФИО\" like '%" + txt + "%' ";
    if (txt.isEmpty()) {  query = "Select *from patient_view  "; }
    if (txt.contains(" "))  {
        String s = txt.split(" ")[0].toUpperCase();
        String n = txt.split(" ")[1].toUpperCase();
        String m = txt.split(" ")[2].toUpperCase();
        query = "SELECT id as id, surname || ' ' || name || ' ' || middle_name as 'ФИО' ,birth_date as 'Дата рождения', sex as 'Пол',address as 'Адрес' from patient  where surname like '" + s + "%' and name like '" + n + "%' and middle_name like '" + m + "%'";
    }
    if ((!txt.contains(" ")) && (txt.length() == 9)){
        String surname = txt.substring(0, 1).toUpperCase();
        String name = txt.substring(1, 2).toUpperCase();
        String middle = txt.substring(2, 3).toUpperCase();
        String dr = txt.substring(3);
        query = "SELECT id as id, surname || ' ' || name || ' ' || middle_name as 'ФИО' ,birth_date as 'Дата рождения', sex as 'Пол',address as 'Адрес' from patient where  surname like '" + surname + "%' and name like '" + name + "%' and middle_name like '" + middle + "%' and  (substr(replace(birth_date,'.',''),1,4) || substr(replace(birth_date,'.',''),7,8)) = '" + dr + "' ";
    }
    try {
        Statement post = con.createStatement();
        return post.executeQuery(query);
    }
    catch (SQLException ex){
        log.info(ex, ex);
    }
    return null;
  }
  
  public String getSurname(String id, Connection con) {
    String query = "Select surname from patient where id = ?";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, id);
      ResultSet rs = post.executeQuery();
      if (rs.next())
      {
        String surname = rs.getString("surname");
        System.out.println(surname);
        return surname;
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return null;
  }
  
  public String getName(String id, Connection con)
  {
    String query = "Select name from patient where id = ?";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, id);
      ResultSet rs = post.executeQuery();
      if (rs.next())
      {
        String name = rs.getString("name");
        System.out.println(name);
        return name;
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return null;
  }
  
  public String getMiddleName(String id, Connection con)
  {
    String query = "Select middle_name from patient where id = ?";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, id);
      ResultSet rs = post.executeQuery();
      if (rs.next())
      {
        String middlename = rs.getString("middle_name");
        System.out.println(middlename);
        return middlename;
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return null;
  }
  
  public String getPhoneNum(String id, Connection con)
  {
    String query = "Select phoneNum from patient_docs where id = ?";
    try
    {
      PreparedStatement post = con.prepareStatement(query);
      post.setString(1, id);
      ResultSet rs = post.executeQuery();
      if (rs.next())
      {
        String phone = rs.getString("phoneNum");
        return phone;
      }
    }
    catch (SQLException ex)
    {
      log.info(ex, ex);
    }
    return null;
  }
}
