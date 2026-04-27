/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.org.skydropx.interfaces;

import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import itson.org.skydropx.excepciones.PersistenciaException;

/**
 *
 * @author emyla
 */
public interface IPaquetesDAO {

    /**
     * Busca un paquete en la base de datos simulada por su número de guía.
     * @return El paquete si lo encuentra, o null si no existe.
     */
    public abstract RespuestaRastreoDTO obtenerPorGuia(String numeroGuia) throws PersistenciaException;

    /**
     * Guarda o actualiza la información de un paquete en la base de datos simulada.
     */
    public abstract void guardar(RespuestaRastreoDTO paquete) throws PersistenciaException;
}
