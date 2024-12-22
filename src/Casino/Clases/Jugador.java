package Casino.Clases;

/**
 * Clase Jugador: Representa a un jugador en el juego de la ruleta.
 * Esta clase hereda de la clase Persona y gestiona las apuestas realizadas
 * por un jugador en las distintas categorías: números, color, paridad y docena.
 * 
 * Atributos:
 * - apuestasNumeros: Array de apuestas a números (máximo 3).
 * - apuestaColor: Apuesta realizada en color (rojo/negro).
 * - apuestaParImpar: Apuesta realizada en paridad (par/impar).
 * - apuestaDocena: Apuesta realizada en docena (1, 2, o 3).
 * - montos: Array con el monto apostado en cada categoría (números, color, paridad, docena).
 * - numApuestas: Número de apuestas realizadas (máximo 3 números).
 * 
 * Métodos:
 * - apostarNumero: Realiza una apuesta a un número específico.
 * - apostarColor: Realiza una apuesta al color (rojo/negro).
 * - apostarParidad: Realiza una apuesta a la paridad (par/impar).
 * - apostarDocenas: Realiza una apuesta a la docena (1, 2 o 3).
 * - reiniciarApuestas: Reinicia las apuestas del jugador al comenzar una nueva ronda.
 * - toString: Devuelve una representación textual del jugador con sus apuestas y saldo de fichas.
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import java.util.Arrays;

import Casino.ClasesEnum.*;
import Casino.Libreria.*;

public class Jugador extends Persona {
    private int[] apuestasNumeros; // Apuestas a números (máximo 3)
    private enumColor apuestaColor; // Apuesta al color (rojo/negro)
    private enumParidad apuestaParImpar; // Apuesta a par/impar (usando el enum)
    private enumDocenas apuestaDocena; // Apuesta a docena (1, 2 o 3)
    private int[] montos; // Monto apostado en cada categoría en cantidad de fichas
    private int numApuestas; // Número de apuestas realizadas, índice para apuesta de números 0 a 2

    /************************************************************* */
    // CONSTRUCTOR INICIALIZADO POR DEFECTO
    public Jugador(String nombre, int cantFichas, boolean activo) {
        super(nombre, cantFichas, activo);
        this.apuestasNumeros = new int[3]; // 3 apuestas a números
        for (int i = 0; i < 3; i++) {
            apuestasNumeros[i] = -1; // -1 indica que no se ha apostado en esa posición
        }
        apuestaColor = enumColor.SIN_APUESTA;// Por defecto, no se ha apostado a Color
        apuestaParImpar = enumParidad.SIN_APUESTA; // Por defecto, no se ha apostado a par/impar
        apuestaDocena = enumDocenas.SIN_APUESTA;// Por defecto, no se ha apostado a docena
        this.montos = new int[6]; // [0], [1], [2] para números; [3] para color; [4] para par/impar y [5] para docena.
    }

    /************************************************************* */
    // CONSTRUCTOR VACIO
    public Jugador() {
    }

    /************************************************************* */
    // GETTERS
    /************************************************************* */
    public int[] getApuestasNumeros() {
        return apuestasNumeros;
    }

    public enumColor getApuestaColor() {
        return apuestaColor;
    }

    public enumParidad getApuestaParImpar() {
        return apuestaParImpar;
    }

    public enumDocenas getApuestaDocena() {
        return apuestaDocena;
    }

    public int[] getMontos() {
        return montos;
    }

    public int getNumApuestas() {
        return numApuestas;
    }

    /************************************************************* */
    // GENERATE SETTERS
    /************************************************************* */
    public void setApuestasNumeros(int[] apuestasNumeros) {
        this.apuestasNumeros = apuestasNumeros;
    }

    public void setApuestaColor(enumColor apuestaColor) {
        this.apuestaColor = apuestaColor;
    }

    public void setApuestaParImpar(enumParidad apuestaParImpar) {
        this.apuestaParImpar = apuestaParImpar;
    }

    public void setApuestaDocena(enumDocenas apuestaDocena) {
        this.apuestaDocena = apuestaDocena;
    }

    public void setMontos(int[] montos) {
        this.montos = montos;
    }

    public void setNumApuestas(int numApuestas) {
        this.numApuestas = numApuestas;
    }

    /************************************************************* */
    // METODOS PARA LAS APUESTAS
    /************************************************************* */
    public void apostarNumero(int numero, int fichas) {

        if (fichas > cantFichas) {
            Salida.mAdvertencia("No tiene suficientes fichas para esta apuesta.", "ADVERTENCIA");
        }
        apuestasNumeros[numApuestas] = numero;
        montos[numApuestas] = fichas;
        actualizarFichas(fichas * -1);
        numApuestas++;// Posiciones de los arrays
        Salida.mConfirmacionOk("Su apuesta a sido registrada", "CONFIRMACION");
    }
    /************************************************************* */

    /************************************************************* */
    public void apostarColor(enumColor color, int fichas) {
        this.apuestaColor = color;
        montos[3] = fichas;
        actualizarFichas(fichas * -1);
        Salida.mConfirmacionOk("Su apuesta a sido registrada", "CONFIRMACION");

    }
    /************************************************************* */

    /************************************************************* */
    public void apostarParidad(enumParidad paridad, int fichas) {
        this.apuestaParImpar = paridad;
        montos[4] = fichas;
        actualizarFichas(fichas * -1);
        Salida.mConfirmacionOk("Su apuesta a sido registrada", "CONFIRMACION");
    }
    /************************************************************* */

    /************************************************************* */
    public void apostarDocenas(enumDocenas docena, int fichas) {
        this.apuestaDocena = docena;
        montos[5] = fichas;
        actualizarFichas(fichas * -1);
        Salida.mConfirmacionOk("Su apuesta a sido registrada", "CONFIRMACION");
    }
    /************************************************************* */

    /************************************************************* */
    // METODO QUE REINICIALIZA LAS APUESTAS AL COMENZAR OTRA RONDA
    /************************************************************* */
    public void reiniciarApuestas() {
    
        // Reiniciar las apuestas del jugador actual
        for (int i = 0; i < apuestasNumeros.length; i++) {
            apuestasNumeros[i] = -1;
        }

        for (int j = 0; j < montos.length; j++) {
            montos[j] = 0;
        }

        apuestaColor = enumColor.SIN_APUESTA;
        apuestaParImpar = enumParidad.SIN_APUESTA;
        apuestaDocena = enumDocenas.SIN_APUESTA;
        this.numApuestas = 0;

        // Desactivar al jugador si no tiene fichas
        if (cantFichas == 0) {
            this.setActivo(false); // Actualiza el estado del jugador actual
            System.out.println("Jugador desactivado por falta de fichas.");
        }
    }
        /**
 * Método para cargar datos ficticios en los arrays del jugador.
 * Ideal para pruebas de listados y bajas.
 */
