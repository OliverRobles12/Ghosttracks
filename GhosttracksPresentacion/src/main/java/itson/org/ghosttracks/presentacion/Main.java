
package itson.org.ghosttracks.presentacion;

import itson.rog.ghosttracks.controladores.Navegador;

/**
 *
 * @author oliro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        VentanaPrincipal ventana = new VentanaPrincipal();
        Navegador navegador = new Navegador(ventana);
        navegador.irLogin();
        ventana.setVisible(true);
        
    }
    
}
