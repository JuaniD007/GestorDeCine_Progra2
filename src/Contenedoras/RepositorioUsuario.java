package Contenedoras;

import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Interfaces.ItoJson;
import Models.Administrador;
import Models.Usuario;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RepositorioUsuario <T extends Usuario>   {
    private HashMap<String, T> usuarios = new HashMap<>();
    private static final String ARCHIVO_USUARIOS = "usuarios.json";
    public RepositorioUsuario() {
        // Creamos el admin harcodeado con los datos que pediste
        Administrador admin = new Administrador(
                "Admin General",   // nombre
                "11111111",        // dni
                99,                // edad (una edad simbólica)
                "admin@cine.com",  // email (necesita uno, puedes inventarlo)
                "admin"            // contrasenia
        );

        // Lo agregamos directamente al mapa.
        // Usamos (T) para "castear" el Administrador al tipo genérico T
        // (que en tu GestorUsuario será 'Usuario', así que es seguro)
        usuarios.put(admin.getDni(), (T) admin);
    }

    public HashMap<String, T> getUsuarios() {
        return usuarios;
    }


    public void agregarUsuario(T usuario) throws VerificarNulo, ElementoRepetido {
        if (usuario == null) {
            throw new VerificarNulo("El usuario no puede ser nulo.");
        }
        if (usuarios.containsKey(usuario.getDni())) {
            throw new ElementoRepetido("Usuario con DNI " + usuario.getDni() + " ya existe.");
        }
        // Si pasa las validaciones, simplemente lo agrega. No devuelve nada.
        usuarios.put(usuario.getDni(), usuario);
    }

    public T buscarUsuarioPorDni(String dni) {
        return usuarios.get(dni);
    }

    public T eliminarUsuarioPorDni(String dni) {

        return usuarios.remove(dni);
    }

    public int contarUsuarios() {
        return usuarios.size();

    }




}






