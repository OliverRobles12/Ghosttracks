/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IPedidosBO {
    Pedido guardarPedido(Pedido pedido) throws NegocioException;
    Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws NegocioException;
    List<Pedido> consultarTodos() throws NegocioException;
    public PedidoDTO generarPedido(PedidoDTO pedidoDto) throws NegocioException;
    Pedido obtenerPedidoPorId(Long idPedido) throws NegocioException;
}
