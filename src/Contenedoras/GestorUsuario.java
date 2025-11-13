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
        // Intenta leer el archivo usando tu método
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_USUARIOS);

        // 2. Comprueba si el archivo NO existía (tu método devuelve null)
        if (tokener == null) {
            System.out.println("No se encontró " + ARCHIVO_USUARIOS + ". Creando admin por defecto...");
            crearAdminPorDefecto(); // Crea el admin y guarda el archivo
        } else {
            // 3. Si SÍ existía, lo carga en memoria
            System.out.println("Cargando usuarios desde " + ARCHIVO_USUARIOS + "...");
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
                    // Rellena el objeto usando tu método estático (Paso Previo 1)
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
                "Admin General", "11111111", 18, "admin@cine.com", "admin");

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
     * Usa tu JsonUtiles.grabarUnJson
     */
    public void guardarDatos() {
        JSONArray jsonArray = new JSONArray();

        // 2. Usa el método getMapa() que agregaste en el Repositorio (Paso Previo 3)
        for (Usuario usuario : repoUsuario.getUsuarios().values()) {
            jsonArray.put(usuario.toJson());
        }

        // 3. Llama a tu método exacto de JsonUtiles
        JsonUtiles.grabarUnJson(jsonArray, ARCHIVO_USUARIOS);
    }


    public String crearUsuario(String tipo, String nombre, String dni, String edadStr, String email, String contrasenia) {

        // ... ( código de validaciones) ...

        try {
            // --- Simulación de tu código de validación y creación ---
            if (!Validaciones.isStringValido(nombre) || !Validaciones.isDniValido(dni) || !Validaciones.isEmailValido(email) || !Validaciones.isStringValido(contrasenia)) {
                return "Error: Campos inválidos.";
            }
            int edad = Integer.parseInt(edadStr.trim());
            if (!Validaciones.isRangoValido(edad, 18, 90)) {
                return "Error: Edad fuera de rango.";
            }

            Usuario usuario;
            String tipoAniadido;
            if (tipo.equalsIgnoreCase("Cliente")) {
                usuario = new Cliente(nombre.trim(), dni.trim(), edad, email.trim(), contrasenia.trim());
                tipoAniadido = "Cliente";
            } else {
                usuario = new Administrador(nombre.trim(), dni.trim(), edad, email.trim(), contrasenia.trim());
                tipoAniadido = "Administrador";
            }
            // --- Fin Simulación ---

            repoUsuario.agregarUsuario(usuario); // Agrega a memoria

            // ¡IMPORTANTE! Guarda en disco después de agregar
            guardarDatos();

            return tipoAniadido + " agregado correctamente: " + usuario.toString();

        } catch (NumberFormatException e) {
            return "Error: La edad debe ser un número.";
        } catch (Exception e) { // Atrapa ElementoRepetido, VerificarNulo
            return "Error al agregar usuario: " + e.getMessage();
        }
    }

    public String iniciarSesion(String dni, String contrasenia) {
        // ¡Este método no necesita cambios!
        // El repositorio ya fue cargado por el constructor.

        Usuario usuarioEncontrado = repoUsuario.buscarUsuarioPorDni(dni.trim());

        if (usuarioEncontrado == null) {
            return "Error: DNI o contraseña incorrectos.";
        }

        // Gracias al Paso Previo 1, getContrasenia() no será null
        if (usuarioEncontrado.getContrasenia().equals(contrasenia.trim())) {
            return "Login exitoso. ¡Bienvenido, " + usuarioEncontrado.getNombre() + "!";
        } else {
            return "Error: DNI o contraseña incorrectos.";
        }
    }

    // (Necesario para que el Menu obtenga el objeto)
    public Usuario buscarUsuarioPorDni(String dni) {
        return repoUsuario.buscarUsuarioPorDni(dni.trim());
    }
}