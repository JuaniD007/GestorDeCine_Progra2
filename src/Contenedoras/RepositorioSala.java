package Contenedoras;
import Excepciones.*;
import Models.Sala;
import org.json.JSONArray;

import java.util.ArrayList;

public class RepositorioSala {
    private GestionDeRepositorio<Sala> elementosSala;

    public RepositorioSala() {
        this.elementosSala = new GestionDeRepositorio<>();
    }

    public void agregarSala(Sala s) throws VerificarNulo, ElementoRepetido {
        elementosSala.agregarElemento(s);
    }

    public void eliminarSala(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        elementosSala.eliminarElemento(id);
    }

    public Sala buscarSala(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return elementosSala.buscarElemento(id);
    }

    public ArrayList<Sala> getListaSalas() {
        return elementosSala.getElementos();
    }

    // Método para que el Gestor guarde en JSON
    public JSONArray ArregloDeSalas() {
        JSONArray jsonArray = new JSONArray();
        for (Sala s : elementosSala.getElementos()) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}
