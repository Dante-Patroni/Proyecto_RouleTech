package Casino.Pantallas;

/**
 * Clase RuletaMenuPrincipal: Representa la ventana principal del juego de ruleta.
 * Esta clase gestiona la interfaz gráfica para interactuar con el usuario, permitiéndole
 * realizar acciones como agregar jugadores, cargar apuestas, ver resultados, iniciar una ronda, 
 * y salir del juego. Utiliza una serie de botones para ejecutar las funciones disponibles del juego.
 * 
 * Atributos:
 * - frame: El contenedor principal de la ventana de la interfaz gráfica.
 * - apuestasTextArea: Área de texto para mostrar las apuestas realizadas.
 * - jugadores: Array que almacena los jugadores activos en el juego.
 * - jugadorActual: Controla el índice del jugador que está en turno.
 * 
 * Métodos:
 * - mostrarVentana: Muestra la ventana principal del menú del juego.
 * - gestionarApuestasJugador: Permite gestionar las apuestas de un jugador durante su turno.
 * - mostrarReglasDelJuego: Muestra las reglas del juego en una ventana de texto.
 * - iniciarNuevaRonda: Reinicia las apuestas y las fichas de los jugadores para una nueva ronda.
 * 
 * @author Dante Patroni
 * @version 1.0
 */

import javax.swing.*;

