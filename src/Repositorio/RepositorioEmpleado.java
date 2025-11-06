package Repositorio;

import Contenedoras.GestionDeElementos;
import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Cliente;
import Models.Empleado;
import org.json.JSONArray;

public class RepositorioEmpleado
{
    private GestionDeElementos<Empleado> empleados;
    private JSONArray j;
    public RepositorioEmpleado() {
        this.empleados = new GestionDeElementos<>();
        this.j=new JSONArray();

    }

    public boolean agregar(Empleado empleado) throws VerificarNulo, ElementoRepetido
    {

        return this.empleados.agregarElemento(empleado);
    }

    public Empleado buscar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.empleados.buscarElemento(id);
    }

    public boolean eliminar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.empleados.eliminarElemento(id);
    }










}
