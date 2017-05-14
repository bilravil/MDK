/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.engine;


import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
/**
 *
 * @author Равиль
 */
public class AddPatient {
       static final String AB = "АБВГДЕЗИКЛМНОПРСТУФЯ";
       static final String MJ = "МЖ";
       static SecureRandom rnd = new SecureRandom(); 
    
    private SecureRandom random = new SecureRandom();
    
       public void AddPatient(Connection con){
       String name;
       String surname;
       String midname;
       String sex;
       String address;
       String birth;       
       for(int i = 1; i< 1000; i++){ 
       try{
           name = randomName(1);
           surname = randomName(1);
           midname = randomName(1);
           address = randomName(10);
           sex = randomSex(1);
           birth = randomBirth();
            String query = "insert into mdk_server.patient_list values ('"+i+"','"+surname+"','"+name+"','"+midname+"','"+birth+"','"+sex+"','"+address+"')";
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);         
        }catch (SQLException ex) {
            System.out.println(ex);
    }}   
   } 
        String randomName( int len ){     
           StringBuilder sb = new StringBuilder( len );    
           for( int i = 0; i < len; i++ ) 
           sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
           return sb.toString();
           }

        String randomSex( int len ){     
           StringBuilder sb = new StringBuilder( len );    
           for( int i = 0; i < len; i++ ) 
           sb.append( MJ.charAt( rnd.nextInt(MJ.length()) ) );
           return sb.toString();
           } 


        String  randomBirth(  ){  
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1930, 1995);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return (gc.get(gc.DAY_OF_MONTH) + "." + (gc.get(gc.MONTH) + 1) + "." + gc.get(gc.YEAR));
   
}


    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
       
}
