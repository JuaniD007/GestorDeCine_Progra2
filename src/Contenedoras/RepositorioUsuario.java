package Contenedoras;

import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Interfaces.ItoJson;
import Models.Usuario;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RepositorioUsuario <T extends Usuario>   {
    private HashMap<String, T> usuarios = new HashMap<>();

    public boolean verificarUsuario (T usuario) throws VerificarNulo, ElementoRepetido {
        if (usuario == null) {
            throw new VerificarNulo("El usuario no puede ser nulo.");
        }

        if (usuarios.containsKey(usuario.getDni())) {
            throw new ElementoRepetido("Usuario con DNI " + usuario.getDni() + " ya existe.");
        }

        usuarios.put(usuario.getDni(), usuario);
        return true;
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






