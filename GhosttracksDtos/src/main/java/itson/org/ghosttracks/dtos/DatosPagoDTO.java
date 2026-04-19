package itson.org.ghosttracks.dtos;

/**
 *
 * @author emyla
 */
public class DatosPagoDTO {
    
    private String titularTarjeta;
    private String numeroTrajeta;
    private String fechaExpiracion;
    private String cvv;

    public DatosPagoDTO() {
    }

    public DatosPagoDTO(
            String titularTarjeta, 
            String numeroTrajeta, 
            String fechaExpiracion, 
            String cvv
    ) {
        this.titularTarjeta = titularTarjeta;
        this.numeroTrajeta = numeroTrajeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTrajeta() {
        return numeroTrajeta;
    }

    public void setNumeroTrajeta(String numeroTrajeta) {
        this.numeroTrajeta = numeroTrajeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    
    
}
