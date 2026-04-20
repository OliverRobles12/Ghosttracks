
package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.PedidoDTO;
import itson.org.ghosttracks.presentacion.administrador.PantallaVentas;
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
    
    public ControladorVentasAdmin(Navegador nav) {
        this.navegador = nav;
    }
    
    public void llenarTablaPedidos(PantallaVentas vista) {
        try {
            List<PedidoDTO> pedidos = ventaFachada.obtenerTodosLosPedidos();
            vista.llenarTabla(pedidos);
            
        } catch (Exception ex) {
            navegador.mostrarMensaje("Error al cargar los pedidos: " + ex.getMessage(), true);
        }
    }
    
    public void procesarPedido() {
        // logica de obtener pedido
        navegador.irProcesarPedidoAdmin();
    }
    
    public void volverAVentas() {
        navegador.irVentasAdmin();
    }
    
}
