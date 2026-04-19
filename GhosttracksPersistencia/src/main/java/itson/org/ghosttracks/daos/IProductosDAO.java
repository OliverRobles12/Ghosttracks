package itson.org.ghosttracks.daos;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.List;

/**
 *
 * @author emyla
 */
public interface IProductosDAO {
    
    public abstract List<ProductoDTO> obtenerTodos() throws PersistenciaException;
    
    public abstract ProductoDTO buscarPorId(Long idProducto) throws PersistenciaException;
    
    public abstract ProductoDTO agregar(ProductoDTO producto) throws PersistenciaException;
    
}
