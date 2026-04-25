/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nafbr
 */
public class PedidosMockDAO implements IPedidosDAO{
    
    private static List<Pedido> baseDatosPedidos = new ArrayList<>();
    private static Long idAutoincrementable = 1L;
    private static final Logger LOGGER = Logger.getLogger(PedidosMockDAO.class.getName());
    private static boolean datosPrecargados = false;
    
    public PedidosMockDAO() {
        if (!datosPrecargados) {
            precargarPedidos();
            datosPrecargados = true;
        }
    }

    
    private void precargarPedidos() {
        Pedido p1 = new Pedido();
        p1.setIdPedido(idAutoincrementable++);
        p1.setIdCliente(101L);
        p1.setTotal(1500.50);
        p1.setEstado(EstadoPedido.PAGADO); 
        baseDatosPedidos.add(p1);

        Pedido p2 = new Pedido();
        p2.setIdPedido(idAutoincrementable++);
        p2.setIdCliente(102L);
        p2.setTotal(340.00);
        p2.setEstado(EstadoPedido.PAGADO); 
        baseDatosPedidos.add(p2);

        Pedido p3 = new Pedido();
        p3.setIdPedido(idAutoincrementable++);
        p3.setIdCliente(103L);
        p3.setTotal(899.99);
        p3.setEstado(EstadoPedido.PAGADO); 
        baseDatosPedidos.add(p3);
        
        Pedido p4 = new Pedido();
        p4.setIdPedido(idAutoincrementable++);
        p4.setIdCliente(104L);
        p4.setTotal(250.00);
        p4.setEstado(EstadoPedido.PAGADO); 
        baseDatosPedidos.add(p4);
    }
    
    
    @Override
    public Pedido guardarPedido(Pedido pedido) throws PersistenciaException {
        try {
            pedido.setIdPedido(idAutoincrementable++);
            baseDatosPedidos.add(pedido);
            
            LOGGER.log(Level.INFO, "ENTIDAD PERSISTIDA: Pedido guardado exitosamente con ID {0}", pedido.getIdPedido());
            return pedido;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al intentar persistir el pedido en la lista interna", e);
            throw new PersistenciaException("Error al guardar el pedido en BD: " + e.getMessage());
        }
    }
    
    @Override
    public Pedido actualizarEstado(Long idPedido, EstadoPedido nuevoEstado) throws PersistenciaException {
        for (Pedido pedido : baseDatosPedidos) {
            if (pedido.getIdPedido().equals(idPedido)) {
                pedido.setEstado(nuevoEstado);
                LOGGER.log(Level.INFO, "Estado actualizado correctamente para el pedido {0}", idPedido);
                return pedido; 
            }
        }
        LOGGER.log(Level.WARNING, "No se pudo actualizar: Pedido ID {0} no encontrado.", idPedido);
        throw new PersistenciaException("No se encontró ningún pedido con el ID: " + idPedido);
    }
    
    @Override
    public List<Pedido> consultarTodos() throws PersistenciaException {
        try {
            return new ArrayList<>(baseDatosPedidos);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar la lista de pedidos", e);
            throw new PersistenciaException("Error al consultar los pedidos: " + e.getMessage());
        }
    }
    
    @Override
    public Pedido consultarPorId(Long idPedido) throws PersistenciaException {
        return baseDatosPedidos.stream()
                .filter(p -> p.getIdPedido().equals(idPedido))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.log(Level.WARNING, "Consulta fallida: Pedido {0} no existe.", idPedido);
                    return new PersistenciaException("Pedido no encontrado: " + idPedido);
                });
    }
}
