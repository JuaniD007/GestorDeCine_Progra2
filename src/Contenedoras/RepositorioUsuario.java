package Contenedoras;

import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Usuario;

import java.util.HashMap;

public class RepositorioUsuario <T extends Usuario> {
    private HashMap<String, T> usuarios = new HashMap<>();

    //Respositorio para hacer verificaciones de los usuarios

    public boolean verificarUsuario (T usuario) throws VerificarNulo, ElementoRepetido {
        if (usuario == null) throw new VerificarNulo("El usuario no puede ser nulo");
        if (usuarios.containsKey(usuario.getDni())) throw new ElementoRepetido("Usuario ya existe");

        usuarios.put(usuario.getDni(), usuario);
        return true;
    }



}

