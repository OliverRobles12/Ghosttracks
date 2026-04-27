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
     * @param numeroGuia
     * @return 
     * @throws itson.org.skydropx.excepciones.NegocioException
     */
    public abstract RespuestaRastreoDTO consultarRastreo(String numeroGuia) throws NegocioException;

    /**
     * Agrega un nuevo evento al historial de la guía 
     * @param numeroGuia
     * @param nuevoEstado
     * @param movimiento
     * @throws itson.org.skydropx.excepciones.NegocioException
     */
    public abstract void registrarNuevoMovimiento(String numeroGuia, EstadoEnvio nuevoEstado, MovimientoDTO movimiento) throws NegocioException;
    
    /**
     * Genera un nuevo número de guía y lo registra en el sistema de paquetería.
     * @return El número de guía generado en formato String.
     * @throws NegocioException Si ocurre un error al registrarlo.
     */
    public abstract String generarGuiaEnvio() throws NegocioException;
}
