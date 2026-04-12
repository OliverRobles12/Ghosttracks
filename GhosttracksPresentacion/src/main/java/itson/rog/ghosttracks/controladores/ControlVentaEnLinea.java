
package itson.rog.ghosttracks.controladores;

import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.VistaProducto;
import javax.swing.JFrame;

/**
 *
 * @author oliro
 */
public class ControlVentaEnLinea {
    
    public ControlVentaEnLinea() {
        
    }
    
    public void mostrarPantallaInicio(JFrame pantallaActual) {
        PantallaInicioCliente vistaPantalla = new PantallaInicioCliente();
        vistaPantalla.setLocationRelativeTo(vistaPantalla);
        vistaPantalla.setVisible(true);
        
        if (pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
    public void mostrarPantallaVistaProducto(JFrame pantallaActual) {
        VistaProducto vistaProducto = new VistaProducto(this);
        vistaProducto.setVisible(true);
        
        if(pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
}
