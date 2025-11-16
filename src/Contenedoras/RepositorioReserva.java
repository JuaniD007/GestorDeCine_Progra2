package Contenedoras;

import Models.Reserva;
import Excepciones.*;
import org.json.JSONArray;
import java.util.ArrayList;

public class RepositorioReserva {

    // Usa la GestionDeRepositorio genérica que ya hicimos
    private GestionDeRepositorio<Reserva> elementosReserva;

    public RepositorioReserva() {
        this.elementosReserva = new GestionDeRepositorio<>();
    }

    public void agregarReserva(Reserva r) throws VerificarNulo, ElementoRepetido {
        elementosReserva.agregarElemento(r);
    }

    public void eliminarReserva(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        elementosReserva.eliminarElemento(id);
    }

    public Reserva buscarReserva(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return elementosReserva.buscarElemento(id);
    }

    public ArrayList<Reserva> getListaReservas() {
        return elementosReserva.getElementos();
    }

    // Método para que el Gestor guarde en JSON
    public JSONArray ArregloDeReservas() {
        JSONArray jsonArray = new JSONArray();
        for (Reserva r : elementosReserva.getElementos()) {
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}