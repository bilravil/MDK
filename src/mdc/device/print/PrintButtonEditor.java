/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.device.print;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import mdc.engine.PatientData;
import mdc.engine.SessionData;
import mdc.engine.filltable.FillRezultTable;

/**
 *
 * @author Равиль
 */
public class PrintButtonEditor extends DefaultCellEditor {
  protected JButton button;

  private String label;
  private PatientData patient;
  private boolean isPushed;
  private JTable table;
  private FillRezultTable fill;
  private Connection con;
  private SessionData session;
  
  public PrintButtonEditor(JCheckBox checkBox,PatientData patient, JTable table,FillRezultTable fill,Connection con,SessionData session) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(false);
    button.addActionListener((ActionEvent e) -> {
        fireEditingStopped();
    });
    this.patient = patient;
    this.table = table;
    this.fill = fill;
    this.con = con;
    this.session = session;
  }


  
  

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    if (isSelected) {

      button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/print24.png"))); // NOI18N
      button.setFocusPainted(false);
      button.setContentAreaFilled(false);

    } else {
      button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/print24.png"))); // NOI18N
      button.setFocusPainted(false);
      button.setContentAreaFilled(false);

    }
    label = (value == null) ? "" : value.toString();
    isPushed = true;
    return button;
  }

  @Override
  public Object getCellEditorValue() {
    if (isPushed) {
        PrintImpl print = new PrintImpl(con, table, fill, patient,session);
        print.print();
    }
    isPushed = false;
    return new String(label);
  }

  @Override
  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  @Override
  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }
}