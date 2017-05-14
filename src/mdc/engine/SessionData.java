/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.engine;

/**
 *
 * @author Равиль
 */
public class SessionData {
    
    private String fap;
    private String acusher;
    private String crbID;
    
    public SessionData() {
        ReadConfigXml config = new ReadConfigXml();
        config.parse();
        try {
            this.crbID = config.getCrbID();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void setFap(String fap) {
        this.fap = fap;
    }

    public void setAcusher(String acusher) {
        this.acusher = acusher;
    }

    public String getFap() {
        return fap;
    }

    public String getAcusher() {
        return acusher;
    }

    public String getCrbID() {
        return crbID;
    }
    
}
