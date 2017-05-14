/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.gui.jicontextfield;

/**
 *
 * @author Равиль
 */
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
 

public class JIconTextField extends JTextField{
 
    private Icon icon;
    private Insets dummyInsets;
 
    public JIconTextField(){
        super();
        this.icon = new ImageIcon(getClass().getResource("ico.png"));
 
        Border border = UIManager.getBorder("TextField.border");
        JTextField dummy = new JTextField();
        this.dummyInsets = border.getBorderInsets(dummy);
    }
 
    public void setIcon(Icon icon){
        this.icon = icon;
    }
 
    public Icon getIcon(){
        return this.icon;
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        int textX = 2;
 
        if(this.icon!=null){
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = dummyInsets.left + 5;//this is our icon's x
            textX = x+iconWidth+2; //this is the x where text should start
            int y = (this.getHeight() - iconHeight)/2;
            icon.paintIcon(this, g, x, y);
        }
 
        setMargin(new Insets(2, textX, 2, 2));
 
    }
 
}
