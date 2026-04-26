
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
import itson.org.ghosttracks.utilerias.pnlResumenPedido;
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
    
    /**
     * Obtiene el catálogo completo de productos disponibles.
     * Útil para cuando necesitamos la lista de datos puros (como en las sugerencias al azar).
     */
    public List<ProductoDTO> obtenerCatalogo() throws Exception {
        return ventaFachada.obtenerCatalogo();
    }    
    
    public void mostrarDetalleProducto(ProductoDTO productoSeleccionado) {
        navegador.irVistaProducto(productoSeleccionado);
    }
    
    public void llenarResumenPedido(pnlResumenPedido vistaResumen) {
        try {
            vistaResumen.cargarResumen(this.carrito);
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al cargar el resumen del pedido.", true);
        }
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
            if (this.carrito == null || this.carrito.getProductos().isEmpty()) {
                throw new Exception("El carrito está vacío. Agrega productos antes de pagar.");
            }
            
            this.pedidoDTO.setProductos(this.carrito.getProductos());
            this.pedidoDTO.calcularTotales(150.0);
            
            this.pedidoDTO = ventaFachada.confirmarCompra(this.pedidoDTO);
            
            mostrarMensaje("¡Pago aprobado! Pedido registrado exitosamente.", false);
            
            this.carrito = new CarritoDTO();
            this.pedidoDTO = new PedidoDTO();
            volverACatalogo();
            
        } catch (Exception ex) {
            mostrarMensaje(ex.getMessage(), true);
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
