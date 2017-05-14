
package mdc.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author Равиль
 */
public class CheckServiceState {
    
    private final PatientData patient;
    private static final Logger log = Logger.getLogger(CheckServiceState.class);
    public CheckServiceState(PatientData patient) {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        this.patient = patient;
    }   
    
    public void getChangeState(Connection con){
        String id = patient.getId();
        String query = "SELECT service_name FROM rezult r "
                + "inner join service s on(r.service_code = s.service_code)"
                + "where r.state = 'Выполнено ранее' and r.id = '"+id+"' ";
        ResultSet rs;
        Statement post;
        patient.getService_state().removeAll(patient.getService_state());
        try {
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
                String name = rs.getString("service_name");
                patient.getService_state().add(name);
                
            }
            
        } catch (SQLException e) {
            log.info(e);
        }
    }
    
    public String getServiceInCode(Connection con,String code){
        String query = "SELECT service_incode FROM service s where s.service_code = '"+code+"' or s.service_name = '"+code+"' ";
        ResultSet rs;
        Statement post;
        try {
            post = con.createStatement();
            rs = post.executeQuery(query);
            while(rs.next()){
                return rs.getString(1);                
            }            
        } catch (SQLException e) {
            log.info(e,e);
            return "";
        }
        return "";
    }
}
