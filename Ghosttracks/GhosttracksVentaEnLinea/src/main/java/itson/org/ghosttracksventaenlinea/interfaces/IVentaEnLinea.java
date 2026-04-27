/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracksventaenlinea.interfaces;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IVentaEnLinea {

    List<ProductoDTO> obtenerCatalogo() throws VentaEnLineaException;

    ProductoDTO consultarDetalleProducto(Long id) throws VentaEnLineaException;

    ClienteDTO consultarPerfilCliente(Long idCliente) throws VentaEnLineaException;
    
    CarritoDTO agregarAlCarrito(CarritoDTO carritoActual, ProductoDTO producto, Integer cantidad) throws VentaEnLineaException;
    
    PedidoDTO confirmarCompra(PedidoDTO pedido) throws Exception;
    
    PedidoDTO actualizarEstadoPedido(Long idPedido, EstadoPedidoDTO nuevoEstado) throws VentaEnLineaException;
    
    CarritoDTO eliminarDelCarrito(CarritoDTO carrito, Long idProducto) throws VentaEnLineaException;
    
    List<PedidoDTO> obtenerTodosLosPedidos() throws VentaEnLineaException;
    
    PedidoDTO obtenerPedidoPorID(Long idPedido) throws VentaEnLineaException;
    
    ClienteDTO iniciarSesion(String correo, String contrasena) throws VentaEnLineaException;
    
    /**
     * Orquesta la creación de un paquete a partir de un pedido.
     */
    PaqueteDTO procesarEmpaqueDePedido(Long idPedido) throws VentaEnLineaException;

}
