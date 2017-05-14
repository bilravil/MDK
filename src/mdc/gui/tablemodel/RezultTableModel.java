package mdc.gui.tablemodel;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import mdc.engine.PatientData;
import mdc.engine.filltable.FillRezultTable;
import mdc.gui.tools.Translate;
import org.apache.log4j.Logger;


public class RezultTableModel extends AbstractTableModel {
    
        // здесь мы будем хранить названия столбцов 
    private final ArrayList columnNames = new ArrayList(); 
    // список типов столбцов 
    private final ArrayList columnTypes = new ArrayList(); 
    // хранилище для полученных данных из базы данных 
    private final ArrayList data = new ArrayList(); 
    private final JTable table1;
    private final JTable table2;
    private final Connection con;
    private FillRezultTable fill;
    private final PatientData patient;
    private final Translate trans = new Translate();
    // конструктор позволяет задать возможность редактирования 
    
    private static final Logger log = Logger.getLogger(RezultTableModel.class);
    
    public RezultTableModel(boolean editable,Connection con, JTable table1,JTable table2,PatientData patient) {
        this.editable = editable;
        this.con = con;
        this.table1 = table1;
        this.table2 = table2;
        this.patient = patient;
        fill = new FillRezultTable(patient);
    }
    
    private final boolean editable; 
    // количество строк 

    
    @Override
    public int getRowCount() {
        synchronized (data) {
        return data.size();
        } 
    } 
    // количество столбцов 
    @Override
    public int getColumnCount() {
        return columnNames.size();
    } 
    // тип данных столбца 
    @Override
    public Class getColumnClass(int column) {
        return (Class)columnTypes.get(column);
    } 
    // название столбца 
    @Override
    public String getColumnName(int column) {
        return (String)columnNames.get(column);
    } 
    // данные в ячейке 
    @Override
    public Object getValueAt(int row, int column) {            
        //check(row);
        synchronized (data) {
            table1.getColumnModel().getColumn(0).setHeaderValue("Параметр");
            table1.getColumnModel().getColumn(1).setHeaderValue("Значение");    
            return ((ArrayList)data.get(row)).get(column); 
        }
             
    } 
    // возможность редактирования 
    @Override
    public boolean isCellEditable(int row, int column) {
        switch (column) {
         case 0: return false;
         case 1:
             return true;
         default:
             return false;
      }
    } 
    
    
    @Override
    public void setValueAt( Object value, int row, int column){       
         try {
            synchronized (data) { 
            ((ArrayList)data.get(row)).set(column, getInputData(row, value.toString()));
            if(!value.toString().equals("Некорр данные")){          
                String param = ((ArrayList)data.get(row)).get(0).toString();
                String rez =((ArrayList)data.get(row)).get(column).toString();
                String col = trans.translate(param);
                updateData(row, getMainRow(),rez, col); 
                JTextField txt = new JTextField();
                txt.setDocument(new JTextFieldLimit(16)); 
                txt.setFont(new java.awt.Font("Dialog", 0, 14));
                TableColumn tc = table1.getColumnModel().getColumn(1);       
                TableCellEditor tce = new DefaultCellEditor(txt);
                tc.setCellEditor(tce); 
                }              
            } 
        } catch (Exception e) {
            
        }       
    }
    
    public String getMainRow(){
        int mainrow = table2.getSelectedRow();
        return  table2.getValueAt(mainrow, 0).toString();       
}
    
    public void updateData(int row,String name,String rez,String col){
            String id = patient.getId();
            //String code = fill.getServiceCode(con, id, name);
            fill.updateMeasures(con, col, rez, id);
    }
    
