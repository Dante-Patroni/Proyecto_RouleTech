package Casino.Clases;

/**
 * Clase GestorJuego: Encargada de gestionar la lógica del juego de la ruleta.
 * Esta clase maneja el cálculo y la validación de las apuestas realizadas por los jugadores,
 * así como la determinación de los ganadores en cada ronda.
 * 
 * Atributos:
 * - paridad: Array que contiene la paridad (par/impar) para cada número de la ruleta.
 * - docena: Array que contiene la docena (1, 2, 3) para cada número de la ruleta.
 * - colores: Array que contiene el color (rojo, negro, verde) para cada número de la ruleta.
 * - numero: El número que salió en la ruleta en esta ronda.
 * 
 * Métodos:
 * - inicializarParidad: Inicializa el array de paridad con los valores correspondientes a cada número.
 * - inicializarDocena: Inicializa el array de docenas con los valores correspondientes a cada número.
 * - inicializarColores: Inicializa el array de colores con los valores correspondientes a cada número.
 * - comprobarGanadores: Recorre los jugadores y determina si han ganado en alguna de las apuestas.
 * - mostrarResultadoRonda: Muestra los resultados de la ronda en un cuadro de diálogo.
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import javax.swing.JOptionPane;
import Casino.ClasesEnum.*;
import Casino.Libreria.*;

public class GestorJuego {
    private enumParidad[] paridad; // Guarda los valores de paridad como enums
    private enumDocenas[] docena; // Guarda las docenas como enums
    private enumColor[] colores; // Guarda los colores como enums
    private int numero; // Número que salió en la ruleta

    // Constructor
    public GestorJuego(int numero, Jugador[] jugadores) {
        // Inicializar las estructuras de datos
        inicializarParidad();
        inicializarDocena();
        inicializarColores();
        this.numero = numero;
        enumColor colorNumero = colores[numero]; // Este método devuelve el color del número que salió
        enumParidad paridadNumero = paridad[numero]; // Este método devuelve la paridad del número que salió
        enumDocenas docenaNumero = docena[numero]; // Este método devuelve la docena del número que salió
    }


    // METODOS
    /************************************************************* */
    // Método para inicializar PARIDAD
    /************************************************************* */
    private void inicializarParidad() {
        paridad = new enumParidad[37];
        paridad[0] = enumParidad.SIN_APUESTA; // El 0 no es par ni impar
        for (int i = 1; i < paridad.length; i++) {
            if ((i % 2 == 0)) {
                paridad[i] = enumParidad.PAR;
            } else {
                paridad[i] = enumParidad.IMPAR;
            }
        }
    }

    /************************************************************* */
    // Método para inicializar DOCENAS
    /************************************************************* */
    private void inicializarDocena() {
        docena = new enumDocenas[37];
        for (int i = 0; i < docena.length; i++) {
            if (i == 0) {
                docena[i] = enumDocenas.SIN_APUESTA; // El 0 no pertenece a ninguna docena
            } else if (i <= 12) {
                docena[i] = enumDocenas.PRIMERA; // Primera docena
            } else if (i <= 24) {
                docena[i] = enumDocenas.SEGUNDA; // Segunda docena
            } else {
                docena[i] = enumDocenas.TERCERA; // Tercera docena
            }
        }
    }

    /************************************************************* */
    // Método para inicializar los colores
    /************************************************************* */
    private void inicializarColores() {
        colores = new enumColor[37];
        for (int i = 0; i < colores.length; i++) {
            if (i == 0) {
                colores[i] = enumColor.SIN_APUESTA; // El 0 tiene color asociado el verde pero no se puede apostar
            } else if (i == 1 || i == 3 || i == 5 || i == 7 || i == 9 || i == 12 || i == 14 || i == 16
                    || i == 18 || i == 19 || i == 21
                    || i == 23 || i == 25 || i == 27 || i == 30 || i == 32 || i == 34 || i == 36) {
                colores[i] = enumColor.ROJO;
            } else {
                colores[i] = enumColor.NEGRO;
            }
        }
    }

    /************************************************************* */
    //METODO PARA COMPROBAR SI HAY GANADORES, PAGAR E INFORMAR
    /************************************************************* */
    public void comprobarGanadores(Jugador[] jugadores, int numero) {
        mostrarResultadoRonda();//Muestro los resultados
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados de la Ronda:\n");

        // Determinar el color del número que salió
        enumColor colorNumero = colores[numero]; // Este método devuelve el color del número que salió
        enumParidad paridadNumero = paridad[numero]; // Este método devuelve la paridad del número que salió
        enumDocenas docenaNumero = docena[numero]; // Este método devuelve la docena del número que salió
        // Recorro el array de jugadores para ver si han ganado en alguna categoría
        for (Jugador jugador : jugadores) {
            sb.append(String.format("Jugador: %s\n", jugador.getNombre()));
            int totalGanado = 0;

            /* COMPRUEBO SI EL JUGADOR GANÓ EN LA APUESTA NUMERO */
            for (int i = 0; i < jugador.getApuestasNumeros().length; i++) {
 
                if (jugador.getApuestasNumeros()[i] == numero) {//Posibilidad de 3 apuestas a número
                    // El jugador ha ganado en este número
                    int fichasApostadas = jugador.getMontos()[i]; // Obtener el monto apostado en ese número
                    int fichasGanadas = fichasApostadas * 35; // El jugador gana 35 veces lo apostado por un número 35:1
                    totalGanado += fichasGanadas;

                    // Actualizar las fichas del jugador
                    jugador.actualizarFichas(fichasGanadas);//Paga

                    // Informar al jugador que ha ganado
                    sb.append(
                            String.format(" - Número: , Fichas Ganadas: %d\n", fichasGanadas));
                }
            }//FIN APUESTAS NUMEROS

            /*COMPRUEBO SI EL JUGADOR GANÓ EN LA APUESTA COLOR */
            if (jugador.getApuestaColor() != enumColor.SIN_APUESTA) {
                // Si el jugador apostó y el color coincide con el resultado
                if (jugador.getApuestaColor() == colorNumero) {
                    // El jugador ha ganado en color, obtiene el doble de lo apostado
                    int fichasApostadas = jugador.getMontos()[3]; // Obtener el monto apostado al color desde montos[3]
                    int fichasGanadas = fichasApostadas; // El jugador gana lo mismo de lo apostado 1:1
                    totalGanado += fichasGanadas;
                    // Actualizar las fichas del jugador
                    jugador.actualizarFichas(fichasGanadas);//Paga
                    // Informar al jugador que ha ganado
                    sb.append(
                            String.format(" - Color: , Fichas Ganadas: %d\n", fichasGanadas));
                        }
            }//FIN IF COLOR

            /* COMPRUEBO SI EL JUGADOR GANÓ EN LA APUESTA PARIDAD */
            if (jugador.getApuestaParImpar() != enumParidad.SIN_APUESTA) {
                // Si el jugador apostó y el color coincide con el resultado
                if (jugador.getApuestaParImpar() == paridadNumero) {
                    // El jugador ha ganado en color, obtiene el doble de lo apostado
                    int fichasApostadas = jugador.getMontos()[4]; // Obtener el monto apostado paridad desde montos[4]
                    int fichasGanadas = fichasApostadas; // El jugador gana lo mismo de lo apostado 1:1
                    totalGanado += fichasGanadas;
                    // Actualizar las fichas del jugador
                    jugador.actualizarFichas(fichasGanadas);//Paga
                    // Informar al jugador que ha ganado
                            sb.append(
                            String.format(" - Paridad: , Fichas Ganadas: %d\n", fichasGanadas));
                }
            } // FIN IF PARIDAD

            /* COMPRUEBO SI EL JUGADOR GANÓ EN LA APUESTA DOCENA */
            if (jugador.getApuestaDocena() != enumDocenas.SIN_APUESTA) {
                // Si el jugador apostó y el color coincide con el resultado
                if (jugador.getApuestaDocena() == docenaNumero) {
                    // El jugador ha ganado en color, obtiene el doble de lo apostado
                    int fichasApostadas = jugador.getMontos()[5]; // Obtener el monto apostado DOCENA desde montos[5]
                    int fichasGanadas = fichasApostadas * 3; // El jugador gana el triple de lo apostado
                    totalGanado += fichasGanadas;
                    // Actualizar las fichas del jugador
                    jugador.actualizarFichas(fichasGanadas);//Paga
                    // Informar al jugador que ha ganado
                    sb.append(
                            String.format(" - Docena: , Fichas Ganadas: %d\n", fichasGanadas));
                }
            } // FIN IF DOCENA
            // Agregar total del jugador
            sb.append(String.format("Total Ganado por %s: %d fichas\n", jugador.getNombre(),totalGanado));
            sb.append("============================\n");
        }//FIN FOR GENERAL
        Salida.mMensaje(sb.toString(), "Premios");
}//FIN METODO GANADORES

/************************************************************* */
//METODO QUE MUESTRA LOS RESULTADOS DE LA RONDA
/************************************************************* */
public void mostrarResultadoRonda() {
    JOptionPane.showMessageDialog(
            null,
            String.format(
                    "Salió número: %d\nColor: %s\nParidad del número: %s\nDocena del número: %s",
                    numero, colores[numero], paridad[numero], docena[numero]),
            "RESULTADO RONDA",
            JOptionPane.INFORMATION_MESSAGE);
}

}