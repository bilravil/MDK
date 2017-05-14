package mdc.device.print;


import mdc.engine.ReadConfigXml;
import com.onbarcode.barcode.Code128;
import java.awt.*;
import java.awt.print.*;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocPrintJob;
import javax.print.PrintService;


public class PrintBarcode implements Printable {
    private final String id;
    private final String name;
    private final String birth_date;
    private final String code;
    private final String serv_name;
    private final String cur_date;
    private ReadConfigXml config = new ReadConfigXml();
    private FileInputStream fis;
    private Properties property = new Properties(); 
    private String crbID;
    private String printerName = "";
    
    public PrintBarcode(String id, String name, String birth_date, String code, String serv_name, String cur_date) {
        config.parse();
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.code = code;
        this.serv_name = serv_name;
        this.cur_date = cur_date;       
        printerName = config.getPrinterName();
        
    }
    
    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
         if (page > 0) { 
            return NO_SUCH_PAGE;
        }
        
        Code128 barcode = new Code128(); 
        barcode.setData(code); 
        barcode.setShowText(false); 
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());        
        g.setFont(new java.awt.Font("Tahoma",0,9));      
        g.drawString(name, 10, 15);
        g.drawString(birth_date,10,25);
        g.drawString(cur_date, 10, 35);
        g.drawString(serv_name, 10, 45);
        if(!code.equals("null")){
           g.drawString(code, 10, 55);  
         }
        
        if(!code.equals("null")){
          try {
            g.drawImage(barcode.drawBarcode(), 15, 70, null);
        } catch (Exception ex) {
            Logger.getLogger(PrintBarcode.class.getName()).log(Level.SEVERE, null, ex);
        }
         }
             
        return PAGE_EXISTS;       
    }
    
    
    public void PrintBarcode(){
        Paper paper = new Paper();
        paper.setSize(58, 40);
        paper.setImageableArea(10, 10, 0, 0); 
        PageFormat format = new PageFormat();
        format.setOrientation(PageFormat.PORTRAIT);
        format.setPaper(paper);
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this, format);
        PrintService[] service = PrinterJob.lookupPrintServices();
        
        DocPrintJob docPrintJob = null;
        int count = service.length;
        
        for (int i = 0; i < count; i++) {
            if (service[i].getName().equalsIgnoreCase(printerName)) {               
                docPrintJob = service[i].createPrintJob();
                i = count;
            }
        }
        
         try {
             
             printJob.setPrintService(docPrintJob.getPrintService());
             printJob.setPrintable(this, format);
             printJob.print();
         } catch (PrinterException ex) {
             System.out.println(ex);
         }

    }
}