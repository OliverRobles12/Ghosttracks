package itson.org.sistemastripeapi.objetos;

/**
 *
 * @author emyla
 */
public class StripeSolicitudCargo {
    
    private String tarjeta;
    private Double monto;
    private String tipoMetodo;

    public StripeSolicitudCargo(String tarjeta, Double monto, String tipoMetodo) {
        this.tarjeta = tarjeta;
        this.monto = monto;
        this.tipoMetodo = tipoMetodo;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public String getTipoMetodo() {
        return tipoMetodo;
    }
       
}
