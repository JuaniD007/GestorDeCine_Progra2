package Contenedoras;

import Models.Funcion;
import Excepciones.*;
import org.json.JSONArray;
import java.util.ArrayList;

public class RepositorioFuncion {
    private GestionDeRepositorio<Funcion> elementosFuncion;

    public RepositorioFuncion() {
        this.elementosFuncion = new GestionDeRepositorio<>();
    }

    public void agregarFuncion(Funcion f) throws VerificarNulo, ElementoRepetido {
        elementosFuncion.agregarElemento(f);
    }

    public void eliminarFuncion(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        elementosFuncion.eliminarElemento(id);
    }

    public Funcion buscarFuncion(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return elementosFuncion.buscarElemento(id);
    }

    public ArrayList<Funcion> getListaFunciones() {
        return elementosFuncion.getElementos();
    }

    // Método para que el Gestor guarde en JSON
    public JSONArray ArregloDeFunciones() {
        JSONArray jsonArray = new JSONArray();
        for (Funcion f : elementosFuncion.getElementos()) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}