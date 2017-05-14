
package mdc.engine;

import java.util.ArrayList;



public class PatientData {
    
    private String id;
    private String full_name;
    private String surname;
    private String nname;
    private String middleName;
    private String uuid;
    private String sex;
    private String adress;
    private ArrayList service_code = new ArrayList();
    private ArrayList<String> service_state = new ArrayList();
    
    public PatientData() {
               
    }
    public String getUuid() {
        return uuid;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    private String birth_date;   

    public String getSurname() {
        return surname;
    }

    public String getNname() {
        return nname;
    }

    public String getMiddleName() {
        return middleName;
    }
    

    public ArrayList getService_state() {
        return service_state;
    }

    public String getId() {
        return id;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getBirth_date() {
        return birth_date;
    }
    
    public String getName() {
        return full_name;
    }

    public String getSex() {
        return sex;
    }

    public String getAdress() {
        return adress;
    }

    public ArrayList getArr() {
        return service_code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.full_name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setArr(ArrayList arr) {
        this.service_state = arr;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    
}
