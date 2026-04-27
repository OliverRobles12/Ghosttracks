
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.fachada.VentaEnLineaFachada;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;

/**
 *
 * @author oliro
 */
public class ControlLogin {
    
    private Navegador navegador;
    private final IVentaEnLinea ventaFachada = new VentaEnLineaFachada();

    public ControlLogin(Navegador navegador) {
        this.navegador = navegador;
    }
    
    public void loginCliente(String correo, String contrasena) {
        try {
            
            ClienteDTO cliente = ventaFachada.iniciarSesion(correo, contrasena);
            SesionUsuario.getInstancia().setCliente(cliente);
            
            navegador.iniciarSesionClienteExitoso();
            navegador.irInicioCliente();
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Credenciales inválidas", true);
        }
    }
    
    public void loginAdmin() {
        navegador.iniciarSesionAdminExitoso();
        navegador.irVentasAdmin();
    }
    
    public void mostrarMensaje(String mensaje, boolean esError) {
        navegador.mostrarMensaje(mensaje, esError);
    }
}
