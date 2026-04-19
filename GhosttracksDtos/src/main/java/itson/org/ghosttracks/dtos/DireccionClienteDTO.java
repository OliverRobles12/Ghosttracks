package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class DireccionClienteDTO {
    
    private Long idDireccion;
    private String calle;
    private String numero;
    private String colonia;
    private String codigoPostal;
    private String ciudad;
    private String estado;

    public DireccionClienteDTO() {
    }

    public DireccionClienteDTO(
            Long idDireccion, 
            String calle, 
            String numero, 
            String colonia, 
            String codigoPostal, 
            String ciudad, 
            String estado
    ) {
        this.idDireccion = idDireccion;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
    }
    
    
}
