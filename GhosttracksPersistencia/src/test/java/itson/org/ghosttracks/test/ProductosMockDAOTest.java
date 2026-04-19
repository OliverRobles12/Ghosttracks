package itson.org.ghosttracks.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.enums.EstadoProducto;
import itson.org.ghosttracks.enums.TipoProducto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.ProductosMockDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas unitarias para ProductosMockDAO.
 * @author emyla
 */
public class ProductosMockDAOTest {
    
    private ProductosMockDAO dao;

    @BeforeEach
    public void init() {
        // Setup general: Se crea una nueva instancia del DAO antes de cada test.
        // Esto garantiza que los 3 productos dummy originales siempre se carguen limpios.
        this.dao = new ProductosMockDAO();
    }

    public ProductosMockDAOTest() {
    }

    @Test
    public void testObtenerTodosHappyPath() {
        // Setup
        
        // Ejecución
        assertDoesNotThrow(() -> {
            List<ProductoDTO> listaProductos = dao.obtenerTodos();
            
            // Verificación
            assertNotNull(listaProductos);
            // Esperamos 3 productos porque el método cargarDatosDummy() mete 3 por defecto
            assertEquals(3, listaProductos.size()); 
        });
        
        // Tear down
    }

    @Test
    public void testBuscarPorIdHappyPath() {
        // Setup
        Long idProductoBuscado = 1L; // Sabemos que el ID 1 es Abbey Road
        
        // Ejecución 
        assertDoesNotThrow(() -> {
            ProductoDTO producto = dao.buscarPorId(idProductoBuscado);
            
            // Verificación
            assertNotNull(producto);
            assertEquals(idProductoBuscado, producto.getIdProducto());
            assertEquals("Abbey Road", producto.getNombre());
        });
        
        // Tear down
    }

    @Test
    public void testBuscarPorIdNoExiste() {
        // Setup
        Long idProductoFake = 99L; // Un ID que sabemos que no está en los dummies
        
        // Ejecución y Verificación
        // Se espera que lance PersistenciaException por no encontrarlo
        assertThrows(PersistenciaException.class, () -> {
            dao.buscarPorId(idProductoFake);
        });
        
        // Tear down
    }

    @Test
    public void testBuscarPorIdNulo() {
        // Setup
        Long idNulo = null;
        
        // Ejecución y Verificación
        assertThrows(PersistenciaException.class, () -> {
            dao.buscarPorId(idNulo);
        });
    }

    @Test
    public void testAgregarHappyPath() {
        // Setup
        ProductoDTO nuevoProducto = new ProductoDTO(
                null, // El ID debe generarse automáticamente
                "Random Access Memories", 
                "ram.jpg", 
                TipoProducto.VINILO, 
                "Daft Punk", 
                "Electronic", 
                new ArrayList<>(), 
                550.00, 
                10, 
                EstadoProducto.DISPONIBLE
        );
        
        // Ejecución
        assertDoesNotThrow(() -> {
            ProductoDTO productoGuardado = dao.agregar(nuevoProducto);
            
            // Verificación
            assertNotNull(productoGuardado.getIdProducto());
            // Como ya había 3 productos, el nuevo debería recibir el ID 4L
            assertEquals(4L, productoGuardado.getIdProducto()); 
            assertEquals(nuevoProducto.getNombre(), productoGuardado.getNombre());
            
            // Verificamos que realmente se añadió a la lista general
            assertEquals(4, dao.obtenerTodos().size());
        });
        
        // Tear down
    }

    @Test
    public void testAgregarProductoNulo() {
        // Setup
        ProductoDTO productoNulo = null;
        
        // Ejecución y Verificación
        assertThrows(PersistenciaException.class, () -> {
            dao.agregar(productoNulo);
        });
    }

    @Test
    public void testAgregarProductoNombreVacio() {
        // Setup
        ProductoDTO productoInvalido = new ProductoDTO(
                null, 
                "", // Nombre vacío intencionalmente
                "img.jpg", 
                TipoProducto.CD, 
                "Artista", 
                "Pop", 
                new ArrayList<>(), 
                100.00, 
                5, 
                EstadoProducto.DISPONIBLE
        );
        
        // Ejecución y Verificación
        assertThrows(PersistenciaException.class, () -> {
            dao.agregar(productoInvalido);
        });
    }
}
