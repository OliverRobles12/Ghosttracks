/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.org.stripeapi.dtos;

import itson.org.stripeapi.enums.TipoPago;
import java.time.LocalDate;

/**
 * DTO que encapsula los datos necesarios para realizar un intento de cobro.
 * * @author Emy
 */

public class SolicitudPagoDTO {
    
    private Long idPedido;
    private Double monto;
    private TipoPago tipoPago;
    private String titular;
    private String numeroTarjeta;
    private String cvv;
    private LocalDate fechaExpiracion;
    private String tokenPago;

    /**
     * Constructor por defecto.
     */
    public SolicitudPagoDTO() {
        
    }

    /**
     * Constructor completo para inicializar una solicitud de pago.
     * @param idPedido Identificador del pedido en el sistema origen
     * @param monto Cantidad total a cobrar
     * @param tipoPago Método seleccionado 
     * @param titular Nombre del dueño del método de pago
     * @param numeroTarjeta Dígitos de la tarjeta
     * @param cvv Código de seguridad
     * @param fechaExpiracion Mes y año de vencimiento
     * @param tokenPago Token digital para pagos móviles
     */
    public SolicitudPagoDTO(Long idPedido, Double monto, TipoPago tipoPago, String titular, String numeroTarjeta, String cvv, LocalDate fechaExpiracion, String tokenPago) {
        this.idPedido = idPedido;
        this.monto = monto;
        this.tipoPago = tipoPago;
        this.titular = titular;
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fechaExpiracion = fechaExpiracion;
        this.tokenPago = tokenPago;
    }

    /**
    * Obtiene el identificador del pedido asociado al pago.
    * @return id del pedido
    */
    public Long getIdPedido() {
        return idPedido;
    }

    /**
    * Establece el identificador del pedido asociado al pago.
    * 
    * @param idPedido id del pedido
    */
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    /**
    * Obtiene el monto total a pagar.
    * 
    * @return monto del pago
    */
   public Double getMonto() {
       return monto;
   }

   /**
    * Establece el monto total a pagar.
    * 
    * @param monto monto del pago
    */
   public void setMonto(Double monto) {
       this.monto = monto;
   }

   /**
    * Obtiene el tipo de pago seleccionado.
    * 
    * @return tipo de pago
    */
   public TipoPago getTipoPago() {
       return tipoPago;
   }

   /**
    * Establece el tipo de pago seleccionado.
    * 
    * @param tipoPago tipo de pago
    */
   public void setTipoPago(TipoPago tipoPago) {
       this.tipoPago = tipoPago;
   }

   /**
    * Obtiene el nombre del titular de la tarjeta.
    * 
    * @return nombre del titular
    */
   public String getTitular() {
       return titular;
   }

   /**
    * Establece el nombre del titular de la tarjeta.
    * 
    * @param titular nombre del titular
    */
   public void setTitular(String titular) {
       this.titular = titular;
   }

   /**
    * Obtiene el número de la tarjeta.
    * 
    * @return número de tarjeta
    */
   public String getNumeroTarjeta() {
       return numeroTarjeta;
   }

   /**
    * Establece el número de la tarjeta.
    * 
    * @param numeroTarjeta número de tarjeta
    */
   public void setNumeroTarjeta(String numeroTarjeta) {
       this.numeroTarjeta = numeroTarjeta;
   }

   /**
    * Obtiene el código de seguridad (CVV) de la tarjeta.
    * 
    * @return CVV de la tarjeta
    */
   public String getCvv() {
       return cvv;
   }

   /**
    * Establece el código de seguridad (CVV) de la tarjeta.
    * 
    * @param cvv código de seguridad
    */
   public void setCvv(String cvv) {
       this.cvv = cvv;
   }

   /**
    * Obtiene la fecha de expiración de la tarjeta.
    * 
    * @return fecha de expiración
    */
   public LocalDate getFechaExpiracion() {
       return fechaExpiracion;
   }

   /**
    * Establece la fecha de expiración de la tarjeta.
    * 
    * @param fechaExpiracion fecha de expiración
    */
   public void setFechaExpiracion(LocalDate fechaExpiracion) {
       this.fechaExpiracion = fechaExpiracion;
   }

   /**
    * Obtiene el token generado para procesar el pago.
    * 
    * @return token de pago
    */
   public String getTokenPago() {
       return tokenPago;
   }

   /**
    * Establece el token generado para procesar el pago.
    * 
    * @param tokenPago token de pago
    */
   public void setTokenPago(String tokenPago) {
       this.tokenPago = tokenPago;
   }
}