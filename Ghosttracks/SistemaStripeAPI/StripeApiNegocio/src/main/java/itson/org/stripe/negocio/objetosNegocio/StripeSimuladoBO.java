package itson.org.stripe.negocio.objetosNegocio;

import itson.org.stripe.negocio.interfaces.IStripeAPI;
import itson.org.stripeapi.dtos.RespuestaPagoDTO;
import itson.org.stripeapi.dtos.SolicitudPagoDTO;
import itson.org.stripeapi.enums.EstadoPago;
import java.time.LocalDateTime;
import java.util.UUID; // Este es Univerally Unique Identifier. Nos sirve para generar una cadena de texto random de 128 bits (realismo señores)
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Objeto de Negocio que simula el comportamiento de la pasarela de pagos Stripe
 * Implementa reglas de validación para aprobar o rechazar transacciones basadas 
 * en datos de entrada específicos
 * * @author Emy
 */
public class StripeSimuladoBO implements IStripeAPI{

    private static final Logger LOGGER = Logger.getLogger(StripeSimuladoBO.class.getName());
    
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
        
        String idTransaccion = "ch_" + UUID.randomUUID().toString().substring(0, 8);
        
        try {
            if (solicitud == null) {
                LOGGER.log(Level.SEVERE, "La solicitud de pago recibida es NULL.");
                return new RespuestaPagoDTO(EstadoPago.RECHAZADO, idTransaccion, "Solicitud inválida.", LocalDateTime.now());
            }

            if (solicitud.getFechaExpiracion() != null && 
                solicitud.getFechaExpiracion().isBefore(java.time.LocalDate.now())) {
                LOGGER.log(Level.WARNING, "Transacción {0} RECHAZADA: Tarjeta expirada ({1})", 
                        new Object[]{idTransaccion, solicitud.getFechaExpiracion()});
                return new RespuestaPagoDTO(EstadoPago.RECHAZADO, idTransaccion, "La tarjeta ha expirado.", LocalDateTime.now());
            }

            if (solicitud.getNumeroTarjeta() != null && solicitud.getNumeroTarjeta().endsWith("00")) {
                LOGGER.log(Level.WARNING, "Transacción {0} RECHAZADA: Fondos insuficientes (Regla terminación '00')", idTransaccion);
                return new RespuestaPagoDTO(EstadoPago.RECHAZADO, idTransaccion, "Fondos insuficientes en la cuenta.", LocalDateTime.now());
            }

            if ("000".equals(solicitud.getCvv())) {
                LOGGER.log(Level.WARNING, "Transacción {0} RECHAZADA: CVV incorrecto (Regla '000')", idTransaccion);
                return new RespuestaPagoDTO(EstadoPago.RECHAZADO, idTransaccion, "CVV incorrecto.", LocalDateTime.now());
            }

            //Happy Path
            LOGGER.log(Level.INFO, "Transacción {0} APROBADA exitosamente para el titular: {1}", 
                    new Object[]{idTransaccion, solicitud.getTitular()});
            
            return new RespuestaPagoDTO(EstadoPago.APROBADO, idTransaccion, "Pago procesado exitosamente.", LocalDateTime.now());

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error inesperado al procesar el pago en el simulador. ID: " + idTransaccion, e);
            return new RespuestaPagoDTO(EstadoPago.RECHAZADO, idTransaccion, "Error interno en la pasarela de pagos.", LocalDateTime.now());
        }
    }
    
}
