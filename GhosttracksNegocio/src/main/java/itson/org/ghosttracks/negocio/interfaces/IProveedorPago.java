/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.negocio.interfaces;

import itson.org.ghosttracks.negocio.objetosNegocio.Excepciones.NegocioException;

/**
 *
 * @author emyla
 */
public interface IProveedorPago {
    /**
     * Intenta realizar el cobro a una tarjeta.
     * @param monto El total a cobrar.
     * @param titular Nombre del dueño de la tarjeta.
     * @param numeroTarjeta Los 16 dígitos
     * @param cvv Código de seguridad.
     * @param fechaExpiracion Fecha en formato texto 
     * @return true si el banco aprobó el pago, false si lo rechazó
     */
    public boolean cobrar(Double monto, String titular, String numeroTarjeta, String cvv, String fechaExpiracion) throws NegocioException;
}
