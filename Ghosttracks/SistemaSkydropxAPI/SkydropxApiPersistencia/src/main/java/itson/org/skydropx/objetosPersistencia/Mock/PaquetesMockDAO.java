package itson.org.skydropx.objetosPersistencia.Mock;

import static itson.org.skydrop.enums.EstadoEnvio.ENVIADO;
import static itson.org.skydrop.enums.EstadoEnvio.EN_REPARTO;
import itson.org.skydropx.dtos.MovimientoDTO;
import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import itson.org.skydropx.excepciones.PersistenciaException;
import itson.org.skydropx.interfaces.IPaquetesDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class PaquetesMockDAO implements IPaquetesDAO {

    private static final Logger LOGGER = Logger.getLogger(PaquetesMockDAO.class.getName());
    private static final Map<String, RespuestaRastreoDTO> baseDatos = new HashMap<>();

    public PaquetesMockDAO() {
        if (baseDatos.isEmpty()) {
            cargarDatosDePrueba();
        }
    }

    
    /**
     * Busca un paquete en el diccionario en memoria.
     * @param numeroGuia Guía a buscar.
     * @return El paquete si se encuentra, o null si no existe.
     * @throws PersistenciaException Si ocurre un error inesperado al consultar.
     */
    @Override
    public RespuestaRastreoDTO obtenerPorGuia(String numeroGuia) throws PersistenciaException{
        try {
            return baseDatos.get(numeroGuia);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al intentar leer de la base de datos en memoria.", e);
            throw new PersistenciaException("Error de conexión al consultar la base de datos.", e);
        }
    }

    /**
     * Guarda o actualiza un paquete en el diccionario en memoria.
     * @param paquete El DTO con toda la información a guardar.
     * @throws PersistenciaException Si el paquete es nulo o ocurre un error.
     */
    @Override
    public void guardar(RespuestaRastreoDTO paquete) throws PersistenciaException {
        try {
            if (paquete == null || paquete.getNumeroGuia() == null) {
                throw new IllegalArgumentException("No se puede guardar un paquete nulo o sin número de guía.");
            }
            
            baseDatos.put(paquete.getNumeroGuia(), paquete);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al intentar escribir en la base de datos en memoria.", e);
            throw new PersistenciaException("Error al intentar persistir los datos del paquete.", e);
        }
    }

    /**
     * Llena la base de datos temporal con información base para poder probar el sistema.
     */
    private void cargarDatosDePrueba() {
        
        List<MovimientoDTO> hist1 = new ArrayList<>();
        hist1.add(new MovimientoDTO(LocalDateTime.now().minusHours(2), "Ciudad Obregón, Sonora", "El paquete ha sido documentado."));
        baseDatos.put("11040100400", new RespuestaRastreoDTO("11040100400", ENVIADO, hist1));

        List<MovimientoDTO> hist2 = new ArrayList<>();
        hist2.add(new MovimientoDTO(LocalDateTime.now().minusHours(1), "Hermosillo, Sonora", "Llegada al centro de distribución."));
        hist2.add(new MovimientoDTO(LocalDateTime.now().minusDays(1), "Ciudad Obregón, Sonora", "Paquete recolectado."));
        baseDatos.put("TRK-999", new RespuestaRastreoDTO("TRK-999", EN_REPARTO, hist2));
        
    }
}