import Casino.Clases.GestorDatos;
import Casino.Clases.GestorDeImpresiones;
import Casino.Clases.GestorJuego;
import Casino.Clases.Jugador;
import Casino.Libreria.Ingreso;
import Casino.Libreria.Salida;
import Casino.ClasesEnum.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class RuletaMenuPrincipal {
    private int jugadorActual; // Controla el jugador en turno
    private JFrame frame;
    private JTextArea apuestasTextArea;
    //Jugador[] jugadores = crearJugadoresDePrueba();//Carga automática de datos
    private Jugador[] jugadores; // Array de jugadores

    public RuletaMenuPrincipal() {
        // Crear el marco principal
        frame = new JFrame("Apuestas");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiar a DISPOSE_ON_CLOSE para cerrar solo esta
                                                                 // ventana
        frame.setSize(800, 530);
        frame.setLayout(new BorderLayout());
        // Centrar el frame en la pantalla
        frame.setLocationRelativeTo(null);

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Alineación top vertical del panel de los
                                                                             // botones
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes alrededor del panel

        // Crear botones para el menú con tamaño fijo
        Dimension buttonSize = new Dimension(150, 40); // Ancho y alto fijo para los botones
        JButton reglasButton = crearBoton("Reglas del Juego", buttonSize);
        JButton jugadoresButton = crearBoton("Agregar Jugadores", buttonSize);
        JButton apuestasButton = crearBoton("Cargar Apuestas", buttonSize);
        JButton jugarButton = crearBoton("Jugar", buttonSize);
        JButton verButton = crearBoton("Ver pantallas", buttonSize);
        JButton bajaButton = crearBoton("Baja jugador", buttonSize);
        JButton nuevaRondaButton = crearBoton("Nueva Ronda", buttonSize);
        JButton salirButton = crearBoton("Salir del Juego", buttonSize);

        // Agregar botones al panel con
        buttonPanel.add(reglasButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre botones
        buttonPanel.add(jugadoresButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(apuestasButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(jugarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(verButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(bajaButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(nuevaRondaButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(salirButton);

        // Crear un panel para la ruleta con la imagen
        JPanel PanelImagen = new JPanel() {
            private Image ImagenFondo;
            {
                // Cargar la imagen desde el archivo
                ImagenFondo = new ImageIcon(getClass().getResource("/Recursos/ImagePanelMenu.jpg")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen como fondo en el centro del panel
                if (ImagenFondo != null) {
                    g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Agregar el panel de botones al lado izquierdo
        frame.add(buttonPanel, BorderLayout.EAST);

        // Agregar el panel de imagen al centro
        frame.add(PanelImagen, BorderLayout.CENTER);

        /************************************************************* */
        // Botón "Salir del Juego"
        /************************************************************* */
        salirButton.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que deseas salir?", "Confirmación",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                frame.dispose(); // Solo cierra la ventana sin finalizar todo el programa
            }
        });
        /************************************************************* */
        // Acción para el botón "Cargar Jugadores"
        /************************************************************* */
        jugadoresButton.addActionListener(e -> {
            if (jugadores == null) { // Verifica si los jugadores ya fueron cargados
                int cantJugadores = Ingreso.datoEntero("Ingrese cantidad de Jugadores", "CARGA DE DATOS", 0);
                jugadores = new Jugador[cantJugadores];
                // Cargar jugadores desde el GestorDatos
                GestorDatos gestor = new GestorDatos();
                gestor.cargarJugadores(jugadores); // Cargar jugadores
            }
        });
        /************************************************************* */
        // Acción para mostrar las reglas de juego
        reglasButton.addActionListener(e -> mostrarReglasDelJuego());
        /************************************************************* */
        /************************************************************* */
        // Acción para dar de baja un jugador
        /************************************************************* */
        bajaButton.addActionListener(e -> {
            GestorDatos gestor = new GestorDatos();
            String nombre = Ingreso.datoString("Ingrese jugador a dar de baja", "BAJA JUGADOR", 1);
            gestor.darDeBajaJugador(jugadores, nombre); // Cargar jugadores
        });
        /************************************************************* */
        // Acción que comienza una nueva ronda de juego
        /************************************************************* */
        nuevaRondaButton.addActionListener(e -> {
            iniciarNuevaRonda(jugadores);
            cargarApuestas();
        });
        /************************************************************* */
        // Botón "Cargar Apuestas"
        /************************************************************* */
        apuestasButton.addActionListener(e -> {
            cargarApuestas();
        });
        /************************************************************* */
        // Jugar: obtener número y resultados del juego
        /************************************************************* */
        jugarButton.addActionListener(e -> {
            Random random = new Random();
            int numero = random.nextInt(37); // Genera un número aleatorio entre 0 y 36 (inclusive)
            GestorJuego gestorJuego = new GestorJuego(numero, jugadores);
            gestorJuego.comprobarGanadores(jugadores, numero);
        });
        /************************************************************* */
        // Acción para Mostrar los listados disponibles
        /************************************************************* */
        verButton.addActionListener(e -> {
            if (jugadores != null && jugadores.length > 0) {
                mostrarListados(jugadores);
            } else {
                JOptionPane.showMessageDialog(null, "No hay jugadores cargados.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /* ......................................................... */
    /* METODOS */
    /*--------------------------------------------------------- */
    /************************************************************* */
    // METODO PARA MOSTRAR LISTADO APUESTA REALIZADAS EN LA RONDA
    /************************************************************* */

    private void cargarApuestas(){
        if (jugadores != null && jugadores.length > 0) {
            // Recorrer todos los jugadores
            for (int i = jugadorActual; i < jugadores.length; i++) {
                if (jugadores[i].isActivo()) {
                    // Mostrar mensaje del turno del jugador
                    JOptionPane.showMessageDialog(null,
                            "Es el turno del jugador: " + jugadores[i].getNombre(),
                            "Turno de jugador", JOptionPane.INFORMATION_MESSAGE);
                    // Gestionar las apuestas del jugador actual
                    gestionarApuestasJugador(jugadores[i]);//Menú apuestas / Lin. 302
                    // Si es el último jugador, reiniciar para la siguiente ronda
                    if (i == jugadores.length - 1) {
                        JOptionPane.showMessageDialog(null, "Todos los jugadores han completado su turno.",
                                "Fin de la ronda", JOptionPane.INFORMATION_MESSAGE);
                        jugadorActual = 0; // Reiniciar para la siguiente ronda
                    }
                } else {
                    Salida.mAdvertencia(jugadores[i].getNombre() + " no está activo, no puede apostar.",
                            "ADVERTENCIA");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay jugadores cargados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void mostrarListados(Jugador[] aJugadores) {
        GestorDeImpresiones ver = new GestorDeImpresiones();
        boolean seguir = true;
        while (seguir) {
            String[] opciones = { "Apuestas realizadas (Ronda)", "Informe final", "Informe por jugador", "Salir" };
            int eleccion = JOptionPane.showOptionDialog(null, "Seleccione listado",
                    "Opciones de apuesta",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);

            switch (eleccion) {
                case 0: // Listado Jugadores por ronda ordenados alfabetivamente
                    ver.ordenarPorNombre(jugadores);// Ordeno array alfabeticamente A --> Z
                    ver.listarApuestasRonda(jugadores); // Muestra apuesta ronda jugadores
                    break;
                case 1: // Muestra informe final de los Jugadores ordenados por fichas 10 --> 1
                    ver.ordenarPorFichas(jugadores);
                    ver.mostrarInformeFinal(jugadores);
                    break;
                case 2: // Muestra informe por jugador
                    String nombre = Ingreso.datoString("Ingrese jugador a listar", "DATOS JUGADOR", 1);
                    ver.mostrarJugadorEncontrado(jugadores, nombre);
                    break;
                case 3: // Salir Listados
                    seguir = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /************************************************************* */
    // MUESTRA LAS REGLAS DE JUEGO EN UNA CAJA DE TEXTO (TextArea)
    /************************************************************* */
    private void mostrarReglasDelJuego() {
        // Crear un JFrame para las reglas
        JFrame reglasFrame = new JFrame("Reglas del Juego");
        reglasFrame.setSize(600, 400);
        reglasFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reglasFrame.setLayout(new BorderLayout());
        // Centrar el frame en la pantalla
        reglasFrame.setLocationRelativeTo(null);

        // Crear un JTextArea para mostrar las reglas
        JTextArea reglasTextArea = new JTextArea();
        reglasTextArea.setEditable(false); // No editable
        reglasTextArea.setLineWrap(true); // Ajustar líneas automáticamente
        reglasTextArea.setWrapStyleWord(true); // Ajustar líneas por palabras

        // Establecer el texto de las reglas
        reglasTextArea.setText(
                """
                            Bienvenido a la Ruleta Electrónica RouletTech Systems.
                            Aquí están las reglas del juego:
                            1. Cada jugador carga su nombre y cantidad de fichas iniciales <<Agregar jugadores>>
                               Se recomienda que comiencen todos con la misma cantidad de fichas.
                            2. Comenzar a apostar. Se puede apostar en las siguientes categorías:<<Cargar Auestas>>
                               - Números específicos (máximos tres números). Paga 35:1
                               - Color (rojo/negro). Paga 1:1
                               - Paridad (par/impar). Paga 1:1
                               - Docenas o filas. Paga 3:1
                               Se pueden realizar una apuesta o todas.
                            3. Las fichas apostadas se deducirán del saldo del jugador.
                            4. El número se genera aleatoriamente al seleccionar <<Jugar>>
                            5. Se mostrará automáticamente el resultado y el listado de los jugadores que hayan obtenido premios.
                            6. Los jugadores sin fichas no pueden continuar jugando.
                            7. Se puede comenzar una nueva ronda <<Nueva Ronda>>
                            7. Puedes abandonar el juego en cualquier momento.<<Baja Jugador>>
                            8. Puedes ver diferentes listados por pantalla:<<Ver Pantallas>>
                                - Apuestas Realizadas en la ronda
                                - Apuestas realizadas por un jugador específico
                                - Resultados finales, muestra nombre de jugador y cantidad de fichas.
                            9. Para salir del Juego <<Salir del Juego>>
                            ¡Buena suerte y que ganes mucho!
                        """);

        // Agregar el JTextArea dentro de un JScrollPane para permitir el scroll
        JScrollPane scrollPane = new JScrollPane(reglasTextArea);
        reglasFrame.add(scrollPane, BorderLayout.CENTER);

        // Hacer visible la ventana
        reglasFrame.setVisible(true);
    }

    /************************************************************* */
    // MENU PARA GESTIONAR LAS APUESTAS DE LOS JUGADORES
    /************************************************************* */
    public void gestionarApuestasJugador(Jugador jugador) {
        GestorDatos gestor = new GestorDatos();
        boolean seguirApostando = true;
        while (seguirApostando) {
            // Preguntar en qué categoría quiere apostar
            String[] opciones = { "Número", "Color", "Par/Impar", "Docenas", "Finalizar" };
            int eleccion = JOptionPane.showOptionDialog(null, "Seleccione la categoría en la que desea apostar:",
                    "Opciones de apuesta",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);

            switch (eleccion) {
                case 0: // Apuesta a número
                    gestor.cargarApuestaNumeros(jugador);
                    break;
                case 1: // Apuesta a color
                    gestor.cargarApuestaColor(jugador);
                    break;
                case 2: // Apuesta a par/impar
                    gestor.cargarApuestaParidad(jugador);
                    break;
                case 3: // Apuesta a docenas
                    gestor.cargarApuestaDocena(jugador);
                    break;
                case 4: // Terminar turno
                    seguirApostando = false;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /************************************************************* */
    // Método auxiliar para crear botones con tamaño fijo
    /************************************************************* */
    private JButton crearBoton(String texto, Dimension size) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(size); // Tamaño máximo
        boton.setPreferredSize(size); // Tamaño preferido
        boton.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear el botón a la izquierda dentro del panel
        return boton;
    }

    /************************************************************* */
    // AL COMENZAR UNA NUEVA RONDA DE JUEGO REINICIO LAS APUESTAS
    /************************************************************* */
    public static void iniciarNuevaRonda(Jugador[] jugadores) {
        for (Jugador jugador : jugadores) {
            if (jugador.isActivo()) { // Verifica si el jugador está activo
                jugador.reiniciarApuestas(); // Reinicia sus apuestas
            }
        }

        Salida.mMensaje("Todas las apuestas han sido reiniciadas. \n¡Puede comenzar a apostar!", "NUEVA RONDA");
    }

    /************************************************************* */
    // Método para crear un array de Jugadores para pruebas
    /************************************************************* */

    private Jugador[] crearJugadoresDePrueba() {
        // Define el número de jugadores de prueba
        int cantidadJugadores = 3; // Puedes ajustar este número según sea necesario
        Jugador[] jugadoresPrueba = new Jugador[cantidadJugadores];

        // Crear jugadores con datos predefinidos
        jugadoresPrueba[0] = new Jugador("Pedro", 100, true); // Nombre, fichas, activo
        jugadoresPrueba[1] = new Jugador("Alicia", 150, true);
        jugadoresPrueba[2] = new Jugador("Juan", 200, true);

        // Cargar datos ficticios en las apuestas de cada jugador
        for (Jugador jugador : jugadoresPrueba) {
            jugador.cargarDatosAutomaticos();
        }

        // Retorna el array de jugadores de prueba
        return jugadoresPrueba;
    }



    /************************************************************* */
    // Método para mostrar la ventana
    /************************************************************* */
    public void mostrarVentana() {
        frame.setVisible(true);
    }
}
