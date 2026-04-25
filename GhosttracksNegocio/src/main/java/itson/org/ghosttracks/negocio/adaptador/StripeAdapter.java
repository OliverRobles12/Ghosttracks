/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.stripe.negocio.interfaces.IStripeAPI;
import itson.org.stripe.negocio.objetosNegocio.StripeSimuladoBO;
import itson.org.stripeapi.dtos.RespuestaPagoDTO;
import itson.org.stripeapi.dtos.SolicitudPagoDTO;
import itson.org.stripeapi.enums.EstadoPago;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adaptador que conecta nuestro sistema Ghosttracks con la API externa de Stripe.
 * @author emyla
 */
public class StripeAdapter implements IProveedorPago {

    private final IStripeAPI stripeAPI;
    private static final Logger LOGGER = Logger.getLogger(StripeAdapter.class.getName());

    public StripeAdapter() {
        // Instanciamos nuestra API Simulada
        this.stripeAPI = new StripeSimuladoBO();
    }

    @Override
    public boolean cobrar(Double monto, String titular, String numeroTarjeta, String cvv, String fechaExpiracion) throws NegocioException {
        
        SolicitudPagoDTO solicitud = new SolicitudPagoDTO();
        solicitud.setMonto(monto);
        solicitud.setTitular(titular);
        solicitud.setNumeroTarjeta(numeroTarjeta);
        solicitud.setCvv(cvv);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth yearMonth = YearMonth.parse(fechaExpiracion, formatter);
            solicitud.setFechaExpiracion(yearMonth.atEndOfMonth());
        } catch (DateTimeParseException e) {
            LOGGER.log(Level.SEVERE, "Error al parsear la fecha de expiración: {0}", fechaExpiracion);
            return false; 
        }
        
        try {
            
            RespuestaPagoDTO respuesta = stripeAPI.procesarPago(solicitud);

            if (respuesta.getEstado() == EstadoPago.APROBADO) {
                LOGGER.log(Level.INFO, "Pago aprobado exitosamente. ID Transacción: {0}", respuesta.getIdTransaccion());
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Pago rechazado por Stripe. Motivo: {0}", respuesta.getMensaje());
                throw new NegocioException(respuesta.getMensaje()); 
            }
            
        }catch (NegocioException e) {
            throw e;
                
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ocurrió un error inesperado en la comunicación con Stripe", e);
            throw new NegocioException("Error de conexión con el banco.");
        }
    }
}