    public void Show(){
        String txt = "";
        String value = "";
        String param_name = "";
        for (int i = 0; i < data.size(); i++) {
            txt = ((ArrayList)data.get(i)).get(0).toString();
            param_name = trans.translate(txt);
            value = fill.showMeasuresParam(con, patient.getId(), param_name);
            ((ArrayList)data.get(i)).set(1, value);
        }            
    }
    
    
    public String  getInputData(int row,String rez){
        try { 
       String value = ((ArrayList)data.get(row)).get(0).toString();
       switch(value){
            case  "Рост" : if(Integer.parseInt(rez) < 300 && Integer.parseInt(rez) > 50){
                System.out.println("okkkk");
                return rez.replaceAll("\\D+","");            
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Вес" : if(Integer.parseInt(rez) < 300 && Integer.parseInt(rez) > 10){
                return rez.replaceAll("\\D+","");               
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Окружность талии" : if(Integer.parseInt(rez) < 300 && Integer.parseInt(rez) > 10){
                return rez.replaceAll("\\D+","");                
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Артериальное давление верхнее" : if(Integer.parseInt(rez) < 400 && Integer.parseInt(rez) > 60){
                return rez.replaceAll("\\D+","");               
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Артериальное давление нижнее" : if(Integer.parseInt(rez) < 250 && Integer.parseInt(rez) > 10){
                return rez.replaceAll("\\D+","");                
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Пульс" : if(Integer.parseInt(rez) < 300 && Integer.parseInt(rez) > 10){
                return rez.replaceAll("\\D+","");              
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Правый" : if(Integer.parseInt(rez) < 40 && Integer.parseInt(rez) > 0){
                return rez.replaceAll("\\D+","");              
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
            case  "Левый" : if(Integer.parseInt(rez) < 40 && Integer.parseInt(rez) > 0){
                return rez.replaceAll("\\D+","");             
            }else JOptionPane.showMessageDialog(null, "Неправильно введены данные!"); return "0";
                }
          } catch (Exception e) {
              System.out.println(e);
        }  
                
       return rez;
        
    }
    
    public void check(String value){
      //  String value = ((ArrayList)data.get(row)).get(0).toString();
        try {
             switch (value) {
                case "Патологии выявлены?":
                    {
                        JComboBox cb = new JComboBox();
                        cb.setModel(new DefaultComboBoxModel(new String[] {"Да", "Нет"}));
                        cb.setFont(new java.awt.Font("Dialog", 0, 16));
                        TableColumn tc = table1.getColumnModel().getColumn(1);
                        TableCellEditor tce = new DefaultCellEditor(cb);
                        tc.setCellEditor(tce);
                        break;
                    }
                case "Курите ли вы?(курение одной или более сигарет в день)":
                    {
                        JComboBox cb = new JComboBox();
                        cb.setModel(new DefaultComboBoxModel(new String[] {"Да", "Нет" , "Курил ранее"}));
                        cb.setFont(new java.awt.Font("Dialog", 0, 16));
                        TableColumn tc = table1.getColumnModel().getColumn(1);
                        TableCellEditor tce = new DefaultCellEditor(cb);
                        tc.setCellEditor(tce);
                        break;
                    }
                 case "Цвет":
                    {
                        JComboBox cb = new JComboBox();
                        cb.setModel(new DefaultComboBoxModel(new String[] {"желтый","темно-желтый","соломенно-желтый"}));
                        cb.setFont(new java.awt.Font("Dialog", 0, 16));
                        TableColumn tc = table1.getColumnModel().getColumn(1);
                        TableCellEditor tce = new DefaultCellEditor(cb);
                        tc.setCellEditor(tce);
                        break;
                    }
                case "Мутность":
                    {
                        JComboBox cb = new JComboBox();
                        cb.setModel(new DefaultComboBoxModel(new String[] {"прозрачная", "мутная"}));
                        cb.setFont(new java.awt.Font("Dialog", 0, 16));
                        TableColumn tc = table1.getColumnModel().getColumn(1);
                        TableCellEditor tce = new DefaultCellEditor(cb);
                        tc.setCellEditor(tce);
                        break;
                    }   
                default:
                    try {
                        JTextField txt = new JTextField();
                        txt.setDocument(new JTextFieldLimit(16));
                        txt.setFont(new java.awt.Font("Dialog", 0, 14));
                        TableColumn tc = table1.getColumnModel().getColumn(1);
                        TableCellEditor tce = new DefaultCellEditor(txt);
                        tc.setCellEditor(tce);
                    } catch (Exception e) {
                        System.out.println(e);
                        log.info(e,e);
                    }   break;
            }
            if(value.equals("Рост")|| value.equals("Вес")|| value.equals("Окружность талии")
                    || value.equals("Артериальное давление верхнее")|| value.equals("Артериальное давление нижнее")
                    || value.equals("Пульс") || value.equals("Правый") || value.equals("Левый") ){
                JTextField txt = new JTextField();
                txt.setDocument(new JTextFieldLimit(8)); 
                txt.setFont(new java.awt.Font("Dialog", 0, 14));
                TableColumn tc = table1.getColumnModel().getColumn(1);       
                TableCellEditor tce = new DefaultCellEditor(txt);
                tc.setCellEditor(tce);                   
            }
            if(value.equals("Глюкоза (моча), ммоль/л") || value.equals("Протеин, г/л") || value.equals("Кетоны, ммоль/л") ||
                    value.equals("Эритроциты (моча),кол-во/мкл") || value.equals("Лейкоциты (моча), кол-во/мкл") ||
                    value.equals("Удельная плотность") || value.equals("Лейкоциты (моча), кол-во/мкл")|| value.equals("pH")
                    || value.equals("Уробилиноген, мкмоль/л") || value.equals("Аскорбиновая кислота, г/л") || value.equals("Нитриты")
                    || value.equals("Билирубин (моча), мкмоль/л")){
                JTextField txt = new JTextField();
                txt.setDocument(new JTextFieldLimit(16)); 
                txt.setFont(new java.awt.Font("Dialog", 0, 14));
                TableColumn tc = table1.getColumnModel().getColumn(1);       
                TableCellEditor tce = new DefaultCellEditor(txt);
                tc.setCellEditor(tce);  
            }
        } catch (Exception e) {
            log.info(e,e);
        }
       
    }
    

    
    // получение данных из объекта ResultSet 
    public void setDataSource( ResultSet rs) throws Exception {
    // удаляем прежние данные 
        data.clear(); 
        columnNames.clear(); 
        columnTypes.clear(); 
        // получаем вспомогательную информацию о столбцах 
        ResultSetMetaData rsmd = rs.getMetaData(); 
        int columnCount = rsmd.getColumnCount(); 
        
        for ( int i=0; i < columnCount; i++) {
            // название столбца 
            columnNames.add(rsmd.getColumnName(i+1));
            
            // тип столбца 
            Class type =
            Class.forName(rsmd.getColumnClassName(i+1));
            columnTypes.add(type); 
        } 
        // сообщаем об изменениях в структуре данных 
        fireTableStructureChanged(); 
        // получаем данные 
        while ( rs.next() ) {
            // здесь будем хранить ячейки одной строки 
            ArrayList row = new ArrayList(); 
            for ( int i=0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                row.add(rs.getString(i+1));
            else
                row.add(rs.getObject(i+1));
                } 
            synchronized (data) {
                 data.add(row); 
                 // сообщаем о прибавлении строки 
                 fireTableRowsInserted( data.size()-1, data.size()-1);
            } 
        }//end while 
    }//end setDataSource 
    
}
