/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.entidades;

import itson.org.ghosttracks.enums.RazonSalida;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author nafbr
 */
public class Salida {
    private Long idSalida;
    private LocalDate fechaSalida;
    private String comenatriosSalida;
    private RazonSalida razon;
    
    //Relaciones
    private Long idSucursal;
    private List<ProductoSalida> productosSalida;
}
