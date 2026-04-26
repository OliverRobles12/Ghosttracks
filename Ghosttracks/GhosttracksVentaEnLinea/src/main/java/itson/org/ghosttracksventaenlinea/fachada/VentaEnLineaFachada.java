package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.DireccionClienteDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.ProductoPedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.ghosttracks.negocio.objetosNegocio.PedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ProductosBO;
import itson.org.ghosttracksventaenlinea.excepciones.CodigoErrorVenta;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.time.LocalDateTime;
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

    public VentaEnLineaFachada() {
        this.productosBO = new ProductosBO();
        this.clientesBO = new ClientesBO();
        this.pedidosBO = new PedidosBO();
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
        if(cantidad == null || cantidad <= 0 || cantidad > 100){
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "Cantidad inválida.");
        }
        if (producto.getStock() == null || cantidad > producto.getStock()) {
            throw new VentaEnLineaException(CodigoErrorVenta.STOCK_INSUFICIENTE, "Stock insuficiente para: " + producto.getNombre());
        }

        boolean existe = false;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getProductoSeleccionado().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                existe = true;
                break;
            }
        }

        if (!existe) {
            ItemCarritoDTO nuevoItem = new ItemCarritoDTO();
            nuevoItem.setProductoSeleccionado(producto);
            nuevoItem.setCantidad(cantidad);
            carrito.getProductos().add(nuevoItem);
        }

        carrito.calcularTotalGeneral();
        return carrito;
    }

    @Override
    public CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws VentaEnLineaException {
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.CARRITO_VACIO, "No hay productos para eliminar.");
        }
        carrito.getProductos().removeIf(item
                -> item.getProductoSeleccionado().getIdProducto().equals(idProducto)
        );
        carrito.calcularTotalGeneral();
        return carrito;
    }

    @Override
    public PedidoDTO confirmarCompra(PedidoDTO pedidoDto) throws VentaEnLineaException {
        if (pedidoDto == null || pedidoDto.getProductos() == null || pedidoDto.getProductos().isEmpty()) {
            throw new VentaEnLineaException(CodigoErrorVenta.PEDIDO_INVALIDO, "No se puede procesar un pedido vacío.");
        }

        try {
            return pedidosBO.generarPedido(pedidoDto);

        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, e.getMessage(), e);
        }
    }

    @Override
    public PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstadoDTO) throws VentaEnLineaException {
        if (idPedido == null || idPedido <= 0) {
            throw new VentaEnLineaException(CodigoErrorVenta.DATOS_INVALIDOS, "ID de pedido inválido.");
        }
        if (nuevoEstadoDTO == null) {
            throw new VentaEnLineaException(CodigoErrorVenta.ESTADO_INVALIDO, "Estado inválido proporcionado.");
        }

        try {
            EstadoPedido estadoDominio = EstadoPedido.valueOf(nuevoEstadoDTO.name());
            Pedido pedidoActualizado = pedidosBO.actualizarEstado(idPedido, estadoDominio);

            PedidoDTO dto = new PedidoDTO();
            dto.setIdPedido(pedidoActualizado.getIdPedido());
            dto.setTotal(pedidoActualizado.getTotal());
            dto.setSubtotal(pedidoActualizado.getSubtotal());
            dto.setCostoEnvio(pedidoActualizado.getCostoEnvio());
            dto.setEstado(EstadoPedidoDTO.valueOf(pedidoActualizado.getEstado().name()));
            return dto;

        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "No se pudo actualizar el estado", e);
        }
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() throws VentaEnLineaException {
        try {
            List<Pedido> pedidosEntidad = pedidosBO.consultarTodos();
            List<PedidoDTO> pedidosDTO = new ArrayList<>();

            for (Pedido p : pedidosEntidad) {
                EstadoPedidoDTO estadoDTO = p.getEstado() != null ? EstadoPedidoDTO.valueOf(p.getEstado().name()) : null;
                PedidoDTO dto = new PedidoDTO(p.getIdPedido(), p.getIdCliente(), estadoDTO, p.getTotal());
                pedidosDTO.add(dto);
            }
            return pedidosDTO;
        } catch (NegocioException e) {
            throw new VentaEnLineaException(CodigoErrorVenta.ERROR_PERSISTENCIA, "Error al obtener pedidos", e);
        }
    }

    // Método utilitario privado para no repetir código jeje
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
