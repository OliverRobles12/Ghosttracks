package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.fachada.VentaEnLineaFachada;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author oliro
 */
public class ControladorVentasAdmin {

    private static final Logger LOGGER = Logger.getLogger(ControladorVentasAdmin.class.getName());

    
    private final Navegador navegador;
    private final IVentaEnLinea ventaFachada = new VentaEnLineaFachada();
    private PedidoDTO pedidoSeleccionado;

    public ControladorVentasAdmin(Navegador nav) {
        this.navegador = nav;
    }

    public void llenarTablaPedidos(PantallaVentas vista) {
        try {
            List<PedidoDTO> pedidos = ventaFachada.obtenerTodosLosPedidos();
            vista.llenarTabla(pedidos);

        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al cargar los pedidos: " + ex.getMessage(), true);
        }
    }

    public void seleccionarPedido(Long idPedido, PantallaVentas vista) {
        try {
            this.pedidoSeleccionado = ventaFachada.obtenerPedidoPorID(idPedido);

            if (this.pedidoSeleccionado != null) {
                vista.mostrarDetallesDelPedido(this.pedidoSeleccionado);
            }
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al cargar los detalles del pedido: " + ex.getMessage(), true);
        }
    }

    public void procesarPedido() {
        if (this.pedidoSeleccionado == null) {
            navegador.mostrarMensaje("Por favor, selecciona un pedido de la tabla primero.", true);
            return;
        }

        EstadoPedidoDTO estado = this.pedidoSeleccionado.getEstado();

        switch (estado) {
            case PAGADO:
                // Si está pagado, lo mandamos a la pantalla de confirmar empaque
                navegador.irPantallaConfirmarEmpaque(this.pedidoSeleccionado);
                break;
                
            case EN_PREPARACION:
                // Si ya se está preparando, lo mandamos a la pantalla de ingresar guía y sus datos emy
                navegador.irPantallaConfirmarEnvio(this.pedidoSeleccionado);
                break;
                
            case PENDIENTE:
                navegador.mostrarMensaje("Este pedido aún no ha sido pagado.", true);
                break;
                
            case ENVIADO:
            case ENTREGADO:
                navegador.mostrarMensaje("Este pedido ya fue procesado y enviado/entregado.", false);
                break;
                
            case CANCELADO:
                navegador.mostrarMensaje("Este pedido se encuentra cancelado.", true);
                break;
                
            default:
                navegador.mostrarMensaje("Estado de pedido desconocido.", true);
                break;
        }
    }

    public void volverAVentas() {
        navegador.irVentasAdmin();
    }

    public void actualizarEstadoPedido(PedidoDTO pedido, EstadoPedidoDTO estado) {
        try{
           ventaFachada.actualizarEstadoPedido(pedido.getIdPedido(), estado); 
        } catch(VentaEnLineaException ex){
            navegador.mostrarMensaje("Error en el sistema. No fué posible actualizar el pedido: "+ex, true);
        }
        
    }
    
    public String obtenerNombreClienteCompleto(Long idCliente) {
        try {
            return ventaFachada.obtenerNombreCliente(idCliente); 
        } catch (VentaEnLineaException ex) {
            LOGGER.warning("No se pudo cargar el nombre del cliente");
            return "Cliente Desconocido";
        }
    }
    
    public void aplicarFiltros(String nombreCliente, EstadoPedidoDTO estado, PantallaVentas vista) {
        try {
            List<PedidoDTO> pedidosFiltrados = ventaFachada.consultarPedidosFiltrados(nombreCliente, estado);
            vista.llenarTabla(pedidosFiltrados);
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("Error al filtrar la tabla: " + ex.getMessage(), true);
        }
    }
}
