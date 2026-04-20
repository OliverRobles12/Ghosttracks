/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class PedidosMockDAO implements IPedidosDAO{
    private List<Pedido> baseDatosPedidos = new ArrayList<>();
    private Long idAutoincrementable = 1L;

    @Override
    public Pedido guardarPedido(Pedido pedido) throws PersistenciaException {
        try {
            pedido.setIdPedido(idAutoincrementable++);
            baseDatosPedidos.add(pedido);
            return pedido;
        } catch (Exception e) {
            throw new PersistenciaException("Error al guardar el pedido en BD: " + e.getMessage());
        }
    }
    
    @Override
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws PersistenciaException {
        for (Pedido pedido : baseDatosPedidos) {
            if (pedido.getIdPedido().equals(idPedido)) {
                pedido.setEstado(nuevoEstado);
                return pedido; 
            }
        }
        throw new PersistenciaException("No se encontró ningún pedido con el ID: " + idPedido);
    }
}
