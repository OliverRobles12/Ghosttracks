
package itson.rog.ghosttracks.controladores;

import itson.org.ghosttracks.mocks.Producto;
import itson.org.ghosttracks.presentacion.VentanaPrincipal;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentasProcesarAdmin;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracks.presentacion.cliente.PanelInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.PanelVistaProducto;
import itson.org.ghosttracks.presentacion.panelLogin;
import itson.org.ghosttracks.utilerias.pnlBarraSuperiorCorta;
import itson.org.ghosttracks.utilerias.pnlMenuLateral;
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
    
    // Paneles cliente
    
    public void iniciarSesionClienteExitoso() {
         ventana.fijarMenuLateral(new pnlMenuLateral(this));
         ventana.fijarHeader(new pnlBarraSuperiorCorta());
    }
    
    public void irInicioCliente() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PanelInicioCliente vista = new PanelInicioCliente(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void irVistaProducto(Producto producto) {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PanelVistaProducto vista = new PanelVistaProducto(ctrl, producto);
        ventana.cambiarPantalla(vista);
    }
    
    // Paneles admin
    
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
    
    public void alternarMenuLateral() {
        ventana.alternarMenuLateral();
    }
    
}
