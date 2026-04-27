package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.skydropx.interfaces.ISkydropxAPI;
import itson.org.skydropx.objetosNegocio.mock.SkydropxSimuladoBO;
import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adaptador que conecta Ghosttracks con la API de Skydropx.
 */
public class SkydropxAdapter implements IProveedorEnvios {

    private final ISkydropxAPI skydropxAPI;
    private static final Logger LOGGER = Logger.getLogger(SkydropxAdapter.class.getName());

    public SkydropxAdapter() {
        this.skydropxAPI = new SkydropxSimuladoBO();
    }

    @Override
    public RespuestaRastreoDTO rastrearGuia(String numeroGuia) throws NegocioException {
        try {
            LOGGER.log(Level.INFO, "Consultando rastreo en Skydropx para guía: {0}", numeroGuia);
            
            // Llamamos a la API externa
            RespuestaRastreoDTO respuesta = skydropxAPI.consultarRastreo(numeroGuia);
            
            if (respuesta == null) {
                throw new NegocioException("No se recibió respuesta del servidor de envíos.");
            }

            return respuesta;

        } catch (itson.org.skydropx.excepciones.NegocioException e) {
            // Traducimos la excepción de la API a una excepción de nuestro sistema
            LOGGER.log(Level.WARNING, "Skydropx reportó un error: {0}", e.getMessage());
            throw new NegocioException("Error del proveedor de envíos: " + e.getMessage());
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al conectar con Skydropx", e);
            throw new NegocioException("No se pudo establecer conexión con el servicio de rastreo.");
        }
    }
}