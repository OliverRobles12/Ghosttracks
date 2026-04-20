/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IPedidosDAO {
    Pedido guardarPedido(Pedido pedido) throws PersistenciaException;
    
    Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws PersistenciaException;
    
    List<Pedido> consultarTodos() throws PersistenciaException;
}
