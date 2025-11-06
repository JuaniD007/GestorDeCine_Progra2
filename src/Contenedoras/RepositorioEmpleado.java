package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Empleado;
import org.json.JSONArray;

public class RepositorioEmpleado
{
    private GestionDeElementos<Empleado> empleadosGestionDeElementos;
    private JSONArray j;
    public RepositorioEmpleado() {
        this.empleadosGestionDeElementos = new GestionDeElementos<>();
        this.j=new JSONArray();

    }

    public boolean agregar(Empleado empleado) throws VerificarNulo, ElementoRepetido
    {

        return this.empleadosGestionDeElementos.agregarElemento(empleado);
    }

    public Empleado buscar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.empleadosGestionDeElementos.buscarElemento(id);
    }

    public boolean eliminar(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return this.empleadosGestionDeElementos.eliminarElemento(id);
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Empleado empleado : this.empleadosGestionDeElementos.getElementos()) { // Recorre una por una todas las películas que están guardadas dentro de tu GestionDeElementos.
            jsonArray.put(empleado.toJson());
        }

        return jsonArray; // devuelve arreglo JSON
    }










}
