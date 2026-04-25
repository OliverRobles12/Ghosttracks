package itson.org.stripe.negocio.interfaces;

import itson.org.stripeapi.dtos.RespuestaPagoDTO;
import itson.org.stripeapi.dtos.SolicitudPagoDTO;

/**
 * Interfaz de Stripe que define las operaciones principales para el procesamiento de pagos
 * mediante la integración con Stripe API.
 * Esta interfaz permite recibir una solicitud de pago y devolver
 * una respuesta con el resultado de la transacción realizada.
 * @author emyla
 */
public interface IStripeAPI {

    /**
     * Procesa una solicitud de pago de manera síncrona.
     * @param solicitud Datos del pago y método seleccionado.
     * @return Respuesta con el estado final de la transacción.
     */
    public RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud);
    
}