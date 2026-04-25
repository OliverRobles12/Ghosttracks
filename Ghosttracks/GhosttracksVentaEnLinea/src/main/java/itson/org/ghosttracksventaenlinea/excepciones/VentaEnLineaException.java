/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracksventaenlinea.excepciones;

/**
 *
 * @author nafbr
 */
public class VentaEnLineaException extends Exception {

    private final CodigoErrorVenta codigoError;

    public VentaEnLineaException(CodigoErrorVenta codigo, String mensaje) {
        super(mensaje);
        this.codigoError = codigo;
    }
    
    public VentaEnLineaException(CodigoErrorVenta codigo, String mensaje, Throwable causa){
        super(mensaje, causa);
        this.codigoError = codigo;
    }

    public CodigoErrorVenta getCodigoError() {
        return codigoError;
    }
    
    
}
