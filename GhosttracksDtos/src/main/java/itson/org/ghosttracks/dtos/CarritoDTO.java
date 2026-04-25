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
    private Double subtotal = 0.0;
    private Double impuestos = 0.0;
    private static final double TASA_IMPUESTO = 0.16;
    
    public CarritoDTO() {
    }
    
    public void calcularTotalGeneral() {
        this.subtotal = 0.0;
        if (productos != null) {
            for (ItemCarritoDTO producto : productos) {
                producto.calcularSubtotal();
                this.subtotal += producto.getSubtotal();
            }
        }
        
        this.impuestos = this.subtotal * TASA_IMPUESTO;
        this.total = this.subtotal + this.impuestos;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }
    
}
