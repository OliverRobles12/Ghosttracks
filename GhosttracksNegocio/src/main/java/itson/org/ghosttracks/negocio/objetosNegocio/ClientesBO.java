/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IClientesDAO;
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
    public Cliente obtenerClientePorId(Long idCliente) throws NegocioException {
        try {
            return clientesDAO.buscarPorId(idCliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener cliente en BD", e);
        }
    }
}
