
package itson.rog.ghosttracks.controladores;

import itson.org.ghosttracks.presentacion.VentanaPrincipal;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentasProcesarAdmin;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracks.presentacion.panelLogin;
import itson.org.ghosttracks.utilerias.pnlBarraSuperiorCorta;
import itson.org.ghosttracks.utilerias.pnlMenuLateralAdmin;

/**
 *
 * @author oliro
 */
public class Navegador {

    private final VentanaPrincipal ventana;

    public Navegador(VentanaPrincipal ventana) {
        this.ventana = ventana;
    }
    
    public void irLogin() {
        ventana.limpiarMenuYHeader();
        ControlLogin ctrl = new ControlLogin(this);
        panelLogin vista = new panelLogin(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void iniciarSesionAdminExitoso() {
        ventana.fijarMenuLateral(new pnlMenuLateralAdmin(this));
        ventana.fijarHeader(new pnlBarraSuperiorCorta());
    }
    
    public void irVentasAdmin() {
        ControladorVentasAdmin ctrl = new ControladorVentasAdmin(this);
        PantallaVentas vista = new PantallaVentas(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void irProcesarPedidoAdmin() {
        ControladorVentasAdmin ctrl = new ControladorVentasAdmin(this);
        PantallaVentasProcesarAdmin vista = new PantallaVentasProcesarAdmin(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void cerrarSesion() {
        irLogin();
    }
    
}
