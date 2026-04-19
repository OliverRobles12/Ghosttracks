
package itson.org.ghosttracks.controladores;

/**
 *
 * @author oliro
 */
public class ControlLogin {
    
    private Navegador navegador;

    public ControlLogin(Navegador navegador) {
        this.navegador = navegador;
    }
    
    public void loginCliente() {
        navegador.iniciarSesionClienteExitoso();
        navegador.irInicioCliente();
    }
    
    public void loginAdmin() {
        navegador.iniciarSesionAdminExitoso();
        navegador.irVentasAdmin();
    }
    
}
