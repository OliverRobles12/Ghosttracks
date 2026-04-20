package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.EstadoPaquete;
import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class Paquete {
   
    private Long idPaquete;
    private String numeroGuia;
    private String idEnvio;
    private EstadoPaquete estado; 
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntregaEstimada;
    private LocalDateTime fechaEntregaFinal;
    private String ubicacionActual;

    public Paquete() {
    }

    public Paquete(String numeroGuia, String idEnvio, EstadoPaquete estado, LocalDateTime fechaEnvio, LocalDateTime fechaEntregaEstimada, LocalDateTime fechaEntregaFinal, String ubicacionActual) {
        this.numeroGuia = numeroGuia;
        this.idEnvio = idEnvio;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.fechaEntregaFinal = fechaEntregaFinal;
        this.ubicacionActual = ubicacionActual;
    }

    public Paquete(Long idPaquete, String numeroGuia, String idEnvio, EstadoPaquete estado, LocalDateTime fechaEnvio, LocalDateTime fechaEntregaEstimada, LocalDateTime fechaEntregaFinal, String ubicacionActual) {
        this.idPaquete = idPaquete;
        this.numeroGuia = numeroGuia;
        this.idEnvio = idEnvio;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.fechaEntregaFinal = fechaEntregaFinal;
        this.ubicacionActual = ubicacionActual;
    }

    public Long getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(Long idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDateTime fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public LocalDateTime getFechaEntregaFinal() {
        return fechaEntregaFinal;
    }

    public void setFechaEntregaFinal(LocalDateTime fechaEntregaFinal) {
        this.fechaEntregaFinal = fechaEntregaFinal;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }
    
    
    
}
