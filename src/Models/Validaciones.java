package Models;

import java.util.regex.Pattern;

public class Validaciones {



    public static boolean isStringValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /**
     * Comprueba si un DNI tiene formato numérico.
     */
    public static boolean isDniValido(String dni) {
        if (!isStringValido(dni)) {
            return false;
        }
        return dni.trim().matches("\\d+"); // \d+ significa "uno o más dígitos"
    }

    /**
     * Comprueba si un email tiene un formato válido.
     */
    public static boolean isEmailValido(String email) {
        if (!isStringValido(email)) {
            return false;
        }

        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email.trim());
    }

    /**
     * Comprueba si una edad está dentro de un rango.
     */
    public static boolean isRangoValido(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }



}
