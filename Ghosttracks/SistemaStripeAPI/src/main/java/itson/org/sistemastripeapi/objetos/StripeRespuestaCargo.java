/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemastripeapi.objetos;

/**
 *
 * @author emyla
 */
public class StripeRespuestaCargo {
    
    private String idTransaccion;
    private String estado;
    private String metodoUtilizado;

    public StripeRespuestaCargo(String idTransaccion, String estado, String metodoUtilizado) {
        this.idTransaccion = idTransaccion;
        this.estado = estado;
        this.metodoUtilizado = metodoUtilizado;
    }

    public String getEstado() {
        return estado;
    }

    public String getMetodoUtilizado() {
        return metodoUtilizado;
    }
    
}
