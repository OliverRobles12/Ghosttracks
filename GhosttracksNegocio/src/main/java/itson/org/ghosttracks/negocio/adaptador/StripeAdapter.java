package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import itson.org.sistemastripeapi.core.StripeCliente;
import itson.org.sistemastripeapi.objetos.StripeRespuestaCargo;
import itson.org.sistemastripeapi.objetos.StripeSolicitudCargo;
import itson.org.sistemastripeapi.servicios.StripeServicio;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adaptador que conecta nuestro sistema Ghosttracks con la API externa de Stripe.
 * @author emyla
 */
public class StripeAdapter implements IProveedorPago {

    private final StripeServicio stripeServicio;
    private static final Logger LOGGER = Logger.getLogger(StripeAdapter.class.getName());

    public StripeAdapter() {
        StripeCliente.getInstancia().inicializar("sk_test_ghosttracks12345");
        this.stripeServicio = new StripeServicio();
    }

    @Override
    public boolean cobrar(String tarjeta, Double monto, TipoPago tipo) throws NegocioException {
        System.out.println("Procesando cobro de $" + monto + " a través de " + tipo.name() + " con tarjeta: " + tarjeta);
        return true; 
    }
}