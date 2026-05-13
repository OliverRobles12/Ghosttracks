/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.RazonSalida;

/**
 *
 * @author nafbr
 */
public class ProductoOrden {
    private Integer cantidadProducto;
    private Double importeTotal;
    private Double precioUnitario;
    private Boolean recibido;
    private RazonSalida razon;
    
    //Relaciones
    private Long idOrden;
    private Long idProducto;
}
