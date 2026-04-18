
package itson.rog.ghosttracks.controladores;

import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import javax.swing.JFrame;

/**
 *
 * @author oliro
 */
public class ControladorVentasAdmin {
    
    public ControladorVentasAdmin() {
    }
    
    public void mostrarPantallaVentas(JFrame pantallaActual) {
        PantallaVentas vistaPantalla = new PantallaVentas();
        vistaPantalla.setLocationRelativeTo(vistaPantalla);
        vistaPantalla.setVisible(true);
        
        if (pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
}
