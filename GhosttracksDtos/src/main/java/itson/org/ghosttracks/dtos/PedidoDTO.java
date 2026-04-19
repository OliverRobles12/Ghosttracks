package itson.org.ghosttracks.dtos;

import java.util.List;

/**
 *
 * @author emyla
 */
public class PedidoDTO {
    
    private Long idCliente;
    private List<ItemCarritoDTO> productos;
    private Double subtotal;
    private Double costoEnvio;
    private Double total;
    private DireccionEntregaDTO direccionEntrega;
    private DatosPagoDTO datosPago;

    public PedidoDTO() {
    }
    
    public void calcularTotales(Double tarifaEnvio) {
        this.subtotal = 0.0;
        if (productos != null) {
            for (ItemCarritoDTO item : productos) {
                item.calcularSubtotal();
                this.subtotal += item.getSubtotal();
            }
        }
        this.costoEnvio = tarifaEnvio;
        this.total = this.subtotal + this.costoEnvio;
    }

    public PedidoDTO(
            Long idCliente, 
            List<ItemCarritoDTO> productos, 
            Double subtotal, 
            Double costoEnvio, 
            Double total, 
            DireccionEntregaDTO direccionEntrega, 
            DatosPagoDTO datosPago
    ) {
        this.idCliente = idCliente;
        this.productos = productos;
        this.subtotal = subtotal;
        this.costoEnvio = costoEnvio;
        this.total = total;
        this.direccionEntrega = direccionEntrega;
        this.datosPago = datosPago;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public DireccionEntregaDTO getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(DireccionEntregaDTO direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public DatosPagoDTO getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(DatosPagoDTO datosPago) {
        this.datosPago = datosPago;
    }
    
    
    
}
