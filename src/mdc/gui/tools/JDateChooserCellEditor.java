package mdc.gui.tools;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class JDateChooserCellEditor
  extends AbstractCellEditor
  implements TableCellEditor
{
  private final JDateChooser dateChooser = new JDateChooser("dd.MM.yyyy", "##.##.####", '_');
  private final SimpleDateFormat df = new SimpleDateFormat("dd.MM.YYYY");
  
  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
  {
    this.dateChooser.setFont(new Font("Dialog", 0, 15));
    if (this.dateChooser.getDate() != null)
    {
      this.dateChooser.setDate(this.dateChooser.getDate());
      return this.dateChooser;
    }
    Date date = new Date();
    if ((value instanceof Date)) {
      date = (Date)value;
    }
    this.dateChooser.setDateFormatString("dd.MM.yyyy");
    this.dateChooser.setDate(date);
    return this.dateChooser;
  }
  
  @Override
  public Object getCellEditorValue()
  {
    try
    {
      this.dateChooser.setFont(new Font("Dialog", 0, 15));
      if (((JTextField)this.dateChooser.getDateEditor().getUiComponent()).getText().isEmpty()) {
        return "";
      }
      return this.df.format(this.dateChooser.getDate());
    }
    catch (Exception e) {}
    return this.df.format(new Date());
  }
}
