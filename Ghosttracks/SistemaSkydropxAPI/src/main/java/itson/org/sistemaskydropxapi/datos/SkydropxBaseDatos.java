/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemaskydropxapi.datos;

import itson.org.sistemaskydropxapi.objetos.SkydropxPaquete;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author emyla
 */
public class SkydropxBaseDatos {
    
    private Map<String, SkydropxPaquete> paquetes;

    public SkydropxBaseDatos() {
        this.paquetes = new HashMap<>();
    }

    public void guardar(SkydropxPaquete paquete) {
        paquetes.put(paquete.getNumeroGuia(), paquete);
    }

    public SkydropxPaquete buscar(String numeroGuia) {
        return paquetes.get(numeroGuia);
    }
}
