
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.mocks.ProductosMockDAO;
import itson.org.ghosttracks.presentacion.VentanaPrincipal;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentasProcesarAdmin;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioContacto;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioEntrega;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.PantallaSeleccionMetodoDePago;
import itson.org.ghosttracks.presentacion.cliente.PantallaVistaProducto;
import itson.org.ghosttracks.presentacion.cliente.metodosDePago.PanelMetodoPagoTarjetaDebito;
import itson.org.ghosttracks.presentacion.panelLogin;
import itson.org.ghosttracks.utilerias.PanelHeader;
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
         ventana.fijarHeader(new PanelHeader(this));
    }
    
    public void irInicioCliente() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaInicioCliente vista = new PantallaInicioCliente(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void irVistaProducto(ProductosMockDAO producto) {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaVistaProducto vista = new PantallaVistaProducto(ctrl, producto);
        ventana.cambiarPantalla(vista);
    }
    
    public void irCarrito() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaCarrito vista = new PantallaCarrito(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    // Paneles Pedido
    
    public void irFormularioContacto() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaFormularioContacto vista = new PantallaFormularioContacto(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void irFormularioEntrega() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaFormularioEntrega vista = new PantallaFormularioEntrega(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    public void irSeleccionMetodoPago() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PantallaSeleccionMetodoDePago vista = new PantallaSeleccionMetodoDePago(ctrl);
        ventana.cambiarPantalla(vista);
    }
    
    // Paneles Metodos de pago
    
    public void irMetodoPagoTarjetaDebito() {
        ControlVentaEnLinea ctrl = new ControlVentaEnLinea(this);
        PanelMetodoPagoTarjetaDebito vista = new PanelMetodoPagoTarjetaDebito();
        ventana.cambiarPantalla(vista);
    }
    
    // Paneles admin
    
    public void iniciarSesionAdminExitoso() {
        ventana.fijarMenuLateral(new pnlMenuLateralAdmin(this));
        ventana.fijarHeader(new PanelHeader(this));
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
