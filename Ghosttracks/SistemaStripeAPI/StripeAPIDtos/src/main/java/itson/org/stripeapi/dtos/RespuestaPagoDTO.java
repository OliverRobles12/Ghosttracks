package itson.org.stripeapi.dtos;

import itson.org.stripeapi.enums.EstadoPago;
import java.time.LocalDateTime;

/**
 * DTO que contiene la información de respuesta tras procesar un pago
 * * @author Emy
 */
public class RespuestaPagoDTO {
    
    private EstadoPago estado;
    private String idTransaccion;
    private String mensaje;
    private LocalDateTime fechaProcesamiento;

    /**
     * Constructor por defecto.
     */
    public RespuestaPagoDTO() {
        
    }

    /**
     * Constructor que inicializa todos los campos de la respuesta.
     * @param estado Estado final del pago
     * @param idTransaccion Folio único de la operación
     * @param mensaje Descripción detallada del resultado
     * @param fechaProcesamiento Fecha y hora en la que se realizó la operación
     */
    public RespuestaPagoDTO(EstadoPago estado, String idTransaccion, String mensaje, LocalDateTime fechaProcesamiento) {
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.mensaje = mensaje;
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
    * Obtiene el estado actual del pago procesado.
    * 
    * @return estado del pago
    */
   public EstadoPago getEstado() {
       return estado;
   }

   /**
    * Establece el estado actual del pago procesado.
    * 
    * @param estado estado del pago
    */
   public void setEstado(EstadoPago estado) {
       this.estado = estado;
   }

   /**
    * Obtiene el identificador único de la transacción.
    * 
    * @return id de la transacción
    */
   public String getIdTransaccion() {
       return idTransaccion;
   }

   /**
    * Establece el identificador único de la transacción.
    * 
    * @param idTransaccion id de la transacción
    */
   public void setIdTransaccion(String idTransaccion) {
       this.idTransaccion = idTransaccion;
   }

   /**
    * Obtiene el mensaje asociado al resultado del pago.
    * 
    * @return mensaje del procesamiento
    */
   public String getMensaje() {
       return mensaje;
   }

   /**
    * Establece el mensaje asociado al resultado del pago.
    * 
    * @param mensaje mensaje del procesamiento
    */
   public void setMensaje(String mensaje) {
       this.mensaje = mensaje;
   }

   /**
    * Obtiene la fecha y hora en que se procesó el pago.
    * 
    * @return fecha de procesamiento
    */
   public LocalDateTime getFechaProcesamiento() {
       return fechaProcesamiento;
   }

   /**
    * Establece la fecha y hora en que se procesó el pago.
    * 
    * @param fechaProcesamiento fecha de procesamiento
    */
   public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) {
       this.fechaProcesamiento = fechaProcesamiento;
   }
   
}
