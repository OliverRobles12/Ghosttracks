/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracksventaenlinea.interfaces;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IVentaEnLinea {

    List<ProductoDTO> obtenerCatalogo() throws Exception;

    ProductoDTO consultarDetalleProducto(Long id) throws Exception;

    ClienteDTO consultarPerfilCliente(Long idCliente) throws Exception;
    
    CarritoDTO agregarAlCarrito(CarritoDTO carritoActual, ProductoDTO producto, Integer cantidad) throws Exception;
    
    PedidoDTO confirmarCompra(PedidoDTO pedido) throws Exception;
    
    PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstado) throws Exception;
    
    CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws Exception;
    
    List<PedidoDTO> obtenerTodosLosPedidos() throws Exception;
}
