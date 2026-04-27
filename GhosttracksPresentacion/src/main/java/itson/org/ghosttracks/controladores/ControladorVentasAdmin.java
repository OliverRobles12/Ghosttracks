package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.dtos.PaqueteDTO; 
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
import itson.org.ghosttracksventaenlinea.excepciones.VentaEnLineaException;
import itson.org.ghosttracksventaenlinea.fachada.VentaEnLineaFachada;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.List;

/**
 *
 * @author oliro
 */
public class ControladorVentasAdmin {
    
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
            navegador.mostrarMensaje("Por favor, seleccione un pedido de la tabla primero.", true);
            return;
        }

        try {
            PaqueteDTO paqueteGenerado = ventaFachada.procesarEmpaqueDePedido(this.pedidoSeleccionado.getIdPedido());
            navegador.mostrarMensaje("Empaque generado con éxito. ID de Paquete: " + paqueteGenerado.getIdPaquete(), false);
            navegador.irProcesarPedidoAdmin();
            
        } catch (VentaEnLineaException ex) {
            navegador.mostrarMensaje("No se pudo procesar el pedido: " + ex.getMessage(), true);
        }
    }
    
    public void volverAVentas() {
        navegador.irVentasAdmin();
    }
    
}