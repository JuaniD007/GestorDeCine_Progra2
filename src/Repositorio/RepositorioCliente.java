package Repositorio;

import Contenedoras.GestionDeElementos;
import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Cliente;
import org.json.JSONArray;

public class RepositorioCliente {


    private GestionDeElementos<Cliente> clientes;
    private JSONArray j;
    public RepositorioCliente() {
        this.clientes = new GestionDeElementos<>();
        this.j=new JSONArray();

    }

    public boolean agregar(Cliente cliente) throws VerificarNulo, ElementoRepetido
    {

        return this.clientes.agregarElemento(cliente);
    }

    public Cliente buscar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.clientes.buscarElemento(id);
    }

    public boolean eliminar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.clientes.eliminarElemento(id);
    }

}