package mdc.db.connect;

import mdc.engine.ReadConfigXml;
import java.sql.*;
import mdc.gui.JFrame;
/**
 *
 * @author Равиль
 */
public class GetConnection {
   
    private Connection Connection;
    private String driver;
    private String host;
    private String login;
    private String password;
    private ReadConfigXml config = new ReadConfigXml();
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(JFrame.class);
    public GetConnection() {
        
    }

    public void init(){ 
        config.parse();
        driver = config.getDbDriver();  
        host = config.getDbHost();
        login = config.getDbUser();
        password = config.getDbPassword();            
        
        try{       
            Class.forName(driver);
            Connection=DriverManager.getConnection(
                host,login, password
                );
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println("Failed to get connection");
            log.info(e,e);
        }
     
    }
    
    
    
    public Connection getConnection(){
        return Connection;
    }
    
    
    public void close(ResultSet rs){       
        if(rs !=null){
            try{
               rs.close();
            }
            catch(SQLException e){
                log.info(e,e);
            }       
        }
    }
    
     public void close(Statement stmt){
        
        if(stmt !=null){
            try{
               stmt.close();
            }
            catch(Exception e){}
        
        }
    }
     
  public void destroy(){  
    if(Connection !=null){   
         try{
               Connection.close();
               System.out.println("closing");
            }
            catch(Exception e){}        
    }
  }
}
