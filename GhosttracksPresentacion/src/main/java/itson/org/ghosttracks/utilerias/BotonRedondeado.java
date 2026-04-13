/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.utilerias;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.ButtonModel;
import javax.swing.JButton;

/**
 *
 * @author Nahomi Figueroa
 */
public class BotonRedondeado extends JButton{
    private int arcAncho = 30;
    private int arcAlto = 30;
    private Color hoverColor = new Color(240, 240, 240);
    private Color pressedColor = new Color(200, 200, 200);

    public BotonRedondeado() {
        super("Rounded Button");
        setContentAreaFilled(false); 
        setFocusPainted(false);      
        setBorderPainted(false);    
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        ButtonModel model = getModel();
        if (model.isPressed()) {
            g2.setColor(pressedColor);
        } else if (model.isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcAncho, arcAlto);

        g2.dispose();
        
        super.paintComponent(g);
    }

    public int getArcAncho() {
        return arcAncho;
    }

    public void setArcAncho(int arcAncho) {
        this.arcAncho = arcAncho;
    }

    public int getArcAlto() {
        return arcAlto;
    }

    public void setArcAlto(int arcAlto) {
        this.arcAlto = arcAlto;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }
    
}
