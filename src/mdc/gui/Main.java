package mdc.gui;

import de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Main {
    private static FileInputStream fis;
    private static Properties property = new Properties(); 
    private static String fontScale ;

    
    public static void main(String args[]) {
        try {
            fis = new FileInputStream("./resources/config.properties");          
            property.load(fis);
        } catch (Exception e) {
            System.out.println(e);
        }
            fontScale = property.getProperty("fontScale");
            int scale = Integer.parseInt(fontScale);
            UIManager.put("Synthetica.font.scaleFactor", scale);
            SyntheticaMauveMetallicLookAndFeel lf;
        try {
            lf = new SyntheticaMauveMetallicLookAndFeel();
                try {
                    UIManager.setLookAndFeel(lf);
                } catch (UnsupportedLookAndFeelException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
            
            SyntheticaMauveMetallicLookAndFeel.setFont("Dialog", 16);

        java.awt.EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH );
            frame.setVisible(true);
            });
    }
    
    
}
