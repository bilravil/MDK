package mdc.engine;

import java.sql.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Равиль
 */
public class Autorization {
    String base;
    private static final Logger log = Logger.getLogger(Autorization.class);
    
    public Autorization() {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        base = config.getBaseName();
    }
    
    
    
    public boolean login(String login, String pass,Connection conn) {
        String query = "select * from users where username = '"+login+"' and pass = ('"+pass+"')";
        Statement post; 
        ResultSet rs;
        try{
            post = conn.createStatement();
            rs = post.executeQuery(query);
            if(rs.next()){
                return login.equals(rs.getString("username"));
            }
        }catch (SQLException ex) {   
            log.info(ex,ex);
    }
    return false; 
}
    
}