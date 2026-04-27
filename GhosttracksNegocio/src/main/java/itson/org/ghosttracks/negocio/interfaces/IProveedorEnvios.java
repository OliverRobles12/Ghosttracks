/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IProveedorEnvios {
    /**
     * Recupera la información de rastreo de un paquete externo.
     * Aquí podrías devolver un DTO genérico de Ghosttracks o la respuesta de la API
     * si decides que tu sistema la acepte directamente.
     */
    public abstract Object rastrearGuia(String numeroGuia) throws NegocioException;
    
    public String generarGuiaEnvio() throws NegocioException;

}
