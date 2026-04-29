package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.DireccionClienteDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.ProductoPedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracks.negocio.objetosNegocio.PaquetesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.PedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ProductosBO;
import itson.org.ghosttracksventaenlinea.excepciones.CodigoErrorVenta;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class VentaEnLineaFachada implements IVentaEnLinea {

    private final IPedidosBO pedidosBO;
    private final IProductosBO productosBO;
    private final IClientesBO clientesBO;
    private final IPaquetesBO paquetesBO;

    public VentaEnLineaFachada() {
        this.productosBO = new ProductosBO();
        this.clientesBO = new ClientesBO();
        this.pedidosBO = new PedidosBO();
        this.paquetesBO = new PaquetesBO();
    }

    @Override
    public List<ProductoDTO> obtenerCatalogo() throws VentaEnLineaException {
        try {
            List<Producto> productosEntidad = productosBO.obtenerTodos();
            List<ProductoDTO> disponibles = new ArrayList<>();

            for (Producto p : productosEntidad) {
                if (p.getStock() != null && p.getStock() > 0) {
                    ProductoDTO dto = mapearProductoADTO(p);
                    disponibles.add(dto);
                }
            }
            return disponibles;
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al cargar el catálogo", ex);
        }
    }

    @Override
    public ProductoDTO consultarDetalleProducto(Long id) throws VentaEnLineaException {
        if (id == null || id <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de producto inválido");
        }
        try {
            Producto entidad = productosBO.obtenerProductoPorId(id);
            return mapearProductoADTO(entidad);
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.PRODUCTO_NO_ENCONTRADO, "No se encontró el producto", ex);
        }
    }

    @Override
    public ClienteDTO consultarPerfilCliente(Long idCliente) throws VentaEnLineaException {
        try {
            Cliente entidad = clientesBO.obtenerClientePorId(idCliente);
            ClienteDTO dto = new ClienteDTO();
            dto.setIdUsuario(entidad.getIdUsuario());
            dto.setNombres(entidad.getNombres());
            dto.setApellidoPaterno(entidad.getApellidoPaterno());
            dto.setApellidoMaterno(entidad.getApellidoMaterno());
            dto.setCorreo(entidad.getCorreo());
            dto.setContraseña(entidad.getContraseña());
            dto.setTelefono(entidad.getTelefono());

            if (entidad.getDireccion() != null) {
                DireccionClienteDTO dirDto = new DireccionClienteDTO();
                dirDto.setCalle(entidad.getDireccion().getCalle());
                dirDto.setNumero(entidad.getDireccion().getNumero());
                dirDto.setCodigoPostal(entidad.getDireccion().getCodigoPostal());
                dto.setDireccion(dirDto);
            }
            return dto;
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.CLIENTE_NO_ENCONTRADO, "Error al obtener perfil", ex);
        }
    }

    @Override
    public CarritoDTO agregarAlCarrito(CarritoDTO carrito, ProductoDTO producto, Integer cantidad) throws VentaEnLineaException {
        if (carrito == null) {
            carrito = new CarritoDTO();
        }
        if (producto == null) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Producto inválido.");
        }
        if (cantidad == null || cantidad <= 0 || cantidad > 100) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Cantidad inválida.");
        }
        if (producto.getStock() == null || cantidad > producto.getStock()) {
            throw new VentaEnLineaException(CodigoErrorVenta.STOCK_INSUFICIENTE, "Stock insuficiente para: " + producto.getNombre());
        }

        boolean existe = false;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getProductoSeleccionado().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                item.setSubtotal(item.getCantidad() * producto.getPrecio());
                existe = true;
                break;
            }
        }

        if (!existe) {
            ItemCarritoDTO nuevoItem = new ItemCarritoDTO();
            nuevoItem.setProductoSeleccionado(producto);
            nuevoItem.setCantidad(cantidad);
            nuevoItem.setSubtotal(cantidad * producto.getPrecio());
            carrito.getProductos().add(nuevoItem);
        }

        recalcularTotalesCarrito(carrito);

        return carrito;
    }

    @Override
    public CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws VentaEnLineaException {
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.CARRITO_VACIO, "No hay productos para eliminar.");
        }
        carrito.getProductos().removeIf(item -> item.getProductoSeleccionado().getIdProducto().equals(idProducto));
        recalcularTotalesCarrito(carrito);

        return carrito;
    }

    // TODO Cambiar al NuevoPedidoDTO
    @Override
    public PedidoDTO confirmarCompra(PedidoDTO nuevoPedido) throws VentaEnLineaException {
        if (nuevoPedido == null || nuevoPedido.getCarrito() == null || nuevoPedido.getCarrito().getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.PEDIDO_INVALIDO, "No se puede procesar un pedido vacío.");
        }
        try {
            return pedidosBO.generarPedido(nuevoPedido);
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, e.getMessage(), e);
        }
    }

    @Override
    public PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstadoDTO) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0 || nuevoEstadoDTO == null) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Datos de actualización inválidos.");
        }

        try {
            EstadoPedido estadoDominio = EstadoPedido.valueOf(nuevoEstadoDTO.name());
            Pedido pedidoActualizado = pedidosBO.actualizarEstado(idPedido, estadoDominio);
            return mapearPedidoADTO(pedidoActualizado);
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No se pudo actualizar el estado", e);
        }
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() throws VentaEnLineaException {
        try {
            List<Pedido> pedidosEntidad = pedidosBO.consultarTodos();
            List<PedidoDTO> listaDtos = new ArrayList<>();

            for (Pedido p : pedidosEntidad) {
                listaDtos.add(mapearPedidoADTO(p));
            }
            return listaDtos;
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al obtener pedidos", e);
        }
    }

    @Override
    public ClienteDTO iniciarSesion(String correo, String contrasena) throws VentaEnLineaException {
        try {
            Cliente entidad = clientesBO.iniciarSesion(correo, contrasena);
            ClienteDTO dto = new ClienteDTO();
            dto.setIdUsuario(entidad.getIdUsuario());
            dto.setNombres(entidad.getNombres());
            dto.setApellidoPaterno(entidad.getApellidoPaterno());
            dto.setApellidoMaterno(entidad.getApellidoMaterno());
            dto.setCorreo(entidad.getCorreo());
            dto.setContraseña(entidad.getContraseña());
            dto.setTelefono(entidad.getTelefono());

            if (entidad.getDireccion() != null) {
                DireccionClienteDTO dirDto = new DireccionClienteDTO();
                dirDto.setCalle(entidad.getDireccion().getCalle());
                dirDto.setNumero(entidad.getDireccion().getNumero());
                dirDto.setCodigoPostal(entidad.getDireccion().getCodigoPostal());
                dto.setDireccion(dirDto);
            }
            return dto;
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Credenciales incorrectas", ex);
        }
    }

    @Override
    public PaqueteDTO procesarEmpaqueDePedido(Long idPedido) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de pedido inválido para empaque.");
        }

        try {
            Paquete nuevoPaqueteEntidad = new Paquete();
            Paquete paqueteGuardado = paquetesBO.registrarEmpaque(nuevoPaqueteEntidad);
            return mapearPaqueteADTO(paqueteGuardado);
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al generar el empaque del pedido.", ex);
        }
    }

    private Paquete mapearPaqueteAEntidad(PaqueteDTO dto) {
        if (dto == null) {
            return null;
        }
        Paquete entidad = new Paquete();
        entidad.setIdPaquete(dto.getIdPaquete());
        entidad.setNumeroGuia(dto.getNumeroGuia());
        entidad.setIdEnvio(dto.getIdEnvio());
        entidad.setEstado(dto.getEstado());
        entidad.setFechaEnvio(dto.getFechaEnvio());
        entidad.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        entidad.setFechaEntregaFinal(dto.getFechaEntregaFinal());
        entidad.setUbicacionActual(dto.getUbicacionActual());
        return entidad;
    }

    private PaqueteDTO mapearPaqueteADTO(Paquete entidad) {
        if (entidad == null) {
            return null;
        }
        PaqueteDTO dto = new PaqueteDTO();
        dto.setIdPaquete(entidad.getIdPaquete());
        dto.setNumeroGuia(entidad.getNumeroGuia());
        dto.setIdEnvio(entidad.getIdEnvio());
        dto.setEstado(entidad.getEstado());
        dto.setFechaEnvio(entidad.getFechaEnvio());
        dto.setFechaEntregaEstimada(entidad.getFechaEntregaEstimada());
        dto.setFechaEntregaFinal(entidad.getFechaEntregaFinal());
        dto.setUbicacionActual(entidad.getUbicacionActual());
        return dto;
    }

    private ProductoDTO mapearProductoADTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setIdProducto(p.getIdProducto());
        dto.setNombre(p.getNombre());
        dto.setImgProducto(p.getImgProducto());
        dto.setTipoProducto(p.getTipo());
        dto.setArtista(p.getArtista());
        dto.setGenero(p.getGenero());
        dto.setSetlist(p.getSetlist());
        dto.setPrecio(p.getPrecio());
        dto.setStock(p.getStock());
        dto.setEstado(p.getEstado());
        return dto;
    }

    @Override
    public PedidoDTO obtenerPedidoPorID(Long idPedido) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de pedido inválido.");
        }
        try {
            Pedido entidad = pedidosBO.obtenerPedidoPorId(idPedido);
            return mapearPedidoADTO(entidad);
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No se encontró el pedido", ex);
        }
    }

    private PedidoDTO mapearPedidoADTO(Pedido p) {
        if (p == null) {
            return null;
        }
        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(p.getIdPedido());

        if (p.getEstado() != null) {
            dto.setEstado(EstadoPedidoDTO.valueOf(p.getEstado().name()));
        }

        if (p.getCliente() != null) {
            ClienteDTO clienteDto = new ClienteDTO();
            clienteDto.setIdUsuario(p.getCliente().getIdUsuario());
            clienteDto.setNombres(p.getCliente().getNombres());
            dto.setCliente(clienteDto);
        }

        if (p.getCarrito() != null) {
            CarritoDTO carritoDto = new CarritoDTO();
            carritoDto.setTotal(p.getCarrito().getTotal());
            dto.setCarrito(carritoDto);
        }

        if (p.getPaquete() != null) {
            dto.setPaquete(mapearPaqueteADTO(p.getPaquete()));
        }

        return dto;
    }

    @Override
    public String obtenerNombreCliente(Long idCliente) throws VentaEnLineaException {
        try {
            Cliente cliente = clientesBO.obtenerClientePorId(idCliente);
            String nombreCompleto = cliente.getNombres() + " "
                    + cliente.getApellidoPaterno() + " "
                    + cliente.getApellidoMaterno();
            return nombreCompleto.trim();
        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No fué posible consultar el nombre del cliente");
        }
    }

    @Override
    public List<PedidoDTO> consultarPedidosFiltrados(String nombreCliente, EstadoPedidoDTO estadoDTO) throws VentaEnLineaException {
        try {
            List<Long> idsClientes = null;
            if (nombreCliente != null && !nombreCliente.trim().isEmpty()) {
                idsClientes = clientesBO.buscarIdsPorNombre(nombreCliente);
                if (idsClientes.isEmpty()) {
                    return new ArrayList<>();
                }
            }

            EstadoPedido estadoEntidad = null;
            if (estadoDTO != null) {
                estadoEntidad = EstadoPedido.valueOf(estadoDTO.name());
            }

            List<Pedido> pedidosFiltrados = pedidosBO.buscarPedidosFiltrados(idsClientes, estadoEntidad);

            List<PedidoDTO> dtos = new ArrayList<>();
            for (Pedido p : pedidosFiltrados) {
                dtos.add(mapearPedidoADTO(p));
            }
            return dtos;

        } catch (NegocioException ex) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al filtrar pedidos", ex);
        }
    }

    private void recalcularTotalesCarrito(CarritoDTO carrito) {
        double subtotalGeneral = 0.0;

        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getSubtotal() != null) {
                subtotalGeneral += item.getSubtotal();
            }
        }

        carrito.setSubtotal(subtotalGeneral);

        double impuestos = subtotalGeneral * 0.16;
        carrito.setImpuestos(impuestos);

        carrito.setTotal(subtotalGeneral + impuestos);
    }
}
