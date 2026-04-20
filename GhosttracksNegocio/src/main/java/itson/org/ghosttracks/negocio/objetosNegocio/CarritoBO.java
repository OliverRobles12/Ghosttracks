/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.dtos.CarritoDTO;
import itson.org.ghosttracks.dtos.ItemCarritoDTO;
import itson.org.ghosttracks.dtos.ProductoDTO;
import itson.org.ghosttracks.negocio.interfaces.ICarritoBO;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;


/**
 *
 * @author nafbr
 */
public class CarritoBO implements ICarritoBO{
   @Override
    public CarritoDTO agregarProducto(CarritoDTO carrito, ProductoDTO producto, Integer cantidad) throws NegocioException {
        
        if (carrito == null) {
            carrito = new CarritoDTO();
        }
        if (producto == null || cantidad == null || cantidad <= 0) {
            throw new NegocioException("Producto o cantidad inválidos al intentar agregar al carrito.");
        }
        if (producto.getStock() == null || cantidad > producto.getStock()) {
            throw new NegocioException("No hay suficiente stock para el producto: " + producto.getNombre());
        }

        boolean existe = false;
        for (ItemCarritoDTO item : carrito.getProductos()) {
            if (item.getProductoSeleccionado().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + cantidad);
                existe = true;
                break;
            }
        }

        if (!existe) {
            ItemCarritoDTO nuevoItem = new ItemCarritoDTO();
            nuevoItem.setProductoSeleccionado(producto);
            nuevoItem.setCantidad(cantidad);
            
            carrito.getProductos().add(nuevoItem);
        }

        carrito.calcularTotalGeneral();

        return carrito;
    }

    @Override
    public CarritoDTO eliminarProducto(CarritoDTO carrito, Long idProducto) throws NegocioException {
        if (carrito == null || carrito.getProductos().isEmpty()) {
            throw new NegocioException("No hay productos en el carrito para eliminar.");
        }

        carrito.getProductos().removeIf(item -> 
            item.getProductoSeleccionado().getIdProducto().equals(idProducto)
        );

        carrito.calcularTotalGeneral();

        return carrito;
    }
}
