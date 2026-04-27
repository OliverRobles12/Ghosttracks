package itson.org.skydropx.dtos;

import itson.org.skydrop.enums.EstadoEnvio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class RespuestaRastreoDTO {
    private String numeroGuia;
    private EstadoEnvio estadoActual;
    private List<MovimientoDTO> historialMovimientos;
    
    public RespuestaRastreoDTO() {
        this.historialMovimientos = new ArrayList<>();
    }

    public RespuestaRastreoDTO(String numeroGuia, EstadoEnvio estadoActual, List<MovimientoDTO> historialMovimientos) {
        this.numeroGuia = numeroGuia;
        this.estadoActual = estadoActual;
        this.historialMovimientos = historialMovimientos;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public EstadoEnvio getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoEnvio estadoActual) {
        this.estadoActual = estadoActual;
    }

    public List<MovimientoDTO> getHistorialMovimientos() {
        return historialMovimientos;
    }

    public void setHistorialMovimientos(List<MovimientoDTO> historialMovimientos) {
        this.historialMovimientos = historialMovimientos;
    }
       
}
