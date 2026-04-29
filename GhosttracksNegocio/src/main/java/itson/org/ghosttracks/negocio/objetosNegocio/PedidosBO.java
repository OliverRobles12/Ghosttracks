package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Carrito;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPaquete;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.adaptador.SkydropxAdapter;
import itson.org.ghosttracks.negocio.adaptador.StripeAdapter;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidosBO implements IPedidosBO {

    private final IPedidosDAO pedidosDAO;
    private final IProveedorPago proveedorPago;
    private final IPaquetesBO paquetesBO; 
    private final IProveedorEnvios proveedorEnvios;
    private static final Logger LOGGER = Logger.getLogger(PedidosBO.class.getName());

    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
        this.proveedorPago = new StripeAdapter();
        this.paquetesBO = new PaquetesBO(); 
        this.proveedorEnvios = new SkydropxAdapter();
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
    public PedidoDTO generarPedido(PedidoDTO nuevoPedido) throws NegocioException {
        
        if (nuevoPedido == null || nuevoPedido.getCarrito() == null || nuevoPedido.getCarrito().getProductos().isEmpty()) {
            throw new NegocioException("No se puede procesar un pedido vacío.");
        }

        CarritoDTO carritoDto = nuevoPedido.getCarrito();
//        DatosPagoDTO pagoCliente = nuevoPedido.getDatosPago(); 
//        
//        if (pagoCliente != null) {
//            proveedorPago.cobrar(
//                carritoDto.getTotal(), 
//                pagoCliente.getTitularTarjeta(), 
//                pagoCliente.getNumeroTarjeta(), // Se corrigió el typo getNumeroTrajeta a getNumeroTarjeta asumiendo que lo arreglaste en el DTO
//                pagoCliente.getCvv(), 
//                pagoCliente.getFechaExpiracion() 
//            );
//        }
        
        try {
            Pedido entidadPedido = new Pedido();
            entidadPedido.setEstado(EstadoPedido.PAGADO);
            
            if (nuevoPedido.getCliente() != null) {
                Cliente clienteEntidad = new Cliente();
                clienteEntidad.setIdUsuario(nuevoPedido.getCliente().getIdUsuario());
                entidadPedido.setCliente(clienteEntidad);
            }
            
            Carrito carritoEntidad = new Carrito();
            carritoEntidad.setTotal(carritoDto.getTotal());
            entidadPedido.setCarrito(carritoEntidad);
            
            Pedido pedidoGuardado = pedidosDAO.guardarPedido(entidadPedido);
            
            PedidoDTO pedidoRespuesta = new PedidoDTO();
            pedidoRespuesta.setIdPedido(pedidoGuardado.getIdPedido());
            pedidoRespuesta.setEstado(EstadoPedidoDTO.PAGADO); 
            
            LOGGER.log(Level.INFO, "Pedido guardado correctamente con ID: {0}", pedidoGuardado.getIdPedido());
            return pedidoRespuesta;
            
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
    
    @Override
    public Pedido despacharPedido(Long idPedido, Double peso, Double largo, Double ancho, Double alto) throws NegocioException {
        try {
            Pedido pedido = pedidosDAO.consultarPorId(idPedido);

            if (pedido.getEstado() == EstadoPedido.ENVIADO) {
                throw new NegocioException("Este pedido ya ha sido procesado y enviado anteriormente.");
            }
            
            String numeroGuiaGenerado = proveedorEnvios.generarGuiaEnvio(); 
            
            Paquete nuevoPaquete = new Paquete();
            nuevoPaquete.setNumeroGuia(numeroGuiaGenerado);
            nuevoPaquete.setEstado(EstadoPaquete.ENVIADO); 
            nuevoPaquete.setFechaEnvio(LocalDateTime.now());
            nuevoPaquete.setPesoKg(peso);
            nuevoPaquete.setLargoCm(largo);
            nuevoPaquete.setAnchoCm(ancho);
            nuevoPaquete.setAltoCm(alto);
            nuevoPaquete.setPedido(pedido);
            
            pedido.setPaquete(nuevoPaquete);
            pedido.setEstado(EstadoPedido.ENVIADO); 

            paquetesBO.registrarEmpaque(nuevoPaquete);
            Pedido pedidoActualizado = pedidosDAO.actualizarPedido(pedido); 

            LOGGER.log(Level.INFO, "Pedido {0} despachado con éxito. Guía asignada: {1}", new Object[]{idPedido, numeroGuiaGenerado});
            return pedidoActualizado;

        } catch (PersistenciaException e) {
            LOGGER.log(Level.SEVERE, "Error al intentar despachar el pedido {0}", idPedido);
            throw new NegocioException("Ocurrió un error en la base de datos al despachar el pedido.", e);
        } catch (Exception e) {
            throw new NegocioException("Error al comunicarse con el proveedor de envíos.", e);
        }
    }

    @Override
    public List<Pedido> buscarPedidosFiltrados(List<Long> idsClientes, EstadoPedido estado) throws NegocioException {
        try{
            return pedidosDAO.buscarPedidosFiltrados(idsClientes, estado);
        }catch(PersistenciaException e){
            throw new NegocioException("No fué posible la consulta de los pedidos especificados "+e);
        }
    }
}
