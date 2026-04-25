package itson.org.stripe.negocio.objetosNegocio;

import itson.org.stripe.negocio.interfaces.IStripeAPI;
import itson.org.stripeapi.dtos.RespuestaPagoDTO;
import itson.org.stripeapi.dtos.SolicitudPagoDTO;
import itson.org.stripeapi.enums.EstadoPago;
import java.time.LocalDateTime;
import java.util.UUID; // Este es Univerally Unique Identifier
// Nos sirve para generar una cadena de texto random de 128 bits (realismo señores)

/**
 * Objeto de Negocio que simula el comportamiento de la pasarela de pagos Stripe
 * Implementa reglas de validación para aprobar o rechazar transacciones basadas 
 * en datos de entrada específicos
 * * @author Emy
 */
public class StripeSimuladoBO implements IStripeAPI{

    /**
     * Procesa una solicitud de pago simulando una comunicación con un banco externo.
     * * Reglas de rechazo simuladas:
     * - Tarjeta vencida: Si la fecha es anterior a la actual
     * - Fondos insuficientes: Si el número de tarjeta termina en "00"
     * - CVV Incorrecto: Si el CVV enviado es "000"
     * * @param solicitud Objeto con los datos del pago y del cliente.
     * @return RespuestaPagoDTO indicando si el pago fue aprobado o rechazado.
     */
    @Override
    public RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) {
        
        String idTransaccion = "ch_" + UUID.randomUUID().toString().substring(0, 8); //Primero se genera el idTransacción
        
        // Validación de la fecha de expiración
        if (solicitud.getFechaExpiracion() != null && // Primero validamos que SI haya una fecha
            solicitud.getFechaExpiracion().isBefore(java.time.LocalDate.now())) { // Y luego que la fecha sea anterior a la actual
            // Si se cumple, entonces la tarjeta está vencida
            return new RespuestaPagoDTO(
                EstadoPago.RECHAZADO, 
                idTransaccion, 
                "La tarjeta ha expirado.", 
                LocalDateTime.now()
            );
        }

        // Fondos insuficientes (si la tarjeta termina con 0)
        if (solicitud.getNumeroTarjeta() != null && solicitud.getNumeroTarjeta().endsWith("00")) {
            return new RespuestaPagoDTO(
                EstadoPago.RECHAZADO, 
                idTransaccion, 
                "Fondos insuficientes en la cuenta.", 
                LocalDateTime.now()
            );
        }

        // validar que CVV no sea incorrecto (Si el CVV es "000" entonces está mal)
        if ("000".equals(solicitud.getCvv())) {
            return new RespuestaPagoDTO(
                EstadoPago.RECHAZADO, 
                idTransaccion, 
                "CVV incorrecto.", 
                LocalDateTime.now()
            );
        }

        // Happy path, todo salió bien
        return new RespuestaPagoDTO(
            EstadoPago.APROBADO, 
            idTransaccion, 
            "Pago procesado exitosamente.", 
            LocalDateTime.now()
        );
    }
    
}
