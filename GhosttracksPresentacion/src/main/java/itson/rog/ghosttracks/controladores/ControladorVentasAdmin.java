
package itson.rog.ghosttracks.controladores;

/**
 *
 * @author oliro
 */
public class ControladorVentasAdmin {
    
    private final Navegador navegador;
    
    public ControladorVentasAdmin(Navegador nav) {
        this.navegador = nav;
    }
    
    public void procesarPedido() {
        navegador.irProcesarPedidoAdmin();
    }
    
    public void volverAVentas() {
        navegador.irVentasAdmin();
    }
    
}
