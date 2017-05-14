package mdc.device.print;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class PrintButtonRenderer extends JButton implements TableCellRenderer {

  public PrintButtonRenderer() {
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (isSelected) {

      setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/print24.png"))); // NOI18N
      setFocusPainted(false);
      setContentAreaFilled(false);
    } else {
      setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/print24.png"))); // NOI18N
      setFocusPainted(false);
    setContentAreaFilled(false);
      
    }
    setIcon(new javax.swing.ImageIcon(getClass().getResource("/mdc/images/print16.png")));
    setFocusPainted(false);
    setContentAreaFilled(false);
    setText((value == null) ? "" : value.toString());
    return this;
  }


}