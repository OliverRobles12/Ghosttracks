package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ContactoDTO;
import itson.org.ghosttracks.dtos.DatosPagoDTO;
import itson.org.ghosttracks.dtos.DireccionEntregaDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.NuevoPedidoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.SucursalDTO;
import itson.org.ghosttracks.entidades.Carrito;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Contacto;
import itson.org.ghosttracks.entidades.Direccion;
import itson.org.ghosttracks.entidades.ItemCarrito;
import itson.org.ghosttracks.entidades.Pago;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.Sucursal;
import itson.org.ghosttracks.enums.EstadoPaquete;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.adaptador.SkydropxAdapter;
import itson.org.ghosttracks.negocio.fabricas.ProveedorPagoFactory;
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
    private final IPaquetesBO paquetesBO;
    private final IProveedorEnvios proveedorEnvios;
    private static final Logger LOGGER = Logger.getLogger(PedidosBO.class.getName());

    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
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
    public PedidoDTO generarPedido(NuevoPedidoDTO nuevoPedido) throws NegocioException {

        if (nuevoPedido == null || nuevoPedido.getCarrito() == null || nuevoPedido.getCarrito().getProductos().isEmpty()) {
            throw new NegocioException("No se puede procesar un pedido vacío.");
        }

        CarritoDTO carritoDto = nuevoPedido.getCarrito();
        DatosPagoDTO datosPago = nuevoPedido.getDatosPago();

        if (datosPago != null) {
            IProveedorPago estrategia = ProveedorPagoFactory.obtenerEstrategia(datosPago.getMetodoPago());

            estrategia.cobrar(
                    datosPago.getNumeroTarjeta(),
                    carritoDto.getTotal(),
                    datosPago.getMetodoPago()
            );
        }

        try {
            Pedido entidadPedido = new Pedido();
            entidadPedido.setEstado(EstadoPedido.PAGADO);
            entidadPedido.setFechaPedido(LocalDateTime.now());

            if (datosPago != null) {
                Pago registroPago = new Pago(
                        TipoPago.TARJETA,
                        carritoDto.getTotal(),
                        LocalDateTime.now()
                );
                entidadPedido.setPago(registroPago);
            }

            if (nuevoPedido.getCliente() != null) {
                Cliente clienteEntidad = new Cliente();

                clienteEntidad.setIdUsuario(nuevoPedido.getCliente().getIdUsuario());

                clienteEntidad.setNombres(nuevoPedido.getCliente().getNombres());
                clienteEntidad.setApellidoPaterno(nuevoPedido.getCliente().getApellidoPaterno());
                clienteEntidad.setApellidoMaterno(nuevoPedido.getCliente().getApellidoMaterno());
                clienteEntidad.setCorreo(nuevoPedido.getCliente().getCorreo());
                clienteEntidad.setTelefono(nuevoPedido.getCliente().getTelefono());

                entidadPedido.setCliente(clienteEntidad);
            }

            Carrito carritoEntidad = new Carrito();
            carritoEntidad.setTotal(carritoDto.getTotal());
            carritoEntidad.setSubtotal(carritoDto.getSubtotal());
            carritoEntidad.setImpuestos(carritoDto.getImpuestos());

            List<ItemCarrito> itemsEntidad = new java.util.ArrayList<>();
            for (ItemCarritoDTO itemDTO : carritoDto.getProductos()) {
                ItemCarrito itemEntidad = new ItemCarrito();
                itemEntidad.setCantidad(itemDTO.getCantidad());
                itemEntidad.setSubtotal(itemDTO.getSubtotal());

                if (itemDTO.getProductoSeleccionado() != null) {
                    Producto productoEntidad = new Producto();
                    productoEntidad.setIdProducto(itemDTO.getProductoSeleccionado().getIdProducto());
                    productoEntidad.setPrecio(itemDTO.getProductoSeleccionado().getPrecio());
                    productoEntidad.setImgProducto(itemDTO.getProductoSeleccionado().getImgProducto());
                    itemEntidad.setProducto(productoEntidad);
                }
                itemsEntidad.add(itemEntidad);
            }
            carritoEntidad.setItems(itemsEntidad);
            entidadPedido.setCarrito(carritoEntidad);

            if (nuevoPedido.getDireccionEntrega() != null) {
                DireccionEntregaDTO dirDTO = nuevoPedido.getDireccionEntrega();
                Direccion dirEntidad = new Direccion();
                dirEntidad.setCalle(dirDTO.getCalle());
                dirEntidad.setColonia(dirDTO.getColonia());
                dirEntidad.setNumero(dirDTO.getNumero());
                dirEntidad.setCiudad(dirDTO.getCiudad());
                dirEntidad.setCodigoPostal(dirDTO.getCodigoPostal());
                entidadPedido.setDireccionEntrega(dirEntidad);
            }

            if (nuevoPedido.getContacto() != null) {
                ContactoDTO contactoDTO = nuevoPedido.getContacto();
                Contacto contactoEntidad = new Contacto();

                contactoEntidad.setNombre(contactoDTO.getNombre());
                contactoEntidad.setTelefono(contactoDTO.getTelefono());

                contactoEntidad.setCorreo(contactoDTO.getCorreo());

                entidadPedido.setContacto(contactoEntidad);
            }

            if (nuevoPedido.getSucursal() != null) {
                SucursalDTO sucursalDTO = nuevoPedido.getSucursal();
                Sucursal sucursalEntidad = new Sucursal();
                sucursalEntidad.setNombre(sucursalDTO.getNombre());
                entidadPedido.setSucursal(sucursalEntidad);
            }

            Pedido pedidoGuardado = pedidosDAO.guardarPedido(entidadPedido);

            PedidoDTO pedidoRespuesta = new PedidoDTO();
            pedidoRespuesta.setIdPedido(pedidoGuardado.getIdPedido());
            pedidoRespuesta.setEstado(EstadoPedidoDTO.PAGADO);

            LOGGER.log(Level.INFO, "Pedido y Pago registrados con éxito. ID: {0}", pedidoGuardado.getIdPedido());
            return pedidoRespuesta;

        } catch (PersistenciaException e) {
            LOGGER.log(Level.SEVERE, "ERROR CRÍTICO: Cobro realizado pero falló la persistencia.", e);
            throw new NegocioException("El pago se realizó, pero hubo un error al guardar el pedido. Contacte a soporte.", e);
        }
    }

    @Override
    public Pedido obtenerPedidoPorId(Long idPedido) throws NegocioException {
        if (idPedido == null) {
            throw new NegocioException("El id del pedido no es valido.");
        }
        try {
            return pedidosDAO.consultarPorId(idPedido);
        } catch (PersistenciaException e) {
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

            PaqueteDTO infoGuia = proveedorEnvios.generarGuiaPaquete(pedido.getIdPedido(), peso);

            Paquete nuevoPaquete = new Paquete();
            nuevoPaquete.setNumeroGuia(infoGuia.getNumeroGuia());
            nuevoPaquete.setFechaEntregaEstimada(infoGuia.getFechaEntregaEstimada());
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

            return pedidoActualizado;

        } catch (PersistenciaException e) {
            LOGGER.severe("Error al intentar despachar el pedido {0}");
            throw new NegocioException("Ocurrió un error en la base de datos al despachar el pedido.", e);
        } catch (Exception e) {
            throw new NegocioException("Error al comunicarse con el proveedor de envíos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> buscarPedidosFiltrados(List<Long> idsClientes, EstadoPedido estado) throws NegocioException {
        try {
            return pedidosDAO.buscarPedidosFiltrados(idsClientes, estado);
        } catch (PersistenciaException e) {
            throw new NegocioException("No fué posible la consulta de los pedidos especificados " + e);
        }
    }
}
