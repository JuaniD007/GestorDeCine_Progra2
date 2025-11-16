package Models;
import Contenedoras.GestorUsuario;
import Contenedoras.GestorDeCatalogo;
import Contenedoras.GestorDeVentas;
import Excepciones.*;
import Enum.*;
import UI.Menu;

/**
 * Clase principal que "posee" y "conecta" todos los sistemas del cine.
 * Su única responsabilidad es inicializar los gestores y el menú.
 */
public class Cine {

    // 1. Los 3 "Cerebros" (Gestores)
    private GestorUsuario gestorUsuario;
    private GestorDeCatalogo gestorDeCatalogo; /// es el que gestiona la cartelera
    private GestorDeVentas gestorDeVentas;


    // 2. La "Interfaz" (Consola)
    private Menu menu;

    private String nombreCine;

    /**
     * El constructor del Cine crea e inicializa todos los sistemas.
     */
    public Cine(String nombreCine) {
        this.nombreCine = nombreCine;

        // 3. Inicia los cerebros (Esto carga todos los JSON en memoria)
        this.gestorUsuario = new GestorUsuario();
        this.gestorDeCatalogo = new GestorDeCatalogo();

        // 4. El GestorDeVentas necesita al GestorDeCatalogo
        //    para poder buscar funciones, validar asientos, etc.
        this.gestorDeVentas = new GestorDeVentas(this.gestorDeCatalogo);

        this.menu = new Menu(gestorUsuario, gestorDeCatalogo, gestorDeVentas);

        // 5. Inicia la interfaz y le pasa los cerebros para que los use
//        this.menu = new Menu(gestorUsuario, gestorDeCatalogo, gestorDeVentas);
    }

    /**
     * Método que "enciende" el cine.
     * Llama al bucle principal del menú.
     */
    public void iniciar() {
      System.out.println("--- BIENVENIDO A " + this.nombreCine.toUpperCase() + " ---");
        this.menu.mostrarMenuPrincipal();
       System.out.println("\n--- Gracias por visitar " + this.nombreCine + " ---");
    }
}

