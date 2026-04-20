package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class SucursalDTO {
    
    private Long idSucursal;
    
    private DireccionSucursalDTO direccion;
    private String telefono;
    private String nombre;
    
    public SucursalDTO() {
        
    }

    public SucursalDTO(DireccionSucursalDTO direccion, String telefono, String nombre) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public SucursalDTO(Long idSucursal, DireccionSucursalDTO direccion, String telefono, String nombre) {
        this.idSucursal = idSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public Long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public DireccionSucursalDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionSucursalDTO direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
