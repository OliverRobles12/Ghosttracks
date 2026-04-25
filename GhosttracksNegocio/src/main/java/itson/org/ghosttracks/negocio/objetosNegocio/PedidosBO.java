package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPedidosDAO;
import itson.org.ghosttracks.entidades.Pedido;
import itson.org.ghosttracks.enums.EstadoPedido;

import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PedidosMockDAO;
import itson.org.ghosttracks.negocio.interfaces.IPedidosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.List;

public class PedidosBO implements IPedidosBO {

    private final IPedidosDAO pedidosDAO;

    public PedidosBO() {
        this.pedidosDAO = new PedidosMockDAO();
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
}
