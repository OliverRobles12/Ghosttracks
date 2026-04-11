package itson.org.ghosttracks.utilerias;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Emy
 */
public class PanelRedondeado extends JPanel {
    private int arcoRojo = 30; 

    public PanelRedondeado() {
        setOpaque(false);
    }

    public int getArcoRojo() {
        return arcoRojo;
    }

    public void setArcoRojo(int arcoRojo) {
        this.arcoRojo = arcoRojo;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcoRojo, arcoRojo);
        super.paintComponent(g2);
    }
}
