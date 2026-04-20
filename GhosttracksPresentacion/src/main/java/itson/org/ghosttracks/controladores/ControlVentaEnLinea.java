
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracksventaenlinea.fachada.VentaEnLineaFachada;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.List;

/**
 *
 * @author oliro
 */
public class ControlVentaEnLinea {
    
    private final Navegador navegador;
    private final IVentaEnLinea ventaFachada = new VentaEnLineaFachada();
    
    private CarritoDTO carrito;
    private PedidoDTO pedidoDTO;
    
    public ControlVentaEnLinea(Navegador nav) {
        this.navegador = nav;
        this.pedidoDTO = new PedidoDTO();
        this.carrito = new CarritoDTO();
    }
    
    // Salto pantallas
    
    public void comenzarProcesoPedido() {
        navegador.irFormularioContacto();
    }
    
    public void procesoPedidoEntrega() {
        navegador.irFormularioEntrega();
    }
    
    public void procesarPedidoMetodoPago() {
        navegador.irSeleccionMetodoPago();
    }
    
    public void volverACatalogo() {
        navegador.irInicioCliente();
    }
    
    // Pantallas
    
    public void llenarTablaCarrito(PantallaCarrito vista){
        vista.llenarTabla(carrito);
    }
    
    public void llenarCatalogo(PantallaInicioCliente vista) {
        try {
            List<ProductoDTO> productos = ventaFachada.obtenerCatalogo();
            vista.cargarCatalogo(productos);
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al llenar el catalogo de productos.", true);
        }
    }
    
    public void mostrarDetalleProducto(ProductoDTO productoSeleccionado) {
        navegador.irVistaProducto(productoSeleccionado);
    }
    
    // Pedido
    
    public void agregarDireccionPedido(DireccionEntregaDTO dto) {
        pedidoDTO.setDireccionEntrega(dto);
    }
    
    public void agregarContactoPedido(ContactoDTO dto) {
        pedidoDTO.setContacto(dto);
    }
    
    public void agregarMetodoPago(DatosPagoDTO dto) {
        pedidoDTO.setDatosPago(dto);
    }
    
    public void procesarPedido() {
        try {
            this.pedidoDTO = ventaFachada.confirmarCompra(pedidoDTO);
            ventaFachada.actualizarEstadoPedido(pedidoDTO.getIdPedido(), EstadoPedidoDTO.PAGADO);
            mostrarMensaje("Pedido registrado exitosamente.", false);
        } catch (Exception ex) {
            mostrarMensaje("No ha sido posible realizar el pedido.", true);
        }
        
    }
    
    public void agregarProductoCarrito(ProductoDTO producto, Integer cantidad) {
        try {
            ventaFachada.agregarAlCarrito(carrito, producto, cantidad);
        } catch (Exception ex) {
            navegador.mostrarMensaje("No ha sido poosible agregar el producto al carrito.", true);
        }
    }
    
    public void eliminarProductoCarrito(Long idProducto) {
        try {
            ventaFachada.eliminarDelCarrito(carrito, idProducto);
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al eliminar el producto del carrito.", true);
        }
    }
    
    // Extras
    
    public void mostrarMensaje(String mensaje, boolean esError) {
        navegador.mostrarMensaje(mensaje, esError);
    }
    
}
