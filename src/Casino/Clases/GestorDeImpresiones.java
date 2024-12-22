package Casino.Clases;

/**
 * Clase GestorDeImpresiones: Encargada de gestionar las impresiones y listados de los jugadores y sus apuestas.
 * Esta clase proporciona métodos para buscar jugadores, ordenar por nombre o fichas, y generar informes sobre 
 * las apuestas realizadas y los resultados finales de los jugadores.
 * 
 * Métodos principales:
 * - buscarJugador: Busca un jugador por su nombre en el array de jugadores.
 * - listarApuestasRonda: Muestra todas las apuestas realizadas por los jugadores en una ronda.
 * - mostrarJugadorEncontrado: Muestra la información de un jugador dado su nombre.
 * - ordenarPorFichas: Ordena a los jugadores en el array por la cantidad de fichas en orden descendente.
 * - ordenarPorNombre: Ordena a los jugadores en el array por su nombre en orden alfabético.
 * - mostrarInformeFinal: Muestra el informe final con la cantidad de fichas de cada jugador.
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import javax.swing.JOptionPane;

import Casino.Libreria.*;

public class GestorDeImpresiones {
    /************************************************************* */
    /* Búsqueda secuencial de un jugador */
    /************************************************************* */
    public static int buscarJugador(Jugador[] jugadores, String nombre) {
        if (jugadores != null) {
            for (int i = 0; i < jugadores.length; i++) {
                if (jugadores[i] != null && jugadores[i].getNombre().equalsIgnoreCase(nombre)) {
                    return i; // Devuelve el índice del jugador encontrado
                }
            }
        }
        return -1; // Jugador no encontrado
    }

    /************************************************************* */
    /* Listados de apuestas por ronda */
    /************************************************************* */
    public static void listarApuestasRonda(Jugador[] aJugadores) {
        StringBuilder sb = new StringBuilder();
        sb.append("Apuestas de Jugadores: \n\n");

        for (int i = 0; i < aJugadores.length; i++) {
            Jugador jugador = aJugadores[i];
            if (jugador != null && jugador.isActivo() && jugador.getNumApuestas() > 0) {// Comprueba que el array no esté vacío y que el
                                                                        // jugador haya realizado apuestas
                sb.append(jugador);
            }
        }
        Salida.mMensaje(sb.toString(), "Apuestas Registradas");
    }

    /************************************************************* */
    /* Mostrar jugador encontrado */
    /************************************************************* */
    public static void mostrarJugadorEncontrado(Jugador[] aJugadores, String nombre) {
        int indice = buscarJugador(aJugadores, nombre); // Obtengo indice de la ubicación del jugador
        if (indice != -1) {
            Jugador jugador = aJugadores[indice];
            Salida.mMensaje(jugador.toString(), "Jugador");
        } else {
            Salida.mAdvertencia("Jugador con nombre " + nombre + " no encontrado.", "ADVERTENCIA");
        }
        // Accedo al curso en el índice dado
    }

    /************************************************************* */
    /* Ordeno Array por cantidad de fichas < a > */
    /************************************************************* */
    public static void ordenarPorFichas(Jugador[] aJugadores) {
        int size = aJugadores.length; // calculo la logitud del Array
        for (int i = 0; i < size - 1; i++) {
            // Se genera un registro de los intercambios
            boolean swapped = true;
            for (int j = 0; j < size - i - 1; j++) {// En este bucle se realizan los cambios

                if (aJugadores[j].getCantFichas() < aJugadores[j + 1].getCantFichas()) {

                    // Intercambio
                    Jugador temp = aJugadores[j];
                    aJugadores[j] = aJugadores[j + 1];
                    aJugadores[j + 1] = temp;

                    swapped = false; // Se realizó un cambio
                }
            }
            if (swapped == true)
                break;
        }

    }

    /************************************************************* */
    /* Ordeno Array por nombre de Jugadores A a Z */
    /************************************************************* */

    public static void ordenarPorNombre(Jugador[] aJugadores) {
        int size = aJugadores.length; // calculo la logitud del Array
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = true;
            for (int j = 0; j < size - i - 1; j++) {
                // Compara alfabéticamente usando compareTo

                if (aJugadores[j].getNombre().compareTo(aJugadores[j + 1].getNombre()) > 0) {
                    // Intercambia los objetos si están en el orden incorrecto
                    Jugador temp = aJugadores[j];
                    aJugadores[j] = aJugadores[j + 1];
                    aJugadores[j + 1] = temp;
                    swapped = false; // Se realizó un cambio
                }
            }
            if (swapped == true)
                break;
        }

    }

    /************************************************************* */
    /* MUESTRA POR PANTALLA INFORME FINAL */
    /************************************************************* */
    public void mostrarInformeFinal(Jugador[] jugadores) {
        StringBuilder sb = new StringBuilder();
        for (Jugador jugador : jugadores) {
            if (jugador != null && jugador.getCantFichas() > 0) {// Muestra los jugadores con fichas disponibles
                sb.append(String.format("%s tiene : %d fichas\n", jugador.getNombre(), jugador.getCantFichas()));
                sb.append("============================\n");
            }
        }
        if (sb.length() == 0) {
            sb.append("No hay jugadores con fichas disponibles.");
        }
        Salida.mMensaje(sb.toString(), "Resultado final");
    }

}