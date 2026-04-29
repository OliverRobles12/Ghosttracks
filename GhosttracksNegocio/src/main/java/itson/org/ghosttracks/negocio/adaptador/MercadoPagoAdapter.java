package itson.org.ghosttracks.negocio.adaptador;

import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;
import java.time.LocalDateTime;

public class MercadoPagoAdapter implements IProveedorPago {

    @Override
    public boolean cobrar(String tarjeta, Double monto, TipoPago tipo) throws NegocioException {
        System.out.println("Procesando cobro de $" + monto + " a través de " + tipo.name() + " con tarjeta: " + tarjeta);
        return true; 
    }
}