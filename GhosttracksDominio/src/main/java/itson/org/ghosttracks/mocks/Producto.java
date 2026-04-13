/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.org.ghosttracks.mocks;

import java.util.List;

/**
 *
 * @author nafbr
 */
public class Producto {
    private Long id;
    private String nombre;
    private String urlImagen;
    private TipoProducto tipo;
    private String artista;
    private String genero;
    private Double precio;
    private List<String> setList;
    private Integer stock;
    private String estado;

    public Producto(Long id, String nombre, String urlImagen, TipoProducto tipo, String artista, String genero, Double precio, List<String> setList, Integer stock, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
        this.artista = artista;
        this.genero = genero;
        this.precio = precio;
        this.setList = setList;
        this.stock = stock;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<String> getSetList() {
        return setList;
    }

    public void setSetList(List<String> setList) {
        this.setList = setList;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
