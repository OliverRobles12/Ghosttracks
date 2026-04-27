/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.skydropx.objetosNegocio.mock;

import itson.org.skydrop.enums.EstadoEnvio;
import itson.org.skydropx.dtos.MovimientoDTO;
import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import itson.org.skydropx.excepciones.NegocioException;
import itson.org.skydropx.excepciones.PersistenciaException;
import itson.org.skydropx.interfaces.IPaquetesDAO;
import itson.org.skydropx.interfaces.ISkydropxAPI;
import itson.org.skydropx.objetosPersistencia.Mock.PaquetesMockDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class SkydropxSimuladoBO implements ISkydropxAPI{

    private final IPaquetesDAO paquetesDAO;
    private static final Logger LOGGER = Logger.getLogger(SkydropxSimuladoBO.class.getName());
    
    public SkydropxSimuladoBO() {
       // CAMBIAR POR MONGO 
        this.paquetesDAO = new PaquetesMockDAO(); 
    }
    
    /**
     * Consulta el historial y estado actual de un paquete mediante su guía.
     * @param numeroGuia El número de guía a rastrear.
     * @return RespuestaRastreoDTO con todos los datos del paquete.
     * @throws NegocioException Si la guía no se encuentra registrada o falla la BD.
     */
    @Override
    public RespuestaRastreoDTO consultarRastreo(String numeroGuia) throws NegocioException {
        try {
            
            RespuestaRastreoDTO paquete = paquetesDAO.obtenerPorGuia(numeroGuia);
            
            if (paquete == null) {
                throw new NegocioException("El número de guía '" + numeroGuia + "' no existe en el sistema de envíos.");
            }
            
            return paquete;
            
        } catch (PersistenciaException ex) {
            LOGGER.log(Level.SEVERE, "Excepción de persistencia al consultar la guía: " + numeroGuia, ex);
            throw new NegocioException("Error al consultar el rastreo en la base de datos.", ex);
        }
    }

    /**
     * Registra un nuevo evento en el historial de ubicaciones de un paquete 
     * y actualiza su estado principal.
     * @param numeroGuia El número de guía al que se le añadirá el movimiento.
     * @param nuevoEstado El estado global actualizado (ej. "En Tránsito").
     * @param movimiento DTO con los detalles del lugar, fecha y descripción.
     * @throws NegocioException Si la guía no existe o falla la BD.
     */
    @Override
    public void registrarNuevoMovimiento(String numeroGuia, EstadoEnvio nuevoEstado, MovimientoDTO movimiento) throws NegocioException {
        
        try {
            RespuestaRastreoDTO paquete = paquetesDAO.obtenerPorGuia(numeroGuia);
            
            if (paquete == null) {
                throw new NegocioException("No se puede registrar el movimiento. La guía '" + numeroGuia + "' no existe.");
            }
            
            paquete.setEstadoActual(nuevoEstado);
            
            if (paquete.getHistorialMovimientos() == null) {
                paquete.setHistorialMovimientos(new ArrayList<>());
            }
            
            paquete.getHistorialMovimientos().add(0, movimiento);
            
            paquetesDAO.guardar(paquete);
            
        } catch (PersistenciaException ex) {
            LOGGER.log(Level.SEVERE, "Error crítico en persistencia al intentar registrar movimiento para la guía: " + numeroGuia, ex);
            throw new NegocioException("Error al actualizar el estado del paquete en la base de datos.", ex);
        }
    }
    
    /**
     * Genera un nuevo número de guía, registra el paquete en el sistema con un estado inicial
     * y devuelve únicamente la guía generada.
     * @return El número de guía generado
     * @throws NegocioException Si ocurre un error al guardar en la base de datos.
     */
    @Override
    public String generarGuiaEnvio() throws NegocioException {
        try {
            String nuevaGuia = "SKY-" + (int)(Math.random() * 100000);
            List<MovimientoDTO> historialInicial = new ArrayList<>();
            historialInicial.add(new MovimientoDTO(
                LocalDateTime.now(), 
                "Sucursal Origen", 
                "Guía generada electrónicamente. Paquete documentado."
            ));
            RespuestaRastreoDTO nuevoPaquete = new RespuestaRastreoDTO(nuevaGuia, EstadoEnvio.ENVIADO, historialInicial);
            paquetesDAO.guardar(nuevoPaquete);

            return nuevaGuia;

        } catch (PersistenciaException ex) {
            LOGGER.log(Level.SEVERE, "Error al generar y guardar la nueva guía.", ex);
            throw new NegocioException("Ocurrió un error al intentar generar la guía de envío en Skydropx.", ex);
        }
    }
    
}
