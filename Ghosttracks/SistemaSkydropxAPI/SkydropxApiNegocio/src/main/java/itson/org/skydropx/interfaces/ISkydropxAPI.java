package itson.org.skydropx.interfaces;

import itson.org.skydrop.enums.EstadoEnvio;
import itson.org.skydropx.dtos.MovimientoDTO;
import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import itson.org.skydropx.excepciones.NegocioException;

/**
 *
 * @author Emy
 */
public interface ISkydropxAPI {

    /**
     * Devuelve la información de un paquete (Usado por Cliente y Admin)
     */
    public abstract RespuestaRastreoDTO consultarRastreo(String numeroGuia) throws NegocioException;

    /**
     * Agrega un nuevo evento al historial de la guía 
     */
    public abstract void registrarNuevoMovimiento(String numeroGuia, EstadoEnvio nuevoEstado, MovimientoDTO movimiento) throws NegocioException;
    
}
