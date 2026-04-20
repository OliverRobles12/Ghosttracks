package itson.org.ghosttracks.dtos;

import java.time.LocalDate;

/**
 *
 * @author emyla
 */
public class DatosPagoDTO {
    
    private String titularTarjeta;
    private String numeroTrajeta;
    private LocalDate fechaExpiracion;
    private String cvv;

    public DatosPagoDTO() {
    }

    public DatosPagoDTO(
            String titularTarjeta, 
            String numeroTrajeta, 
            LocalDate fechaExpiracion, 
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

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    
    
}
