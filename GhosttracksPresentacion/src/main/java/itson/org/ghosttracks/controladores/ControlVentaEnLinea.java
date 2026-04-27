
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.presentacion.cliente.PantallaCarrito;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.utilerias.pnlResumenPedido;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
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
    
    public void filtrarCatalogoPorTipo(PantallaInicioCliente vista, TipoProducto tipo) {
        try {
            List<ProductoDTO> todosLosProductos = ventaFachada.obtenerCatalogo();
            
            List<ProductoDTO> productosFiltrados = todosLosProductos.stream()
                    .filter(producto -> producto.getTipoProducto() == tipo) 
                    .toList();
            
            vista.cargarCatalogo(productosFiltrados);
            
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al filtrar el catálogo de productos.", true);
        }
    }
    
    public void filtrarCatalogoPorGenero(PantallaInicioCliente vista, String genero) {
        try {
            List<ProductoDTO> todosLosProductos = ventaFachada.obtenerCatalogo();
            
            List<ProductoDTO> productosFiltrados = todosLosProductos.stream()
                    .filter(producto -> producto.getGenero() != null && 
                                        producto.getGenero().equalsIgnoreCase(genero)) 
                    .toList();
            
            vista.cargarCatalogo(productosFiltrados);
            
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al filtrar el catálogo por género.", true);
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
    
    public void procesarPedido() throws Exception {
        try {
            
            if (!SesionUsuario.getInstancia().haySesionActiva()) {
                navegador.mostrarMensaje("Por favor, inicia sesión para terminar tu compra.", true);
                return; 
            }
            ClienteDTO clienteLogueado = SesionUsuario.getInstancia().getCliente();
            Long idCliente = clienteLogueado.getIdUsuario();
//            
//            Long idCliente = 1L; // El usuario registrado como Emy

            this.pedidoDTO.setIdCliente(idCliente); 
            this.pedidoDTO.setProductos(this.carrito.getProductos());
            this.pedidoDTO.setSubtotal(this.carrito.getSubtotal());
            this.pedidoDTO.setTotal(this.carrito.getTotal());
            this.pedidoDTO.setEstado(EstadoPedidoDTO.PENDIENTE); 

            PedidoDTO pedidoGenerado = ventaFachada.confirmarCompra(this.pedidoDTO);
            
            navegador.mostrarMensaje("¡Compra realizada con éxito! Pedido #" + pedidoGenerado.getIdPedido(), false);
            
            this.carrito = new CarritoDTO(); 
            this.pedidoDTO = new PedidoDTO();
            
            navegador.irInicioCliente();
            
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("No pudimos procesar tu compra: " + ex.getMessage(), true);
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
