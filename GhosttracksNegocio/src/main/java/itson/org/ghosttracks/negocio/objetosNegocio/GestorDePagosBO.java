package itson.org.ghosttracks.negocio.objetosNegocio;

import itson.org.ghosttracks.enums.TipoPago;
import itson.org.ghosttracks.negocio.fabricas.ProveedorPagoFactory;
import itson.org.ghosttracks.negocio.interfaces.IProveedorPago;
import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

public class GestorDePagosBO {

    // Ya solo recibimos exactamente lo que pediste
    public boolean procesarCompra(String tarjeta, Double monto, TipoPago metodoSeleccionado) {
        try {
            // La fábrica jala la estrategia
            IProveedorPago estrategiaDePago = ProveedorPagoFactory.obtenerEstrategia(metodoSeleccionado);
            
            return estrategiaDePago.cobrar(tarjeta, monto, metodoSeleccionado);
            
        } catch (NegocioException ex) {
            System.err.println("Error al cobrar: " + ex.getMessage());
            return false;
        }
    }
}