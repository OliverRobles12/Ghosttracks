package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.negocio.interfaces.ICarritoBO;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.CarritoBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.PedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ProductosBO;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class VentaEnLineaFachada implements IVentaEnLinea{
    private final IPedidosBO pedidosBO;
    private final IProductosBO productosBO;
    private final IClientesBO clientesBO;
    private final ICarritoBO carritoBO;

    public VentaEnLineaFachada() {
        this.productosBO = new ProductosBO();
        this.clientesBO = new ClientesBO();
        this.carritoBO = new CarritoBO();
        this.pedidosBO = new PedidosBO();
    }

    @Override
    public List<ProductoDTO> obtenerCatalogo() throws Exception {
        return productosBO.obtenerTodos();
    }

    @Override
    public ProductoDTO consultarDetalleProducto(Long id) throws Exception {
        return productosBO.obtenerProductoPorId(id);
    }
    
    @Override
    public ClienteDTO consultarPerfilCliente(Long idCliente) throws Exception {
        return clientesBO.obtenerClientePorId(idCliente);
    }
    
    @Override
    public CarritoDTO agregarAlCarrito(CarritoDTO carritoActual, ProductoDTO producto, Integer cantidad) throws Exception {
        return carritoBO.agregarProducto(carritoActual, producto, cantidad);
    }
    
    @Override
    public CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws Exception {
        return carritoBO.eliminarProducto(carrito, idProducto);
    }
    
    @Override
    public PedidoDTO confirmarCompra(PedidoDTO pedido) throws Exception {
        return pedidosBO.generarPedido(pedido);
    }
    
    @Override
        public PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstado) throws Exception {
        return pedidosBO.actualizarEstadoPedido(idPedido, nuevoEstado);
    }
        
    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() throws Exception {
        return pedidosBO.obtenerTodosLosPedidos();
    }
}
