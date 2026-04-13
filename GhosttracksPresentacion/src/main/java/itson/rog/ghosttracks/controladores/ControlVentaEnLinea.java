
package itson.rog.ghosttracks.controladores;

import itson.org.ghosttracks.mocks.Producto;
import itson.org.ghosttracks.mocks.TipoProducto;
import itson.org.ghosttracks.presentacion.cliente.PantallaInicioCliente;
import itson.org.ghosttracks.presentacion.cliente.VistaProducto;
import itson.org.ghosttracks.presentacion.cliente.metodosDePago.PantallaMetodoPagoTarjetaDeb;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author oliro
 */
public class ControlVentaEnLinea {
    
    public ControlVentaEnLinea() {
        
    }
    private List<Producto> obtenerProductosMock() {
        List<Producto> mocks = new ArrayList<>();
        mocks.add(new Producto(
                1L,
                "Abbey Road (Edición Aniversario)",
                "https://upload.wikimedia.org/wikipedia/commons/a/a4/The_Beatles_Abbey_Road_album_cover.jpg", 
                TipoProducto.VINILO,
                "The Beatles",
                "Rock", 
                1200.0,
                Arrays.asList("Come Together", "Something", "Maxwell's Silver Hammer", "Oh! Darling"),
                50,
                "Nuevo"
        ));

        mocks.add(new Producto(
                2L,
                "Thriller",
                "https://i.scdn.co/image/ab67616d00001e024121faee8df82c526cbab2be", 
                TipoProducto.VINILO,
                "Michael Jackson",
                "Pop", 
                1100.0,
                Arrays.asList("Wanna Be Startin' Somethin'", "Baby Be Mine", "The Girl Is Mine", "Thriller"),
                30,
                "Sellado"
        ));

        mocks.add(new Producto(
                3L,
                "Kind of Blue",
                "https://upload.wikimedia.org/wikipedia/en/1/10/Miles_Davis_-_Kind_of_Blue_album_cover.jpg", 
                TipoProducto.VINILO,
                "Miles Davis",
                "Jazz", 
                1350.0,
                Arrays.asList("So What", "Freddie Freeloader", "Blue in Green"),
                25,
                "Edición Especial"
        ));

        mocks.add(new Producto(
                4L,
                "The Album",
                "https://resources.sanborns.com.mx/imagenes-sanborns-ii/1200/602435042589.jpg", 
                TipoProducto.CD,
                "BLACKPINK",
                "K-pop",
                650.0,
                Arrays.asList("How You Like That", "Ice Cream", "Pretty Savage", "Bet You Wanna"),
                75,
                "Disponible"
        ));

        mocks.add(new Producto(
                5L,
                "Me Volví a Acordar de Ti",
                "https://m.media-amazon.com/images/I/51uHfSacaJL._UF1000,1000_QL80_.jpg",
                TipoProducto.CD,
                "Marco Antonio Solís",
                "Regional Mexicana", 
                450.0,
                Arrays.asList("Me Volví a Acordar de Ti", "Donde Estará Mi Primavera", "Si No Te Hubieras Ido"),
                40,
                "Disponible"
        ));

        mocks.add(new Producto(
                6L,
                "Un Verano Sin Ti",
                "https://m.media-amazon.com/images/I/91vkwlCj4mL._UF1000,1000_QL80_.jpg", 
                TipoProducto.CD,
                "Bad Bunny",
                "Urbana", 
                700.0,
                Arrays.asList("Moscow Mule", "Después de la Playa", "Me Porto Bonito", "Tití Me Preguntó"),
                100,
                "Disponible"
        ));

        mocks.add(new Producto(
                7L,
                "Currents",
                "https://m.media-amazon.com/images/I/91dTLHSQdkL._UF1000,1000_QL80_.jpg", 
                TipoProducto.CASSETTE,
                "Tame Impala",
                "Alternativo", 
                350.0,
                Arrays.asList("Let It Happen", "Nungs", "The Moment", "The Less I Know the Better"),
                15,
                "Disponible"
        ));

        
        mocks.add(new Producto(
                8L,
                "DAMN.",
                "https://m.media-amazon.com/images/I/71tdaojeUkL._UF1000,1000_QL80_.jpg", 
                TipoProducto.CASSETTE,
                "Kendrick Lamar",
                "Hip hop", 
                300.0,
                Arrays.asList("BLOOD.", "DNA.", "YAH.", "ELEMENT."),
                20,
                "Disponible"
        ));

        mocks.add(new Producto(
                9L,
                "Ojalá que llueva café",
                "https://i.scdn.co/image/ab67616d0000b273ec8c7f7bd6b215915d15ec0c", 
                TipoProducto.CASSETTE,
                "Juan Luis Guerra",
                "Tropical", 
                250.0,
                Arrays.asList("Visa para un sueño", "Ojalá que llueva café", "La bilirrubina", "Woman del Callao"),
                10,
                "Disponible"
        ));return mocks;
    }
    
    public void mostrarPantallaInicio(JFrame pantallaActual) {
        PantallaInicioCliente vistaPantalla = new PantallaInicioCliente();
        vistaPantalla.setLocationRelativeTo(vistaPantalla);
        vistaPantalla.setVisible(true);
        
        if (pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
    public void mostrarPantallaVistaProducto(JFrame pantallaActual) {
        VistaProducto vistaProducto = new VistaProducto(this);
        vistaProducto.setVisible(true);
        
        if(pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
    public void mostrarPantallaMetodoPagoTarjeta(JFrame pantallaActual) {
        PantallaMetodoPagoTarjetaDeb vistaTarjeta = new PantallaMetodoPagoTarjetaDeb(); 
        vistaTarjeta.setLocationRelativeTo(vistaTarjeta);
        vistaTarjeta.setVisible(true);
        
        if(pantallaActual != null) {
            pantallaActual.dispose();
        }
    }
    
    public void procesarSeleccionMetodoPago(JFrame pantallaActual, String metodoSeleccionado) {
        
        switch (metodoSeleccionado) {
            case "TARJETA":
                mostrarPantallaMetodoPagoTarjeta(pantallaActual);
                break;
                
            case "APPLE_PAY":
                System.out.println("Navegando a Apple Pay...");
                break;
                
            case "MERCADO_PAGO":
                System.out.println("Navegando a Mercado Pago...");
                break;
                
            default:
                JOptionPane.showMessageDialog(pantallaActual, 
                    "Por favor, seleccione un método de pago antes de continuar.", 
                    "Método de pago requerido", 
                JOptionPane.WARNING_MESSAGE);
                break;
        }
    }
}
