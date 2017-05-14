package mdc.device.urinoanalisy;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import mdc.engine.PatientData;
import mdc.engine.filltable.FillRezultTable;
import mdc.gui.JFrame;
import org.apache.log4j.Logger;


public class DocUReader {  /*Класс чтения из порта для уриноанализатора*/
   private static SerialPort serialPort; 
   private  ArrayList arr = new ArrayList();
   private  ArrayList arr1 = new ArrayList();
   private  PatientData patient;
   private Connection con;
   private FillRezultTable fill;
   private static String data = "";
   private static String data1 = "";
   public boolean flag = false;
   private JFrame frame;
   private int bufferCount = 0;
   private static final Logger log = Logger.getLogger(DocUReader.class);
    
    public DocUReader(Connection con,FillRezultTable fill,PatientData patient,JFrame frame) {
        this.con = con;
        this.fill = fill;
        this.patient = patient;
        this.frame = frame;
    }

    public DocUReader() {
    }
    
    
    public static String getFriendlyName(String registryKey) {
    if (registryKey == null || registryKey.isEmpty()) {
        throw new IllegalArgumentException("'registryKey' null or empty");
    }
    try {
        int hkey = WinRegistry.HKEY_LOCAL_MACHINE;
        return WinRegistry.readString(hkey, registryKey, "FriendlyName");
    } catch (Exception ex) { // catch-all: 
        // readString() throws IllegalArg, IllegalAccess, InvocationTarget
        log.info(ex,ex);
        return null;
    }
}


public static int getComNumber(String registryKey) {
    String friendlyName = getFriendlyName(registryKey);
    if (friendlyName != null && friendlyName.indexOf("COM") >= 0) {
        String substr = friendlyName.substring(friendlyName.indexOf("COM"));
        Matcher matchInt = Pattern.compile("\\d+").matcher(substr);
        if (matchInt.find()) {
            return Integer.parseInt(matchInt.group());
        }
    }
    return -1;
}  
    
    
   public String  getPort(){
        String[] portNames = SerialPortList.getPortNames();       
        
        String keyPath = "SYSTEM\\CurrentControlSet\\Enum\\USB\\VID_0525&PID_A4A7\\";
        String device1 = "6&1d7e6c28&0&1";
        String device2 = "6&1d7e6c28&0&2";
        String device3 = "6&1d7e6c28&0&3";
        
        String com1 = String.valueOf("COM"+getComNumber(keyPath + device1));
        String com2 = String.valueOf("COM"+getComNumber(keyPath + device2));
        String com3 = String.valueOf("COM"+getComNumber(keyPath + device3));
        for(int i = 0; i < portNames.length; i++){
            if (portNames[i].equals(com1))
                return com1;
            if (portNames[i].equals(com2))
                return com2;
            if (portNames[i].equals(com3))
                return com3;
        }        
        return null;
   }
   
   public void run() throws SerialPortException{
       // TODO COM PORT!!!!
       serialPort = new SerialPort (getPort()); /*Передаем в конструктор суперкласса имя порта с которым будем работать*/
        try {
            if(serialPort.isOpened()){               
               serialPort.closePort();
            }
            if(!serialPort.isOpened()){
                bufferCount = 0;
                serialPort.openPort (); /*Метод открытия порта*/
                serialPort.setParams (SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); /*Задаем основные параметры протокола UART*/
                serialPort.setEventsMask (SerialPort.MASK_RXCHAR); /*Устанавливаем маску или список события на которые будет происходить реакция. В данном случае это приход данных в буффер порта*/                       
                serialPort.addEventListener (new EventListener ()); /*Передаем экземпляр класса EventListener порту, где будет обрабатываться события. Ниже описан класс*/
                closePort();
            }  
            }
        catch (SerialPortException ex) {
            log.info(ex,ex);
            
            JOptionPane.showMessageDialog(null, "Порт уже открыт.Ожидаются данные с уриноанализатора.");
        } 
   }
   
   public void closePort(){
       try {
           if(serialPort.isOpened()){  
               ButtonSetEnable set = new ButtonSetEnable(frame.addResBut,serialPort);
               set.start();              
            }
       } catch (Exception e) {
           log.info(e);
       }
   }

    private  class EventListener implements SerialPortEventListener { /*Слушатель срабатывающий по появлению данных на COM-порте*/
        @Override
        public void serialEvent (SerialPortEvent event) {
           
            if (event.isRXCHAR () && event.getEventValue () > 330){ /*Если происходит событие установленной маски и количество байтов в буфере более 0*/
                try {                 
                          String enc = "";
                          data = serialPort.readString (event.getEventValue ()); /*Создаем строковую переменную  data, куда и сохраняем данные*/
                          //log.info(data);
                          try {
                            enc = new String(data.getBytes(),"UTF-8");
                            } catch (UnsupportedEncodingException ex) {
                                log.info(ex,ex);
                            }
                          Pattern p = Pattern.compile("\"([^\"]*)\"");
                          Matcher m = p.matcher(enc);                          
                           while (m.find()) {
                                String d = m.group(1);                                
                                arr.add(d);
                                //System.out.println(d);
                                 }  
                           
                            fill.addDocUReaderAnalis(con, arr, patient);                           
                            serialPort.closePort();
                            frame.updateResultTable();
                            frame.addResBut.setEnabled(true);                             
                       }
                
                catch ( SerialPortException ex) {
                    log.info(ex);
                }                          
            }
        }
    }
    
    
    public class ButtonSetEnable extends Thread{
        private JButton button;
        private SerialPort serialPort;
        public ButtonSetEnable(JButton button, SerialPort serialPort) {
            this.button = button;
            this.serialPort = serialPort;
        }
        
        
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                button.setEnabled(true);
                if(serialPort.isOpened()){               
                    serialPort.closePort();
                }
                
            } catch (Exception ex) {
                System.out.println(ex);
            }
            
          }

}
}



