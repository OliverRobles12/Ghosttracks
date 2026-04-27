/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IPaquetesDAO;
import itson.org.ghosttracks.entidades.Paquete;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class PaquetesMockDAO implements IPaquetesDAO{
    
    private List<Paquete> paquetesDB;
    private Long generadorIds;

    public PaquetesMockDAO() {
        this.paquetesDB = new ArrayList<>();
        this.generadorIds = 1L; // Simulamos el AUTO_INCREMENT de la base de datos
    }

    @Override
    public Paquete agregarPaquete(Paquete paquete) throws PersistenciaException {
        // Le asignamos un ID falso y aumentamos el contador
        paquete.setIdPaquete(generadorIds++);
        paquetesDB.add(paquete);
        
        return paquete;
    }

    @Override
    public Paquete buscarPorId(Long idPaquete) throws PersistenciaException {
        for (Paquete paquete : paquetesDB) {
            if (paquete.getIdPaquete().equals(idPaquete)) {
                return paquete;
            }
        }
        throw new PersistenciaException("No se encontró ningún paquete con el ID: " + idPaquete);
    }

    @Override
    public Paquete actualizarPaquete(Paquete paqueteActualizado) throws PersistenciaException {
        // Buscamos el paquete en la lista y lo reemplazamos
        for (int i = 0; i < paquetesDB.size(); i++) {
            if (paquetesDB.get(i).getIdPaquete().equals(paqueteActualizado.getIdPaquete())) {
                paquetesDB.set(i, paqueteActualizado);
                return paqueteActualizado;
            }
        }
        throw new PersistenciaException("Error al actualizar: Paquete no encontrado.");
    }

    @Override
    public List<Paquete> obtenerTodos() throws PersistenciaException {
        // Retornamos una copia de la lista
        return new ArrayList<>(paquetesDB);
    }
}
