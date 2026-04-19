package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class ContactoDTO {
    
    private Long idUsuario; 
    private String nombre;
    private String correo;
    private String asunto;
    private String mensaje;

    public ContactoDTO() {
    }

    public ContactoDTO(Long idUsuario, String nombre, String correo, String asunto, String mensaje) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
