package Casino.Clases;

/**
 * Clase GestorDatos: Encargada de gestionar los datos del juego, incluyendo la carga de jugadores,
 * la baja de jugadores y el manejo de las apuestas. Esta clase contiene métodos para registrar jugadores,
 * manejar las apuestas a números, colores, paridad y docenas, y validar los datos ingresados por los jugadores.
 * 
 * Métodos principales:
 * - cargarJugadores: Carga los jugadores en el juego.
 * - darDeBajaJugador: Baja un jugador, desactivándolo para futuras apuestas.
 * - cargarJugador: Carga los datos de un jugador, incluyendo nombre y cantidad de fichas.
 * - cargarApuestaNumeros: Permite a un jugador realizar una apuesta a números.
 * - cargarApuestaColor: Permite a un jugador realizar una apuesta al color (rojo/negro).
 * - cargarApuestaParidad: Permite a un jugador realizar una apuesta a paridad (par/impar).
 * - cargarApuestaDocena: Permite a un jugador realizar una apuesta a docenas.
 * - validarFichas: Valida si el jugador tiene suficiente saldo para realizar una apuesta.
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import Casino.Libreria.*;
import Casino.ClasesEnum.*;
import javax.swing.JOptionPane;
import Casino.Pantallas.RuletaMenuPrincipal;

public class GestorDatos {
    /************************************************************* */
    // METODO PARA INICIALIZAR EL ARRAY JUGADORES
    /************************************************************* */
    public void cargarJugadores(Jugador[] jugadores) {
        for (int i = 0; i < jugadores.length; i++) {
            // Mostrar mensaje de carga
            Salida.mMensaje("Cargando datos del Jugador " + (i + 1), "ENTRADA");

            // Crear un nuevo jugador y asignarlo al array
            jugadores[i] = crearJugador(jugadores);
        }
    }

    /************************************************************* */
    // METODO PARA CREAR JUGADORES
    /************************************************************* */
    private Jugador crearJugador(Jugador[] jugadores) {
        String nombre;
        do {
            // Solicitar nombre
            nombre = Ingreso.datoString("Ingrese Nombre del Jugador", null, 1);

            // Validar si el nombre ya existe
            GestorDeImpresiones gestorImp = new GestorDeImpresiones();
            if (gestorImp.buscarJugador(jugadores, nombre) != -1) {
                Salida.mAdvertencia("El nombre " + nombre + " ya está en uso. Intente con otro nombre.", "ADVERTENCIA");
            } else {
                break; // Salir del bucle si el nombre no está duplicado
            }
        } while (true);

        // Solicitar cantidad de fichas
        int cantFichas = Ingreso.datoEntero("Cantidad de Fichas: ", nombre, 1);

        // Inicializar el estado activo
        boolean activo = true;

        // Retornar un nuevo jugador
        return new Jugador(nombre, cantFichas, activo);
    }

    /************************************************************* */
    // BAJA JUGADOR
    /************************************************************* */
    public static void darDeBajaJugador(Jugador[] jugadores, String nombre) {
        GestorDeImpresiones gestorImp = new GestorDeImpresiones();
        int indice = gestorImp.buscarJugador(jugadores, nombre); // Obtengo índice del jugador
        if (indice != -1) {
            jugadores[indice].setActivo(false); // Desactiva al jugador
            Salida.mConfirmacion("El jugador " + nombre + " ha sido dado de baja.", "CONFIRMACION");
        } else {
            Salida.mAdvertencia("Jugador con nombre " + nombre + " no encontrado.", "ADVERTENCIA");
        }
    }

    /************************************************************* */
    // CARGAR APUESTAS NUMEROS
    /************************************************************* */
    public void cargarApuestaNumeros(Jugador jugador) {
        int contApuestas = 0; // Contador para el máximo de apuestas
        int respuesta = 0;

        do {
            boolean numeroCorrecto = false;
            contApuestas++;

            // Validar número
            int numero = -1;
            try {
                numero = Ingreso.datoEntero("Ingrese el número que desea apostar (0 al 36): ", jugador.getNombre(), 1);
                if (numero == -1) { // Entrada cancelada desde el Método Ingreso.datoEntero
                    Salida.mAdvertencia("Usted canceló la entrada", "¡ADVERTENCIA!");
                    return; // Salgo del ciclo
                }
                if (numero >= 0 && numero <= 36) {
                    numeroCorrecto = true; // Número válido
                } else {
                    Salida.mAdvertencia("Número inválido. Debe estar entre 0 y 36.", "ADVERTENCIA");
                }
            } catch (Exception e) {
                Salida.mMensaje("Error ingresando el número. Vuelva a intentarlo.", "Info");
            }
            try {
                if (numeroCorrecto) { // Pedir fichas solo si el número es válido
                    int cantFichas = Ingreso.datoEntero(
                            "Ingrese la cantidad de fichas a apostar \n Le quedan " + jugador.getCantFichas()
                                    + " fichas",
                            "INGRESO FICHAS NUMEROS", 1);

                    // Validar si las fichas son suficientes
                    boolean fichasValidas = validarFichas(jugador, cantFichas);
                    if (fichasValidas) {
                        jugador.apostarNumero(numero, cantFichas);
                    } else {
                        System.out.println("Apuesta rechazada.");
                        Salida.mAdvertencia("No se pudo realizar la apuesta. Inténtelo de nuevo.", "Error");
                        contApuestas--; // No contar esta apuesta
                    }
                }

            } catch (Exception e) {
                //e.printStackTrace(); // Muestra la traza completa del error
                Salida.mAdvertencia("Error ingresando la cantidad de fichas. Vuelva a intentarlo.", "Info");
            }

            // Preguntar si desea apostar nuevamente
            if (contApuestas < 3 && jugador.getCantFichas() > 0) {
                respuesta = JOptionPane.showConfirmDialog(null, "¿Desea apostar a otro número?", "CONSULTA",
                        JOptionPane.YES_NO_OPTION);
            } else {
                if (jugador.getCantFichas() == 0) {
                    Salida.mAdvertencia("Se ha quedado sin saldo", "Gracias por apostar");
                }
            }
            if (contApuestas == 3) {
                Salida.mAdvertencia("Ha realizado las 3 apuestas", "Gracias por apostar");
            }

        } while (respuesta == JOptionPane.YES_OPTION && contApuestas < 3 && jugador.getCantFichas() > 0);

    }

    /************************************************************* */
    // CARGAR APUESTAS COLOR
    /************************************************************* */
    public void cargarApuestaColor(Jugador jugador) {
        do {
            enumColor color = enumColor.SIN_APUESTA;
            try {
                int cantFichas = Ingreso.datoEntero(
                        "Ingrese la cantidad de fichas a apostar \n Le quedan" + jugador.getCantFichas()
                                + " fichas",
                        "INGRESO FICHAS COLOR",
                        1);
                if (validarFichas(jugador, cantFichas)) {
                    color = (enumColor) JOptionPane.showInputDialog(
                            null,
                            "Seleccione Color",
                            "COLOR",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            enumColor.values(),
                            enumColor.values()[0]);

                    // Verificar si el usuario cancela o no selecciona nada
                    if (!(color == color.SIN_APUESTA)) {
                        jugador.apostarColor(color, cantFichas);

                        return; // Salir del ciclo si la selección es válida
                    } else {
                        Salida.mAdvertencia("No se ha seleccionado ninguna apuesta.", "ADVERTENCIA");
                    }
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "No tiene saldo suficiente para realizar esta apuesta");
            }
            return;
        } while (true); // Reintentar hasta que el usuario seleccione una opción válida

    }

    /************************************************************* */
    // CARGAR APUESTAS PARIDAD
    /************************************************************* */
    public void cargarApuestaParidad(Jugador jugador) {
        do {
            enumParidad paridad = enumParidad.SIN_APUESTA;
            try {
                int cantFichas = Ingreso.datoEntero(
                        "Ingrese la cantidad de fichas a apostar \n Le quedan" + jugador.getCantFichas()
                                + " fichas",
                        "INGRESO FICHAS PARIDAD", 1);
                if (validarFichas(jugador, cantFichas)) {
                    paridad = (enumParidad) JOptionPane.showInputDialog(
                            null,
                            "Seleccione PAR / IMPAR",
                            "PARIDAD",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            enumParidad.values(),
                            enumParidad.values()[0]);

                    // Verificar si el usuario cancela o no selecciona nada
                    if (!(paridad == paridad.SIN_APUESTA)) {
                        jugador.apostarParidad(paridad, cantFichas);

                        return; // Salir del ciclo si la selección es válida
                    } else {
                        Salida.mAdvertencia("No se ha seleccionado ninguna apuesta.", "ADVERTENCIA");
                    }
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null,
                        "No tiene saldo suficiente para realizar esta apuesta");
            }
            return;
        } while (true); // Reintentar hasta que el usuario seleccione una opción válida

    }

    /************************************************************* */
    // CARGAR APUESTAS DOCENA
    /************************************************************* */
    public void cargarApuestaDocena(Jugador jugador) {
        do {
            enumDocenas docenas = enumDocenas.SIN_APUESTA;
            try {
                int cantFichas = Ingreso.datoEntero(
                        "Ingrese la cantidad de fichas a apostar \n Le quedan" + jugador.getCantFichas()
                                + " fichas",
                        "INGRESO FICHAS DOCENA", 1);
                if (validarFichas(jugador, cantFichas)) {
                    docenas = (enumDocenas) JOptionPane.showInputDialog(
                            null,
                            "Seleccione PAR / IMPAR",
                            "DOCENA",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            enumDocenas.values(),
                            enumDocenas.values()[0]);

                    // Verificar si el usuario cancela o no selecciona nada
                    if (!(docenas == docenas.SIN_APUESTA)) {
                        jugador.apostarDocenas(docenas, cantFichas);

                        return; // Salir del ciclo si la selección es válida
                    } else {
                        Salida.mAdvertencia("No se ha seleccionado ninguna apuesta.", "ADVERTENCIA");
                    }
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null,
                        "No tiene saldo suficiente para realizar esta apuesta");
            }
            return;
        } while (true); // Reintentar hasta que el usuario seleccione una opción válida

    }

    /************************************************************* */
    // METODO COMUN PARA VALIDAR FICHAS
    /************************************************************* */
    public static boolean validarFichas(Jugador jugador, int cantFichas) {
        if (cantFichas <= 0 || cantFichas > jugador.getCantFichas()) {
            Salida.mAdvertencia("Saldo insuficiente. Fichas disponibles: " + jugador.getCantFichas(),
                    "SALDO INSUFICIENTE");
            return false;
        }
        return true;
    }

}