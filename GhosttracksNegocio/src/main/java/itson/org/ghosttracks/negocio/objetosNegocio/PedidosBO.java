package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.entidades.ProductoPedido;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.enums.EstadoPedido;

import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidosBO implements IPedidosBO {
    private final IPedidosDAO pedidosDAO;

    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
    }

    @Override
    public PedidoDTO generarPedido(PedidoDTO pedidoDto) throws NegocioException {
        if (pedidoDto == null || pedidoDto.getProductos() == null || pedidoDto.getProductos().isEmpty()) {
            throw new NegocioException("No se puede procesar un pedido vacío.");
        }

        try {
            if (pedidoDto.getTotal() == null || pedidoDto.getTotal() == 0) {
                pedidoDto.calcularTotales(150.0); 
            }

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
            
            return pedidoDto;
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Ocurrió un error al intentar registrar su pedido: " + e.getMessage());
        }
    }
    
    @Override
    public PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstadoDTO) throws NegocioException {
        if (idPedido == null || idPedido <= 0) {
            throw new NegocioException("El ID del pedido no es válido.");
        }
        if (nuevoEstadoDTO == null) {
            throw new NegocioException("Debe proporcionar un estado válido para actualizar.");
        }

        try {
            EstadoPedido estadoDominio = EstadoPedido.valueOf(nuevoEstadoDTO.name());
            Pedido pedidoActualizado = pedidosDAO.actualizarEstado(idPedido, estadoDominio);

            PedidoDTO dto = new PedidoDTO();
            dto.setIdPedido(pedidoActualizado.getIdPedido());
            dto.setTotal(pedidoActualizado.getTotal());
            dto.setSubtotal(pedidoActualizado.getSubtotal());
            dto.setCostoEnvio(pedidoActualizado.getCostoEnvio());
            EstadoPedidoDTO estadoRegresoDTO = EstadoPedidoDTO.valueOf(pedidoActualizado.getEstado().name());
            dto.setEstado(estadoRegresoDTO);
            
            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el estado del pedido: " + e.getMessage());
        }
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() throws NegocioException {
        try {
            List<Pedido> pedidosEntidad = pedidosDAO.consultarTodos();
            List<PedidoDTO> pedidosDTO = new ArrayList<>();

            for (Pedido p : pedidosEntidad) {
                Long id = p.getIdPedido();
                Long cliente = p.getIdCliente();
                Double total = p.getTotal(); 
                
                EstadoPedidoDTO estadoDTO = null;
                if (p.getEstado() != null) {
                    estadoDTO = EstadoPedidoDTO.valueOf(p.getEstado().name());
                }
                PedidoDTO dto = new PedidoDTO(id, cliente, estadoDTO, total);
                pedidosDTO.add(dto);
            }

            return pedidosDTO;
            
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener la lista de pedidos: " + e.getMessage());
        }
    }
    
    
    
}