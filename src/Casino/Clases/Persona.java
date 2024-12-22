package Casino.Clases;

/**
 * Clase Persona: Representa a un jugador en el juego de la ruleta.
 * Esta clase contiene los atributos básicos de un jugador como su nombre,
 * la cantidad de fichas disponibles y su estado (activo o no).
 * Además, ofrece métodos para registrar a un jugador, darlo de baja, 
 * actualizar su cantidad de fichas y mostrar su información.
 * 
 * Atributos:
 * - nombre: El nombre del jugador.
 * - cantFichas: La cantidad de fichas que posee el jugador.
 * - activo: Un valor booleano que indica si el jugador está activo en el juego.
 * 
 * Métodos:
 * - registrarJugador: Registra al jugador con un nombre y cantidad de fichas.
 * - bajaJugador: Desactiva al jugador, indicándole que ha salido del juego.
 * - actualizarFichas: Modifica la cantidad de fichas del jugador, permitiendo sumar o restar fichas.
 * - toString: Devuelve una representación textual del jugador con su nombre y cantidad de fichas.
 * 
 * Dependencias:
 * - Esta clase depende de las clases 'Ingreso' y 'Salida' para interactuar con el usuario (para la entrada de datos y mostrar mensajes).
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import Casino.Libreria.*;

public class Persona {
    private String nombre;
    protected int cantFichas;
    private boolean activo;

    /************************************************************* */
    // CONSTRUCTOR
    public Persona(String nombre, int cantFichas, boolean activo) {
        this.nombre = nombre;
        this.cantFichas = cantFichas;
        this.activo = activo;
    }

    /************************************************************* */
    public Persona() {
    }

    /************************************************************* */
    // GETTERS
    /************************************************************* */
    public String getNombre() {
        return nombre;
    }

    public int getCantFichas() {
        return cantFichas;
    }

    public boolean isActivo() {
        return activo;
    }

    /************************************************************* */
    // SETTERS
    /************************************************************* */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    ////////////////////////// METODOS ////////////////////////////

    /************************************************************* */
    public void registrarJugador(String nombreJugador, int fichas) {
        this.nombre = nombreJugador;
        this.cantFichas = fichas; 
        this.activo = true;
    }
    /************************************************************* */
    public void bajaJugador() {
        if (this.activo) {
            this.activo = false;
            Salida.mMensaje("Tu baja ha sido cargada, gracias por jugar", "INFORME");
        } else {
            Salida.mMensaje("Ya estás dado de baja.", "INFORME");
        }
    }
    /************************************************************* */
    public void actualizarFichas(int fichas) {
        if (this.cantFichas + fichas < 0) {
            Salida.mMensaje("No tienes suficientes fichas para hacer esta operación.", "ERROR");
        } else {
            this.cantFichas += fichas;
        }
    }
    /************************************************************* */
    @Override
    public String toString() {
        return String.format("Nombre: %s\nCantidad de Fichas: %d\nActivo: %s\n",
                nombre, cantFichas, activo ? "Sí" : "No");
    }

}
