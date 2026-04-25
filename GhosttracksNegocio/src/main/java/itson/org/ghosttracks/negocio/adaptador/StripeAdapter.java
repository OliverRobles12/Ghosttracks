/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.stripe.negocio.interfaces.IStripeAPI;
import itson.org.stripe.negocio.objetosNegocio.StripeSimuladoBO;
import itson.org.stripeapi.dtos.RespuestaPagoDTO;
import itson.org.stripeapi.dtos.SolicitudPagoDTO;
import itson.org.stripeapi.enums.EstadoPago;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Adaptador que conecta nuestro sistema Ghosttracks con la API externa de Stripe.
 * @author emyla
 */
public class StripeAdapter implements IProveedorPago {

    private final IStripeAPI stripeAPI;

    public StripeAdapter() {
        // Instanciamos nuestra API Simulada
        this.stripeAPI = new StripeSimuladoBO();
    }

    @Override
    public boolean cobrar(Double monto, String titular, String numeroTarjeta, String cvv, String fechaExpiracion) {
        
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
            return false; 
        }
        
        RespuestaPagoDTO respuesta = stripeAPI.procesarPago(solicitud);

        if (respuesta.getEstado() == EstadoPago.APROBADO) {
            return true;
        } else {
            return false;
        }
    }
}
