package Contenedoras;

import Models.Usuario;
import Models.Cliente;
import Models.Empleado;

import java.util.regex.Pattern;
// Se elimina la importación de Scanner ya que no se usa para leer entrada
// en esta versión refactorizada de la clase de lógica.

public class GestorUsuario {
    // Repositorio genérico para almacenar usuarios (Cliente o Empleado)
    private RepositorioUsuario<Usuario> repoUsuario = new RepositorioUsuario<>();

    /**
     * Método para crear un usuario (Cliente o Empleado) según el tipo indicado,
     * realizando validaciones.
     *
     * @param tipo El tipo de usuario a crear ("Cliente" o "Empleado").
     * @param nombre Nombre del usuario.
     * @param dni DNI del usuario.
     * @param edadStr Edad del usuario como String (para manejo de errores de formato).
     * @param email Email del usuario.
     * @return String que contiene el mensaje de éxito (con el usuario) o el mensaje de error de validación/adición.
     */
    public String crearUsuario(String tipo, String nombre, String dni, String edadStr, String email) {
        // --- 1. VALIDACIONES DE ENTRADA ---

        // Validación de nombre
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre no puede estar vacío.";
        }
        nombre = nombre.trim();

        // Validación de DNI
        if (dni == null || dni.trim().isEmpty() || !dni.trim().matches("\\d+")) {
            return "DNI inválido. Debe ser numérico y no vacío.";
        }
        dni = dni.trim();

        // Validación de edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr.trim());
        } catch (NumberFormatException e) {
            return "Edad inválida. Debe ser un número entero.";
        }
        if (edad < 18 || edad > 90) {
            return "Edad inválida. Debe estar entre 18 y 90 años.";
        }

        // Validación de email
        email = email.trim();
        if (!esEmailValido(email)) {
            return "Email inválido. Verifique el formato.";
        }


        // --- 2. CREACIÓN DEL OBJETO Y ASIGNACIÓN DE ID (Implícito) ---
        // El ID se genera dentro del constructor de Cliente/Empleado (o Usuario)
        Usuario usuario;
        String tipoAñadido;

        if (tipo != null && tipo.equalsIgnoreCase("Cliente")) {
            usuario = new Cliente(nombre, dni, edad, email);
            tipoAñadido = "Cliente";
        } else {
            usuario = new Empleado(nombre, dni, edad, email);
            tipoAñadido = "Empleado";
        }

        // --- 3. AGREGAR AL REPOSITORIO ---
        try {
            // Llama al método del repositorio que valida duplicados (ej. por DNI) y agrega el usuario.
            if (repoUsuario.agregarUsuario(usuario)) {
                // El toString() de 'usuario' debe incluir el ID generado automáticamente.
                return tipoAñadido + " agregado correctamente: " + usuario.toString();
            } else {
                // Caso alternativo si verificarUsuario retorna false sin lanzar excepción
                return "Error desconocido al intentar agregar " + tipoAñadido + ".";
            }
        } catch (Exception e) {
            // Captura excepciones lanzadas por el repositorio (ej. DNI duplicado)
            return "Error al agregar usuario: " + e.getMessage();
        }
    }


    /**
     * Método auxiliar estático para validar email usando expresión regular.
     * Retorna true si el email cumple el patrón, false si no.
     */
    private static boolean esEmailValido(String email) {
        // Expresión regular para validar emails
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }
}