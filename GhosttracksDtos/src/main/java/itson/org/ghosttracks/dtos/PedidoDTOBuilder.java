
package itson.org.ghosttracks.dtos;

/**
 *
 * @author oliro
 */
public class PedidoDTOBuilder {

    private ClienteDTO cliente;
    private ContactoDTO contacto;
    private DireccionEntregaDTO direccionEntrega;
    private DatosPagoDTO datosPago;
    private CarritoDTO carrito;
    
    public PedidoDTOBuilder setCliente(ClienteDTO cliente){
        this.cliente = cliente;
        return this;
    }
    
    public PedidoDTOBuilder setCarrito(CarritoDTO carrito) {
        this.carrito = carrito;
        return this;
    }
    
    public PedidoDTOBuilder setContacto(ContactoDTO contacto) {
        this.contacto = contacto;
        return this;
    }
    
    public PedidoDTOBuilder setDireccionEntrega(DireccionEntregaDTO direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
        return this;
    }
    
    public PedidoDTOBuilder setDatosPago(DatosPagoDTO datosPago) {
        this.datosPago = datosPago;
        return this;
    }
    
    public NuevoPedidoDTO build() {
        
        // TODO validaciones
        
        return new NuevoPedidoDTO(this);
            
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
    
}
