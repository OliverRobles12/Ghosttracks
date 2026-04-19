 
package itson.org.ghosttracks.controladores;

/**
 *
 * @author oliro
 */
public class Controlador {
    
    private static Controlador instancia;
    
    private ControlVentaEnLinea controlVentaEnLinea;
    private ControladorVentasAdmin controlVentasAdmin;
    
    private Controlador() {
    }
    
    public static Controlador getInstancia() {
        if (instancia == null) {
            instancia = new Controlador();
        }
        return instancia;
    }
    
    
}
