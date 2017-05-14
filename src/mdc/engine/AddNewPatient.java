/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Равиль
 */
public class AddNewPatient {
    
    private Connection con;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    String base;
    public AddNewPatient(Connection con) {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
        this.con = con;
    }
    
    public void addPatient(String surname,String name,String middle,String sex,String birth,String adress,String lpu_id,String crbID){
        String query = "INSERT INTO `"+base+"`.`patient` (`id`, `surname`, `name`, `middle_name`, `birth_date`, `sex`, `address`, `lpu_id`,`crb_id`) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?);";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            int id = Integer.parseInt(getNextID()) + 1;
            setId(id);
            pstmt.setInt(1,id);
            pstmt.setString(2,surname);  
            pstmt.setString(3,name);
            pstmt.setString(4,middle);
            pstmt.setString(5,birth);
            pstmt.setString(6,sex);
            pstmt.setString(7,adress);
            pstmt.setString(8,lpu_id);
            pstmt.setString(9, crbID);
            //method to insert a stream of bytes
            System.out.println("Пациент с "+ id + " добавлен");
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void addPatientDocs(String snils,int seria,int num,String oldPol, String newPol,String phone){
        String query = "INSERT INTO `"+base+"`.`patient_docs` (`id`, `snils`, `pass_ser`, `pass_num`,old_police,new_police,phoneNum) "
                + "VALUES (?,?,?,?,?,?,?) ";        
        PreparedStatement pstmt;         
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,getId());
            pstmt.setString(2,snils);  
            pstmt.setInt(3,seria);
            pstmt.setInt(4,num);
            pstmt.setString(5,oldPol); 
            pstmt.setString(6,newPol); 
            pstmt.setString(7,phone); 
            System.out.println("Док-ты пациент с  id"+ id + " добавлены");
            pstmt.executeUpdate(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public String getNextID(){
      String query = "SELECT count(id) FROM `"+base+"`.patient;";
      PreparedStatement post; 
      ResultSet rs;
      String count;
        try{
            post = con.prepareStatement(query);
            rs = post.executeQuery(query);
            while(rs.next()){
              return count = "99999"+(rs.getString(1));
            }   
        }catch (SQLException ex) {
            System.out.println(ex);
    } return "";
    }
}
