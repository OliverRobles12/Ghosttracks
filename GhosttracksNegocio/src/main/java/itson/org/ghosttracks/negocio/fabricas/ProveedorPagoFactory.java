package itson.org.ghosttracks.negocio.fabricas;

import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.negocio.adaptador.ApplePayAdapter;
import itson.org.ghosttracks.negocio.adaptador.MercadoPagoAdapter;
import itson.org.ghosttracks.negocio.adaptador.StripeAdapter;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;

public class ProveedorPagoFactory {

    public static IProveedorPago obtenerEstrategia(TipoPago tipo) {
        switch (tipo) {
            case TARJETA:
                return new StripeAdapter();
            case MERCADO_PAGO:
                return new MercadoPagoAdapter();
            case APPLE_PAY:
                return new ApplePayAdapter();
            default:
                throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
    }
}