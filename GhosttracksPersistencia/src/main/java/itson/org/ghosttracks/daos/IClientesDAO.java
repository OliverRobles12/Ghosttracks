/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.entidades.Cliente;
import itson.org.ghosttracks.exceptions.PersistenciaException;

/**
 *
 * @author nafbr
 */
public interface IClientesDAO {
    Cliente buscarPorId(Long idCliente) throws PersistenciaException;
}
