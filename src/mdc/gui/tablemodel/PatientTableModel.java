package mdc.gui.tablemodel;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.*;
import mdc.engine.PatientData;
import mdc.engine.filltable.FillPatientTable;

public class PatientTableModel extends AbstractTableModel  {

    // здесь мы будем хранить названия столбцов 
    private final ArrayList columnNames = new ArrayList(); 
    // список типов столбцов 
    private final ArrayList columnTypes = new ArrayList(); 
    // хранилище для полученных данных из базы данных 
    private final ArrayList data = new ArrayList(); 
    private PatientData patient;
    private final FillPatientTable fill;
    private JTable table;
    private Connection conn ;
    
    // конструктор позволяет задать возможность редактирования 
    public PatientTableModel(boolean editable, Connection con,FillPatientTable fill,JTable table) {
        this.editable = editable;
        this.conn = con;
        this.table = table;
        this.fill = fill;
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
    
    public void removeRow(int row) {
        data.remove(row);
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
        synchronized (data) {
             
        return ((ArrayList)data.get(row)).get(column); 
        } 
    } 
    // возможность редактирования 
    @Override
  public boolean isCellEditable(int row, int column){
    switch (column) {
    case 0: 
      return false;
    case 1: 
      return false;
    case 3: 
      if (((ArrayList)this.data.get(row)).get(2).toString().replaceAll(" ", "").equals("Выполненоранее")) {
        return true;
      }
      return false;
    }
    return true;
  }

    
    
    // замена значения ячейки 
    @Override
    public void setValueAt( Object value, int row, int column){
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.YYYY");
        synchronized (data) {        
        ((ArrayList)data.get(row)).set(column, value);        
            if( column == 2){
            updateState(row);
            if(((ArrayList)data.get(row)).get(2).toString().replaceAll(" ", "").equals("Назначено")){
                  ((ArrayList)this.data.get(row)).set(3, df.format(new Date()));
                    updateDate(row);
                    return;
                } 
                setDate(row);
            }
            if( column == 2){
           
            if(((ArrayList)data.get(row)).get(2).toString().replaceAll(" ", "").equals("Выполненоранее")){
                  table.changeSelection(row,3,false, false);
                  table.setCellSelectionEnabled(true);
                  table.setValueAt("Выберите дату", row, 3);
                  updateState(row);
                  return;
                } 
            //setDate(row);
            }
            if(column == 3){
                updateDate(row);
            }           
        }      
    }
    
    public void updateState(int i){
        String service_name = ((ArrayList)data.get(i)).get(1).toString();
        String sub_service_name;
        switch(service_name){
            case "07.Осмотр фельдшером (акушеркой)" : 
                    String state = ((ArrayList)data.get(i)).get(2).toString();   
                    String idd = fill.getCodeFromRezult("07.Мазок на онкоцитологию (Ж)", conn);
                    String id = fill.getCodeFromRezult("07.Мазок на онкоцитологию (С)", conn);
                    if(idd==null)
                    {fill.updateServiceState(id, state, conn);}
                    if(id==null)
                    {fill.updateServiceState(idd, state, conn);}             
                    break;
           case "09.Маммография" :  
                    sub_service_name = "09.Маммография расшифровка";
                    String id1 = fill.getCodeFromRezult(sub_service_name, conn);    
                    String state1 = ((ArrayList)data.get(i)).get(2).toString();
                    fill.updateServiceState(id1, state1, conn);
                    break;         
        }
        String id = fill.getCodeFromRezult(service_name, conn);    
        String state = ((ArrayList)data.get(i)).get(2).toString();
        fill.updateServiceState(id, state, conn);
    }
    
    public void updateDate(int i){                
        String service_name = ((ArrayList)data.get(i)).get(1).toString();
        String sub_service_name;
        String date = ((ArrayList)data.get(i)).get(3).toString();       
        switch(service_name){
            case "07.Осмотр фельдшером (акушеркой)" :   
                    String idd = fill.getCodeFromRezult("07.Мазок на онкоцитологию (Ж)", conn);
                    String id = fill.getCodeFromRezult("07.Мазок на онкоцитологию (С)", conn);
                    if(idd==null){
                        fill.updateServiceDate(id, date, conn);
                    }
                    
                    if(id==null){
                        fill.updateServiceDate(idd, date, conn);
                    }             
                    break;
           case "09.Маммография" :  
                    sub_service_name = "09.Маммография расшифровка";
                    String id1 = fill.getCodeFromRezult(sub_service_name, conn);    
                    fill.updateServiceDate(id1, date, conn);
                    break;         
        }
        String id = fill.getCodeFromRezult(service_name, conn); 
        fill.updateServiceDate(id, date, conn);
    }
    
    public void setDate(int i){     
         Date now = new Date();
         DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
         String s = formatter.format(now);   
         if(((ArrayList)data.get(i)).get(2).toString().replaceAll(" ", "").equals("Отказ")){
             ((ArrayList)data.get(i)).set(3, s);
               updateDate(i); }                   
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
            
           
        }
             //end while 
    }//end setDataSource 
}