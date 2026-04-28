
package itson.org.ghosttracks.dtos;

import itson.org.ghosttracks.enums.EstadoPedidoDTO;

/**
 *
 * @author oliro
 */
public class NuevoPedidoDTO {

    private ClienteDTO cliente;
    private ContactoDTO contacto;
    private DireccionEntregaDTO direccionEntrega;
    private DatosPagoDTO datosPago;
    private CarritoDTO carrito;
    private EstadoPedidoDTO estado = EstadoPedidoDTO.PENDIENTE;

    public NuevoPedidoDTO(PedidoDTOBuilder builder) {
        this.cliente = builder.getCliente();
        this.contacto = builder.getContacto();
        this.direccionEntrega = builder.getDireccionEntrega();
        this.datosPago = builder.getDatosPago();
        this.carrito = builder.getCarrito();
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public ContactoDTO getContacto() {
        return contacto;
    }

    public DireccionEntregaDTO getDireccionEntrega() {
        return direccionEntrega;
    }

    public DatosPagoDTO getDatosPago() {
        return datosPago;
    }

    public CarritoDTO getCarrito() {
        return carrito;
    }

    public EstadoPedidoDTO getEstado() {
        return estado;
    }
    
}
