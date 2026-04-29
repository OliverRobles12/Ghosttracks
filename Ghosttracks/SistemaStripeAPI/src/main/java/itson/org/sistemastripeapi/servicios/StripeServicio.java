/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemastripeapi.servicios;

import itson.org.sistemastripeapi.objetos.StripeRespuestaCargo;
import itson.org.sistemastripeapi.objetos.StripeSolicitudCargo;
import itson.org.sistemastripeapi.core.StripeCliente;

/**
 *
 * @author emyla
 */
public class StripeServicio {
    
    public StripeRespuestaCargo procesarCargo(StripeSolicitudCargo solicitud){
        
        // Validamos q la API esté configurada
        if (StripeCliente.getInstancia().getApiKey() == null){
            throw new RuntimeException("Error! No autorizado. Stripe API no configurada");
        }
        
        System.out.println("Recibiendo petición para cobro...");
        System.out.println("Validando tarjeta: " + solicitud.getTarjeta());
        
        String idSimulado = "stipe_" + System.currentTimeMillis();
        String estadoSimulado = "Éxito";
        
        return new StripeRespuestaCargo(idSimulado, estadoSimulado, solicitud.getTipoMetodo());
        
    }
}
