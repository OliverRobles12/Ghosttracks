package itson.org.ghosttracks.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emyla
 */
public class CarritoDTO {
    
    private Long idCliente;
    private List<ItemCarritoDTO> productos = new ArrayList<>();
    private Double total;

    public CarritoDTO() {
    }
    
    public void calcularTotalGeneral() {
        this.total = 0.0;
        for (ItemCarritoDTO producto : productos) {
            producto.calcularSubtotal();
            this.total += producto.getSubtotal();
        }
    }

    public CarritoDTO(Long idCliente, Double total) {
        this.idCliente = idCliente;
        this.total = total;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItemCarritoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCarritoDTO> productos) {
        this.productos = productos;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    } 
    
}
