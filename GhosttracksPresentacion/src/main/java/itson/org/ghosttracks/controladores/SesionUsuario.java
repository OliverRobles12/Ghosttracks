package itson.org.ghosttracks.controladores;

import itson.org.ghosttracks.dtos.ClienteDTO;

public class SesionUsuario {
    
    private static SesionUsuario instancia;
    private ClienteDTO clienteLogueado;
    private SesionUsuario() {}

    public static SesionUsuario getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    public void setCliente(ClienteDTO cliente) {
        this.clienteLogueado = cliente;
    }

    public ClienteDTO getCliente() {
        return this.clienteLogueado;
    }

    public void cerrarSesion() {
        this.clienteLogueado = null;
    }

    public boolean haySesionActiva() {
        return this.clienteLogueado != null;
    }
}