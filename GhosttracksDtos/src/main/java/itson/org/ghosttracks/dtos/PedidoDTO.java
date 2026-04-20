package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;
import java.util.List;

/**
 *
 * @author emyla
 */
public class PedidoDTO {
    
    private Long idPedido;
    private Long idCliente;
    private ContactoDTO contacto;
    private List<ItemCarritoDTO> productos;
    private Double subtotal;
    private Double costoEnvio;
    private Double total;
    private EstadoPedidoDTO estado;
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

    public PedidoDTO(Long idPedido, Long idCliente, EstadoPedidoDTO estado, Double total) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.estado = estado;
        this.total = total;
    }

    public PedidoDTO(
            Long idPedido, 
            Long idCliente, 
            ContactoDTO contacto, 
            List<ItemCarritoDTO> productos, 
            Double subtotal, 
            Double costoEnvio, 
            Double total, 
            EstadoPedidoDTO estado, 
            DireccionEntregaDTO direccionEntrega, 
            DatosPagoDTO datosPago
    ) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.contacto = contacto;
        this.productos = productos;
        this.subtotal = subtotal;
        this.costoEnvio = costoEnvio;
        this.total = total;
        this.estado = estado;
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

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public EstadoPedidoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedidoDTO estado) {
        this.estado = estado;
    }

    public ContactoDTO getContacto() {
        return contacto;
    }

    public void setContacto(ContactoDTO contacto) {
        this.contacto = contacto;
    }

}
