/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemastripeapi.core;

/**
 * SINGLETON
 * @author emyla
 */
public class StripeCliente {
    
    private static StripeCliente instancia;
    private String apiKey;

    private StripeCliente() {
        
    }
    
    public static StripeCliente getInstancia(){
        if(instancia == null){
            instancia = new StripeCliente();
        }
        return instancia;
    }
    
    public void inicializar(String apiKey){
        this.apiKey = apiKey;
    }
    
    public String getApiKey(){
        return apiKey;
    }
}
