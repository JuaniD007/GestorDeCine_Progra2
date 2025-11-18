package Contenedoras;

import Models.*;
import ModelsJson.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import Excepciones.*;

import javax.print.attribute.standard.JobSheets;
import java.util.regex.Pattern;

// Se elimina la importación de Scanner ya que no se usa para leer entrada
// en esta versión refactorizada de la clase de lógica.

public class GestorUsuario {
    private RepositorioUsuario<Usuario> repoUsuario = new RepositorioUsuario<>();
    private static final String ARCHIVO_USUARIOS = "usuarios.json";

    // --- 1. CONSTRUCTOR PRINCIPAL (EL MOTOR DE ARRANQUE) ---
    public GestorUsuario() {
        // Intenta leer el archivo usando el método
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_USUARIOS);

        // 2. Comprueba si el archivo NO existía (tu método devuelve null)
        if (tokener == null) {
            crearAdminPorDefecto(); // Crea el admin y guarda el archivo
        } else {
            try {
                JSONArray usuariosJson = new JSONArray(tokener); // Convierte el Tokener a JSONArray
                cargarUsuariosDesdeJson(usuariosJson);
            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
    }

    /**
     * Método privado para cargar un JSONArray en el repositorio de memoria.
     * (Usado por el constructor si el JSON existe)
     */
    private void cargarUsuariosDesdeJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                String tipo = obj.getString("tipo"); // <-- Paso Previo 2
                Usuario usuario = null;

                // Fábrica para decidir qué clase instanciar
                if (tipo.equals("Administrador")) {
                    usuario = new Administrador("", "", 0, "", ""); // Objeto temporal
                } else if (tipo.equals("Cliente")) {
                    usuario = new Cliente("", "", 0, "", ""); // Objeto temporal
                }

                if (usuario != null) {
                    // Rellena el objeto usando el método estático (Paso Previo 1)
                    usuario = Usuario.traerDesdeJson(usuario, obj);
                    repoUsuario.agregarUsuario(usuario); // Agrega a memoria
                }
            } catch (Exception e) {
                System.err.println("Error al cargar un usuario desde JSON: " + e.getMessage());
            }
        }
    }

    /**
     * Método privado para crear el admin hardcodeado y guardarlo.
     * (Usado por el constructor si el JSON NO existe)
     */
    private void crearAdminPorDefecto() {
        Administrador admin = new Administrador(
                "Admin General", "1111", 18, "admin@cine.com", "admin");

        try {
            // 1. Agrega el admin al repositorio en memoria
            repoUsuario.agregarUsuario(admin);

            // 2. Guarda el estado actual (con el admin) en el JSON
            guardarDatos();

        } catch (VerificarNulo | ElementoRepetido e) {
            System.err.println("Error fatal al crear admin por defecto: " + e.getMessage());
        }
    }

    /**
     * Método PÚBLICO para guardar el estado actual del repositorio en el JSON.
     * Usa  JsonUtiles.grabarUnJson
     */
    public void guardarDatos() {
        JSONArray jsonArray = new JSONArray();

        // 2. Usa el método getUsuarios() que se agrego en el Repositorio (Paso Previo 3)
        for (Usuario usuario : repoUsuario.getUsuarios().values()) {
            jsonArray.put(usuario.toJson());
        }

        // 3. Llama al método exacto de JsonUtiles
        JsonUtiles.grabarUnJson(jsonArray, ARCHIVO_USUARIOS);
    }


    public void crearUsuario(String tipo, String nombre, String dni, String edadStr, String email, String contrasenia)
            throws ValidacionException, ElementoRepetido, VerificarNulo, NumberFormatException {

        // --- 1. VALIDACIONES (lanzan excepción si fallan) ---


        if (!Validaciones.isNombreValido(nombre)) {
            throw new ValidacionException("Error: El nombre solo puede contener letras y espacios.");
        }

        if (!Validaciones.isStringValido(nombre)) {
            throw new ValidacionException("Error: El nombre no puede estar vacío.");
        }
        if (!Validaciones.isDniValido(dni)) {
            throw new ValidacionException("Error: DNI inválido.");
        }
        if (!Validaciones.isEmailValido(email)) {
            throw new ValidacionException("Error: formato de Email inválido, Email valido: example@gmail.com ");
        }

        if (repoUsuario.buscarUsuarioPorEmail(email) != null) {
            throw new ValidacionException("Error: El email '" + email.trim() + "' ya está registrado.");
        }

        if (!Validaciones.isStringValido(contrasenia)) {
            throw new ValidacionException("Error: La contraseña no puede estar vacía.");
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr.trim());
        } catch (NumberFormatException e) {
            // Relanza la excepción para que el Menú la atrape
            throw new NumberFormatException("Error: La edad debe ser un número entero.");
        }

        if (!Validaciones.isRangoValido(edad, 18, 90)) {
            throw new ValidacionException("Error: Edad inválida. Debe estar entre 18 y 90.");
        }
        // --- FIN VALIDACIONES ---

        // --- 2. CREACIÓN DEL OBJETO ---
        Usuario usuario;
        if (tipo != null && tipo.equalsIgnoreCase("Cliente")) {
            usuario = new Cliente(nombre.trim(), dni.trim(), edad, email.trim(), contrasenia.trim());
        } else {
            usuario = new Administrador(nombre.trim(), dni.trim(), edad, email.trim(), contrasenia.trim());
        }

        // --- 3. AGREGAR AL REPOSITORIO (lanza ElementoRepetido si falla) ---
        repoUsuario.agregarUsuario(usuario);

        // --- 4. PERSISTIR ---
        guardarDatos();

        // Si llega aquí, todo salió bien (no devuelve nada)
    }

    /**
     * Valida el inicio de sesión.
     * Devuelve el objeto Usuario si es exitoso.
     * Lanza una excepción si falla.
     */
    public Usuario iniciarSesion(String dni, String contrasenia)
            throws Exception {

        // 1. Validación de formato
        if (!Validaciones.isStringValido(dni) || !Validaciones.isStringValido(contrasenia)) {
            throw new ValidacionException("Error: DNI y contraseña no pueden estar vacíos.");
        }

        // 2. Validación de existencia
        Usuario usuarioEncontrado = repoUsuario.buscarUsuarioPorDni(dni.trim());

        if (usuarioEncontrado == null) {
            // Mensaje genérico por seguridad
            throw new Exception("Error: DNI o contraseña incorrectos.");
        }

        // 3. Validación de contraseña
        if (usuarioEncontrado.getContrasenia().equals(contrasenia.trim())) {
            // ¡Éxito! Devuelve el objeto Usuario completo
            return usuarioEncontrado;
        } else {
            // Mensaje genérico por seguridad
            throw new Exception("Error: DNI o contraseña incorrectos.");
        }
    }

}