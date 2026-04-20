/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IClientesDAO;
import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.entidades.Direccion;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class ClientesMockDAO implements IClientesDAO{
    private List<Cliente> clientesDB;

    public ClientesMockDAO() {
        this.clientesDB = new ArrayList<>();
        Cliente c = new Cliente();
        c.setIdUsuario(1L);
        c.setNombres("Emy");
        c.setApellidoPaterno("Ghost");
        c.setApellidoMaterno("Tracks");
        c.setCorreo("emy@ghosttracks.com");
        c.setContraseña("secreta123");
        c.setTelefono("6441234567");
        
        
        Direccion dir = new Direccion();
        dir.setCalle("Nainari");
        dir.setNumero("123");
        dir.setCodigoPostal("85000");
        c.setDireccion(dir);
        
        clientesDB.add(c);
    }

    @Override
    public Cliente buscarPorId(Long idCliente) throws PersistenciaException {
        for (Cliente cliente : clientesDB) {
            if (cliente.getIdUsuario().equals(idCliente)) {
                return cliente;
            }
        }
        throw new PersistenciaException("Cliente no encontrado con el ID: " + idCliente);
    }
}
