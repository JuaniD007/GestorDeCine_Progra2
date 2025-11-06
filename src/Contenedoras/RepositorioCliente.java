package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Cliente;
import org.json.JSONArray;

public class RepositorioCliente {


    private GestionDeElementos<Cliente> clientesGestionDeElementos;
    private JSONArray j;
    public RepositorioCliente() {
        this.clientesGestionDeElementos = new GestionDeElementos<>();
        this.j=new JSONArray();

    }

    public boolean agregar(Cliente cliente) throws VerificarNulo, ElementoRepetido
    {

        return this.clientesGestionDeElementos.agregarElemento(cliente);
    }

    public Cliente buscar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.clientesGestionDeElementos.buscarElemento(id);
    }

    public boolean eliminar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.clientesGestionDeElementos.eliminarElemento(id);
    }
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Cliente cliente : this.clientesGestionDeElementos.getElementos()) { // Recorre una por una todas las películas que están guardadas dentro de tu GestionDeElementos.
            jsonArray.put(cliente.toJson());
        }

        return jsonArray; // devuelve arreglo JSON
    }

}