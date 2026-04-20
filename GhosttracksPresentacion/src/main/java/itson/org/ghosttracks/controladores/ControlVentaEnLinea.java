
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.mocks.ProductosMockDAO;

/**
 *
 * @author oliro
 */
public class ControlVentaEnLinea {
    
    private final Navegador navegador;
    
    public ControlVentaEnLinea(Navegador nav) {
        this.navegador = nav;
    }
    
    public void comenzarProcesoPedido() {
        navegador.irFormularioContacto();
    }
    
    public void procesoPedidoEntrega() {
        navegador.irFormularioEntrega();
    }
    
    public void procesarPedidoMetodoPago() {
        navegador.irSeleccionMetodoPago();
    }
    
    public void mostrarDetalleProducto(ProductosMockDAO productoSeleccionado) {
        navegador.irVistaProducto(productoSeleccionado);
    }
    
    public void volverACatalogo() {
        navegador.irInicioCliente();
    }
    
}
