package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.ProductoPedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;

import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.adaptador.StripeAdapter;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidosBO implements IPedidosBO {

    private final IPedidosDAO pedidosDAO;
    private final IProveedorPago proveedorPago;
    private static final Logger LOGGER = Logger.getLogger(PedidosBO.class.getName());

    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
        this.proveedorPago = new StripeAdapter();
    }

    @Override
    public Pedido guardarPedido(Pedido pedido) throws NegocioException {
        try {
            return pedidosDAO.guardarPedido(pedido);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar el pedido en BD", e);
        }
    }

    @Override
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws NegocioException {
        try {
            return pedidosDAO.actualizarEstado(idPedido, nuevoEstado);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar el pedido en BD", e);
        }
    }

    @Override
    public List<Pedido> consultarTodos() throws NegocioException {
        try {
            return pedidosDAO.consultarTodos();
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar pedidos en BD", e);
        }
    }
    
    @Override
    public PedidoDTO generarPedido(PedidoDTO pedidoDto) throws NegocioException {
        
        if (pedidoDto == null || pedidoDto.getProductos() == null || pedidoDto.getProductos().isEmpty()) {
            throw new NegocioException("No se puede procesar un pedido vacío.");
        }

        
        if (pedidoDto.getTotal() == null || pedidoDto.getTotal() == 0) {
            pedidoDto.calcularTotales(150.0); 
        }

        DatosPagoDTO pagoCliente = pedidoDto.getDatosPago(); 
        
        proveedorPago.cobrar(
            pedidoDto.getTotal(), 
            pagoCliente.getTitularTarjeta(), 
            pagoCliente.getNumeroTrajeta(), 
            pagoCliente.getCvv(), 
            pagoCliente.getFechaExpiracion() 
        );
        
        try {
            Pedido entidadPedido = new Pedido();
            entidadPedido.setFechaPedido(LocalDateTime.now());
            entidadPedido.setEstado(EstadoPedido.PAGADO);
            entidadPedido.setSubtotal(pedidoDto.getSubtotal());
            entidadPedido.setCostoEnvio(pedidoDto.getCostoEnvio());
            entidadPedido.setTotal(pedidoDto.getTotal());
            
            List<ProductoPedido> detalles = new ArrayList<>();
            for (ItemCarritoDTO item : pedidoDto.getProductos()) {
                ProductoPedido detalle = new ProductoPedido();
                detalle.setCantidadProducto(item.getCantidad());
                detalle.setPrecioVendido(item.getProductoSeleccionado().getPrecio());
                detalle.setImporteTotal(item.getSubtotal());
                
                Producto prodEntidad = new Producto();
                prodEntidad.setIdProducto(item.getProductoSeleccionado().getIdProducto());
                detalle.setProducto(prodEntidad);
                detalle.setPedido(entidadPedido);
                
                detalles.add(detalle);
            }
            entidadPedido.setProductosPedido(detalles); 
            
            Pedido pedidoGuardado = pedidosDAO.guardarPedido(entidadPedido);
            
            pedidoDto.setIdPedido(pedidoGuardado.getIdPedido());
            pedidoDto.setEstado(EstadoPedidoDTO.PAGADO); 
            
            LOGGER.log(Level.INFO, "Pedido guardado correctamente con ID: {0}", pedidoGuardado.getIdPedido());
            return pedidoDto;
            
        } catch (PersistenciaException e) {
            LOGGER.log(Level.SEVERE, "ERROR CRÍTICO: Cobro realizado pero falló la persistencia del pedido.", e);
            throw new NegocioException("Cobro exitoso pero ocurrió un error al registrar el pedido en la BD.", e);
        }
    }    

    @Override
    public Pedido obtenerPedidoPorId(Long idPedido) throws NegocioException {
        if (idPedido == null ){
            throw new NegocioException("El id del pedido no es valido.");
        }
        try{
            return pedidosDAO.consultarPorId(idPedido);
        } catch(PersistenciaException e){
            LOGGER.log(Level.WARNING, "ERROR: No se logró consultar el pedido por el ID.", e);
            throw new NegocioException("No se logró consultar el pedido por el ID", e);
        }
    }
}
