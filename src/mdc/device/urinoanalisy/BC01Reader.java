package mdc.device.urinoanalisy;

import java.sql.Connection;
import javax.swing.JButton;
import jssc.SerialPort;
import mdc.engine.PatientData;
import mdc.engine.filltable.FillRezultTable;
import mdc.gui.JFrame;
import org.apache.log4j.Logger;



public class BC01Reader extends Thread{
    private static final Logger log = Logger.getLogger(BC01Reader.class);
    private  PatientData patient;
    private Connection con;
    private FillRezultTable fill;
    private JFrame frame;

    public BC01Reader(PatientData patient, Connection con, FillRezultTable fill, JFrame frame) {
        this.patient = patient;
        this.con = con;
        this.fill = fill;
        this.frame = frame;
    }
    
    
    
    @Override
    public  void run() {
        
        SearchEvent searchEvent = new SearchEvent();
        DeviceManager manager = new DeviceManager(searchEvent);      
        
        manager.findAllDevices();
        String addr = manager.selectDevice();
        if (addr == null) {
            log.info("No device selected. Aborted.");
            return;
        }

        String connectUrl = manager.discoverServices(addr);
        if (connectUrl == null) {
            log.info("[warning] Cannot find any capability services this device provide. Aborting.");
            return;
        }
        String result = manager.connectToDevice(connectUrl);
        String[] arr = result.split(";");
        
        fill.addBC01Analis(con, arr, patient);
        frame.updateResultTable();
        frame.addResBut.setEnabled(true); 
    }
}