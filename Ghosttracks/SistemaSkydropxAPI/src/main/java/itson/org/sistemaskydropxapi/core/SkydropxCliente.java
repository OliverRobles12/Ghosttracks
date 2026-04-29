/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.sistemaskydropxapi.core;

import itson.org.sistemaskydropxapi.datos.SkydropxBaseDatos;

/**
 *
 * @author emyla
 */
public class SkydropxCliente {
    
    private static SkydropxCliente instancia;
    private SkydropxBaseDatos baseDatos;

    // Constructor privado
    private SkydropxCliente() {
        // Al nacer el cliente, nace la única base de datos
        this.baseDatos = new SkydropxBaseDatos();
    }

    public static SkydropxCliente getInstancia() {
        if (instancia == null) {
            instancia = new SkydropxCliente();
        }
        return instancia;
    }

    // Método para que el Servicio pueda acceder a la base de datos
    public SkydropxBaseDatos getBaseDatos() {
        return baseDatos;
    }
    
}
