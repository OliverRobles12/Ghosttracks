/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.daos.IProductosDAO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.entidades.Producto;
import itson.org.ghosttracks.exceptions.PersistenciaException;
import itson.org.ghosttracks.mocks.ProductosMockDAO;
import itson.org.ghosttracks.negocio.interfaces.IProductosBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class ProductosBO implements IProductosBO {

    private final IProductosDAO productosDAO;

    public ProductosBO() {
        this.productosDAO = new ProductosMockDAO();
    }

    @Override
    public List<ProductoDTO> obtenerTodos() throws NegocioException {
        try {
            List<Producto> productosEntidad = productosDAO.obtenerTodos();
            List<ProductoDTO> productosDisponibles = new ArrayList<>();

            for (Producto p : productosEntidad) {
                if (p.getStock() != null && p.getStock() > 0) {
                    ProductoDTO dto = new ProductoDTO();
                    dto.setIdProducto(p.getIdProducto());
                    dto.setNombre(p.getNombre());
                    dto.setImgProducto(p.getImgProducto());
                    dto.setTipoProducto(p.getTipo());
                    dto.setArtista(p.getArtista());
                    dto.setGenero(p.getGenero());
                    dto.setSetlist(p.getSetlist());
                    dto.setPrecio(p.getPrecio());
                    dto.setStock(p.getStock());
                    dto.setEstado(p.getEstado());
                    
                    productosDisponibles.add(dto);
                }
            }
            return productosDisponibles;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al consultar el catálogo de productos: " + e.getMessage());
        }
    }

    @Override
    public ProductoDTO obtenerProductoPorId(Long id) throws NegocioException {
        if (id == null || id <= 0) {
            throw new NegocioException("El ID de producto proporcionado no es válido.");
        }

        try {
            Producto entidad = productosDAO.buscarPorId(id);
            ProductoDTO dto = new ProductoDTO();
            
            dto.setIdProducto(entidad.getIdProducto());
            dto.setNombre(entidad.getNombre());
            dto.setImgProducto(entidad.getImgProducto());
            dto.setTipoProducto(entidad.getTipo());
            dto.setArtista(entidad.getArtista());
            dto.setGenero(entidad.getGenero());
            dto.setSetlist(entidad.getSetlist());
            dto.setPrecio(entidad.getPrecio());
            dto.setStock(entidad.getStock());
            dto.setEstado(entidad.getEstado());

            return dto;

        } catch (PersistenciaException e) {
            throw new NegocioException("No pudimos recuperar la información del producto: " + e.getMessage());
        }
    }
}
