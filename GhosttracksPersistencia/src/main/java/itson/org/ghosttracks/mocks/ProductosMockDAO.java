package itson.org.ghosttracks.mocks;

import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emyla
 */
public class ProductosMockDAO implements IProductosDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductosMockDAO.class.getName());
    
    private List<ProductoDTO> productosDB;
    private Long contadorId = 1L;

    public ProductosMockDAO() {
        this.productosDB = new ArrayList<>();
        cargarDatosDummy();
    }

    /**
     * Carga de datos iniciales adaptada EXACTAMENTE a tu ProductoDTO
     */
    private void cargarDatosDummy() {
        
        // --- PRODUCTO 1 ---
        ProductoDTO producto1 = new ProductoDTO(
                1L,                       // idProducto
                "Abbey Road",             // nombre
                "abbey_road.jpg",         // imgProducto
                TipoProducto.VINILO,      // tipoProducto 
                "The Beatles",            // artista
                "Rock",                   // genero
                Arrays.asList("Come Together", "Something", "Maxwell's Silver Hammer", "Oh! Darling", "Octopus's Garden", "I Want You", "Here Comes the Sun"), // setlist
                650.00,                   // precio
                15,                       // stock
                EstadoProducto.DISPONIBLE // estado 
        );
        this.productosDB.add(producto1);
        contadorId++;

        // --- PRODUCTO 2 ---  
        ProductoDTO producto2 = new ProductoDTO(
                2L, 
                "Thriller", 
                "thriller.jpg", 
                TipoProducto.CD,         
                "Michael Jackson", 
                "Pop", 
                Arrays.asList("Wanna Be Startin' Somethin'", "Baby Be Mine", "The Girl Is Mine", "Thriller", "Beat It", "Billie Jean", "Human Nature"),
                350.00, 
                20, 
                EstadoProducto.DISPONIBLE
        );
        this.productosDB.add(producto2);
        contadorId++;

        // --- PRODUCTO 3 ---
        ProductoDTO producto3 = new ProductoDTO(
                3L, 
                "The Dark Side of the Moon", 
                "dark_side.jpg", 
                TipoProducto.CASSETTE,    
                "Pink Floyd", 
                "Rock Progresivo", 
                Arrays.asList("Speak to Me", "Breathe (In the Air)", "On the Run", "Time", "The Great Gig in the Sky", "Money", "Us and Them"), 
                200.00, 
                5, 
                EstadoProducto.DISPONIBLE
        );
        this.productosDB.add(producto3);
        contadorId++;
    }
    
    @Override
    public List<ProductoDTO> obtenerTodos() throws PersistenciaException {
        try {
            return new ArrayList<>(this.productosDB);
        } catch (Exception e) {
            LOGGER.severe("Error crítico e inesperado al intentar obtener todos los productos" + e.getMessage());
            throw new PersistenciaException("Ocurrió un error al consultar el catálogo de productos.", e);
        }
    }
    
    @Override
    public ProductoDTO buscarPorId(Long idProducto) throws PersistenciaException {
        if (idProducto == null) {
            throw new PersistenciaException("El ID del producto a buscar no puede ser nulo");
        }

        try {
            for (ProductoDTO producto : productosDB) {
                if (producto.getIdProducto().equals(idProducto)) {
                    return producto;
                }
            }
            throw new PersistenciaException("Producto no encontrado con el ID: " + idProducto);

        } catch (PersistenciaException e) {
            throw e;

        } catch (Exception e) {
            LOGGER.severe("Error crítico e inesperado al buscar el producto por ID: " + e.getMessage());
            throw new PersistenciaException("Error inesperado al intentar buscar el producto.", e);
        }
    }

    @Override
    public ProductoDTO agregar(ProductoDTO producto) throws PersistenciaException {
        if (producto == null) {
            throw new PersistenciaException("No se puede agregar un producto nulo.");
        }
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new PersistenciaException("El producto debe tener un nombre válido.");
        }

        try {
            producto.setIdProducto(contadorId++);
            this.productosDB.add(producto);
            return producto;
            
        } catch (Exception e) {
            LOGGER.severe("Error crítico e inesperado al intentar agregar un nuevo producto" + e.getMessage());
            throw new PersistenciaException("No se pudo guardar el producto debido a un error interno.", e);
        }
    }
    
}
