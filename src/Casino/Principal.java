package Casino;


/**
 * Clase Principal del proyecto "RouletTech Systems".
 * 
 * Esta clase inicializa el sistema de la ruleta llamando al menú principal
 * para que el usuario pueda interactuar con la aplicación.
 * 
 * Uso:
 * - Ejecutar el método main para iniciar la aplicación.
 * 
 * Proyecto: RouletTech Systems
 * Autor: Dante Patroni
 * Versión: 1.0
 */
import Casino.Pantallas.RuletaMenuPrincipal;

public class Principal {
    /**
     * Método principal que inicializa el menú principal del sistema.

     */
    public static void main(String[] args) {
        // Instancia y muestra la ventana del menú principal
        RuletaMenuPrincipal menu = new RuletaMenuPrincipal();
        menu.mostrarVentana();
    }
}
