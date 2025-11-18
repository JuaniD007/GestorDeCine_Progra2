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
    public RepositorioUsuario() {

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


    /**
     * Busca un usuario por su email.
     * Devuelve el usuario (T) si lo encuentra, o null si no.
     */
    public T buscarUsuarioPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }

        for (T usuario : getUsuarios().values()) {
            if (usuario.getEmail().equalsIgnoreCase(email.trim())) {
                return usuario; // ¡Encontrado!
            }
        }

        return null; // No encontrado
    }

}






