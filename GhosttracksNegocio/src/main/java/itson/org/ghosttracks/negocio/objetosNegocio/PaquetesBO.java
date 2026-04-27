/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IPaquetesDAO;
import itson.org.ghosttracks.dtos.PaqueteDTO;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.PaquetesMockDAO;
import itson.org.ghosttracks.negocio.adaptador.SkydropxAdapter;
import itson.org.ghosttracks.negocio.interfaces.IPaquetesBO;
import itson.org.ghosttracks.negocio.interfaces.IProveedorEnvios;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.skydropx.dtos.RespuestaRastreoDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class PaquetesBO implements IPaquetesBO{
    
    private final IPaquetesDAO paquetesDAO;
    private final IProveedorEnvios proveedorEnvios;
    
    public PaquetesBO() {
        this.paquetesDAO = new PaquetesMockDAO();
        this.proveedorEnvios = new SkydropxAdapter();
    }

    @Override
    public Paquete registrarEmpaque(Paquete paquete) throws NegocioException {
        try {
            paquete.setFechaEnvio(LocalDateTime.now());
            return paquetesDAO.agregarPaquete(paquete);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo registrar el empaque del pedido.", ex);
        }
    }

    @Override
    public Paquete asignarNumeroGuia(Long idPaquete, String numeroGuia) throws NegocioException {
        try {
            Paquete paquete = paquetesDAO.buscarPorId(idPaquete);
            paquete.setNumeroGuia(numeroGuia);
            return paquetesDAO.actualizarPaquete(paquete);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al asignar la guía de rastreo.", ex);
        }
    }

    @Override
    public Paquete consultarPaquetePorId(Long idPaquete) throws NegocioException {
        try {
            return paquetesDAO.buscarPorId(idPaquete);
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se encontró el paquete solicitado.", ex);
        }
    }

    @Override
    public List<Paquete> consultarTodos() throws NegocioException {
        try {
            return paquetesDAO.obtenerTodos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cargar la lista de paquetes.", ex);
        }
    }
    
    @Override
    public RespuestaRastreoDTO rastrearPaquete(Long idPaquete) throws NegocioException {
        try {
            Paquete paqueteLocal = paquetesDAO.buscarPorId(idPaquete);
            
            if (paqueteLocal.getNumeroGuia() == null) {
                throw new NegocioException("El paquete no cuenta con número de guía.");
            }
            return (RespuestaRastreoDTO) proveedorEnvios.rastrearGuia(paqueteLocal.getNumeroGuia());

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar la base de datos local.");
        }
    }
}
