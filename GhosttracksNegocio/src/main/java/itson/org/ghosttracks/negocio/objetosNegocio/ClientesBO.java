/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IClientesDAO;
import itson.org.ghosttracks.dtos.ClienteDTO;
import itson.org.ghosttracks.dtos.DireccionClienteDTO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.ClientesMockDAO;
import itson.org.ghosttracks.negocio.interfaces.IClientesBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author nafbr
 */
public class ClientesBO implements IClientesBO{
    private final IClientesDAO clientesDAO;

    public ClientesBO() {
        this.clientesDAO = new ClientesMockDAO();
    }

    @Override
    public ClienteDTO obtenerClientePorId(Long idCliente) throws NegocioException {
        try {
            Cliente entidad = clientesDAO.buscarPorId(idCliente);
            ClienteDTO dto = new ClienteDTO();
            
            dto.setIdUsuario(entidad.getIdUsuario());
            dto.setNombres(entidad.getNombres());
            dto.setApellidoPaterno(entidad.getApellidoPaterno());
            dto.setApellidoMaterno(entidad.getApellidoMaterno());
            dto.setCorreo(entidad.getCorreo());
            dto.setContraseña(entidad.getContraseña());
            dto.setTelefono(entidad.getTelefono());
            
            if (entidad.getDireccion() != null) {
                DireccionClienteDTO dirDto = new DireccionClienteDTO();
                dirDto.setCalle(entidad.getDireccion().getCalle());
                dirDto.setNumero(entidad.getDireccion().getNumero());
                dirDto.setCodigoPostal(entidad.getDireccion().getCodigoPostal());
                
                dto.setDireccion(dirDto);
            }

            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener cliente: " + e.getMessage());
        }
    }
}
