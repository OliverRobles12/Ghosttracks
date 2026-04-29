
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.presentacion.VentanaPrincipal;
import itson.org.ghosttracks.presentacion.administrador.PanelConfirmarEmpaquetado;
import itson.org.ghosttracks.presentacion.administrador.PanelDatosPaquete;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentasProcesarAdmin;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioContacto;
import itson.org.ghosttracks.presentacion.cliente.PantallaFormularioEntrega;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.PantallaSeleccionMetodoDePago;
import itson.org.ghosttracks.presentacion.cliente.PantallaVistaProducto;
import itson.org.ghosttracks.presentacion.panelLogin;
import itson.org.ghosttracks.utilerias.PanelHeader;
import itson.org.ghosttracks.utilerias.pnlMenuLateral;
import itson.org.ghosttracks.utilerias.pnlMenuLateralAdmin;
import itson.org.ghosttracks.utilerias.pnlResumenPedidoConfirmado;
import javax.swing.JPanel;

/**
 *
 * @author oliro
 */
public class Navegador {

    private final VentanaPrincipal ventana;
    private final ControlVentaEnLinea ctrlVentaLinea;

    public Navegador(VentanaPrincipal ventana) {
        this.ctrlVentaLinea = new ControlVentaEnLinea(this);
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
        PantallaInicioCliente vista = new PantallaInicioCliente(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
    
    public void irVistaProducto(ProductoDTO producto) {
        PantallaVistaProducto vista = new PantallaVistaProducto(ctrlVentaLinea, producto);
        ventana.cambiarPantalla(vista);
    }
    
    public void irCarrito() {
        PantallaCarrito vista = new PantallaCarrito(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
    
    // Paneles Pedido
    
    public void irFormularioContacto() {
        PantallaFormularioContacto vista = new PantallaFormularioContacto(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
    
    public void irFormularioEntrega() {
        PantallaFormularioEntrega vista = new PantallaFormularioEntrega(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
    
    public void irSeleccionMetodoPago() {
        PantallaSeleccionMetodoDePago vista = new PantallaSeleccionMetodoDePago(ctrlVentaLinea);
        ventana.cambiarPantalla(vista);
    }
    
//    public void irPedidoConfirmado(){
//        PantallaPedidoConfirmado vista = new PantallaPedidoConfirmado(ctrlVentaLinea);
//        venata.cambiarPantalla(vista);
//    }
    
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
    
        
    public void cerrarSesion() {
        irLogin();
    }
    
    public void mostrarMensaje(String mensaje, boolean esError) {
        ventana.mostrarMensaje(mensaje, esError);
    }

    public void irPantallaConfirmarEmpaque(PedidoDTO pedidoSeleccionado) {
        ControladorVentasAdmin ctrl = new ControladorVentasAdmin(this);
        PantallaVentasProcesarAdmin vistaBase = new PantallaVentasProcesarAdmin(ctrl, pedidoSeleccionado);
        JPanel panelEmpaque = new PanelConfirmarEmpaquetado(ctrl, pedidoSeleccionado); 
        vistaBase.cambiarPanelAccion(panelEmpaque);
        ventana.cambiarPantalla(vistaBase);
    }

    public void irPantallaConfirmarEnvio(PedidoDTO pedidoSeleccionado) {
        ControladorVentasAdmin ctrl = new ControladorVentasAdmin(this);
        PantallaVentasProcesarAdmin vistaBase = new PantallaVentasProcesarAdmin(ctrl, pedidoSeleccionado);
        JPanel panelEnvio = new PanelDatosPaquete(ctrl, pedidoSeleccionado);
        vistaBase.cambiarPanelAccion(panelEnvio);
        ventana.cambiarPantalla(vistaBase);
    }

    public void irPedidoConfirmado(PedidoDTO pedidoConfirmado) {
        pnlResumenPedidoConfirmado vista = new pnlResumenPedidoConfirmado(ctrlVentaLinea);
        vista.cargarDatosPedido(pedidoConfirmado);
        ventana.cambiarPantalla(vista);
    }
    
}
