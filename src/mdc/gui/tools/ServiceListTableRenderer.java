package mdc.gui.tools;

/**
 *
 * @author Равиль
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import mdc.engine.PatientData;

public class ServiceListTableRenderer extends DefaultTableCellRenderer {
    
    private final PatientData patient;
                @Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setHorizontalAlignment(SwingConstants.LEFT);
                        for (int i = 0; i < patient.getService_state().size(); i++) {
                            if(table.getModel().getValueAt(row, 0).toString().equals(patient.getService_state().get(i))){
                                        setBackground(new Color(140,108, 11,155));
                                        setForeground(Color.WHITE);
                                       
                                       if (isSelected){
                                            setBackground(new Color(140,108, 11,100));
                                            setForeground(Color.WHITE);
                                        } 
                                       return this;                               
                            }
                        }					
                                         if (isSelected)
                                        {
                                            setBackground(new Color(51,153, 255,155));
                                            setForeground(Color.WHITE);
                                        }	
                                        else
                                        {
                                            setBackground(Color.WHITE);
                                            setForeground(Color.BLACK);
                                        }
			return this;			
                }

    public ServiceListTableRenderer(PatientData patient) {    
        this.patient = patient;
    }
	}