public void cargarDatosAutomaticos() {
        // Cargar datos ficticios en las apuestas de números
        apuestasNumeros[0] = 5;
        apuestasNumeros[1] = 12;
        apuestasNumeros[2] = 25;

        // Cargar datos ficticios en los montos
        montos[0] = 10; // Monto apostado al número 5
        montos[1] = 15; // Monto apostado al número 12
        montos[2] = 20; // Monto apostado al número 25
        montos[3] = 0;  // Sin apuesta en color
        montos[4] = 0;  // Sin apuesta en paridad
        montos[5] = 0;  // Sin apuesta en docena

        // Opcional: Valores específicos para color, paridad y docena
        apuestaColor = enumColor.ROJO;
        apuestaParImpar = enumParidad.PAR;
        apuestaDocena = enumDocenas.PRIMERA;

        this.numApuestas = 1;

        // Mostrar un mensaje de confirmación
        System.out.println("Datos ficticios cargados para el jugador: " + getNombre());
    }

    

    
    /************************************************************* */
    @Override
    public String toString() {
        // Crear una lista de apuestas de números sin mostrar los valores -1
        StringBuilder apuestasNumerosString = new StringBuilder();
        for (int i = 0; i < apuestasNumeros.length; i++) {
            if (apuestasNumeros[i] != -1) {
                apuestasNumerosString.append(apuestasNumeros[i]).append("; ");
            } else {
                apuestasNumerosString.append("SIN_APUESTA. ");
            }
        }

        return String.format("Nombre : %s\n" +
                "Apuestas Realizadas en esta Ronda\n" +
                "Números apostados: %s\nApuesta Color : %s\n" +
                "Apuesta Paridad: %s\nApuesta Docena: %s\n" +
                "Saldo en Fichas: %d\n" +
                "============================================================\n",
                super.getNombre(),
                apuestasNumerosString.toString(),
                apuestaColor,
                apuestaParImpar,
                apuestaDocena,
                super.getCantFichas());
    }

}
