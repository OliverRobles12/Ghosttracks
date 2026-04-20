package itson.org.ghosttracksventaenlinea.fachada;

import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.ProductosBO;
import itson.org.ghosttracksventaenlinea.interfaces.IVentaEnLinea;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class VentaEnLineaFachada implements IVentaEnLinea{
    private final IProductosBO productosBO;

    public VentaEnLineaFachada() {
        this.productosBO = new ProductosBO();
    }

    @Override
    public List<ProductoDTO> obtenerCatalogo() throws Exception {
        return productosBO.obtenerTodos();
    }

    @Override
    public ProductoDTO consultarDetalleProducto(Long id) throws Exception {
        return productosBO.obtenerProductoPorId(id);
    }
}
