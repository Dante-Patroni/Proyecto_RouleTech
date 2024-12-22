package Casino.Libreria;

import javax.swing.JOptionPane;

public class Salida {

    /*-------------------------------------------------*/
    /* Constructor privado para prevenir instanciación */
    /*-------------------------------------------------*/
    private Salida() {
    }

    /*-------------------------------------------------*/
    /* Método para mostrar un mensaje de información */
    /*-------------------------------------------------*/
    public static void mMensaje(String mensaje, String cTitulo) {
        JOptionPane.showMessageDialog(null, mensaje, cTitulo == null ? "Información" : cTitulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /*-------------------------------------------------*/
    /* Método para mostrar un mensaje de advertencia */
    /*-------------------------------------------------*/
    public static void mAdvertencia(String mensaje, String cTitulo) {
        JOptionPane.showMessageDialog(null, mensaje, cTitulo == null ? "Advertencia" : cTitulo,
                JOptionPane.WARNING_MESSAGE);
    }

    /*-------------------------------------------------*/
    /* Método para mostrar un mensaje de error */
    /*-------------------------------------------------*/
    public static void mError(String mensaje, String cTitulo) {
        JOptionPane.showMessageDialog(null, mensaje, cTitulo ==null ? "Error": cTitulo, JOptionPane.ERROR_MESSAGE);
    }

    /*-------------------------------------------------*/
    /* Método para mostrar un mensaje de confirmación */
    /*-------------------------------------------------*/
    public static int mConfirmacion(String mensaje, String cTitulo) {
        return JOptionPane.showConfirmDialog(null, mensaje, cTitulo == null ? "Confirmación" : cTitulo,
                JOptionPane.YES_OPTION);
    }
    
    public static int mConfirmacionOk(String mensaje, String cTitulo) {
        // Opciones personalizadas para los botones
        Object[] opciones = { "OK" };

        // Mostrar el diálogo con las opciones personalizadas
        return JOptionPane.showOptionDialog(
                null,
                mensaje,
                cTitulo == null ? "Confirmación" : cTitulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]); // Por defecto, "OK"
    }

}