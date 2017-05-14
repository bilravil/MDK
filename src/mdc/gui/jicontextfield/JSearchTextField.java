/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdc.gui.jicontextfield;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.UIManager;
 

public class JSearchTextField extends JIconTextField implements FocusListener {
 
    private String textWhenNotFocused;
 
    public JSearchTextField() {
        super();
        this.textWhenNotFocused = "Поиск";
        this.addFocusListener(this);
    }
 
    public String getTextWhenNotFocused() {
        return this.textWhenNotFocused;
    }
 
    public void setTextWhenNotFocused(String newText) {
        this.textWhenNotFocused = "ФИО, год рождения или ID:";
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        if (!this.hasFocus() && this.getText().equals("")) {
            int width = this.getWidth();
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(UIManager.getColor("textInactiveText"));
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 4;
            int x = this.getInsets().left;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(textWhenNotFocused, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }
 
    }
 
    //FocusListener implementation:
    public void focusGained(FocusEvent e) {
        this.repaint();
    }
 
    public void focusLost(FocusEvent e) {
        this.repaint();
    }
}
