/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracksventaenlinea.interfaces;

import itson.org.ghosttracks.dtos.ProductoDTO;
import java.util.List;

/**
 *
 * @author nafbr
 */
public interface IVentaEnLinea {
    List<ProductoDTO> obtenerCatalogo() throws Exception;
    ProductoDTO consultarDetalleProducto(Long id) throws Exception;
}
