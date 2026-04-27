package itson.org.skydropx.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author emyla
 */
public class MovimientoDTO {

    private LocalDateTime fechaHora;
    private String ubicacion;
    private String descripcion;
    
    public MovimientoDTO(){
    
    }

    public MovimientoDTO(LocalDateTime fechaHora, String ubicacion, String descripcion) {
        this.fechaHora = fechaHora;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
