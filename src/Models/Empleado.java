package Models;
import Enum.EnumDepartamento;
import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Empleado extends Persona implements ItoJson {
    private int idEmpleado;
    private double SalarioEmpleado;
    private EnumDepartamento enumDepartamento;

    public Empleado(String nombre, String dni, int edad,EnumDepartamento enumDepartamento, int idEmpleado, double salarioEmpleado) {
        super(nombre, dni, edad);
        this.enumDepartamento = enumDepartamento;
        this.idEmpleado = idEmpleado;
        SalarioEmpleado = salarioEmpleado;
    }
    public Empleado (){
        super("", "", 0);
    }
    public EnumDepartamento getEnumDepartamento() {
        return enumDepartamento;
    }

    public void setEnumDepartamento(EnumDepartamento enumDepartamento) {
        this.enumDepartamento = enumDepartamento;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getSalarioEmpleado() {
        return SalarioEmpleado;
    }

    public void setSalarioEmpleado(double salarioEmpleado) {
        SalarioEmpleado = salarioEmpleado;
    }

    @Override
    public String toString() {
        return "Empleado{"+ super.toString() +
                "enumDepartamento=" + enumDepartamento +
                ", idEmpleado=" + idEmpleado +
                ", SalarioEmpleado=" + SalarioEmpleado +
                '}';
    }
    public JSONObject toJson (){
        JSONObject j = new JSONObject();
        try {
            j.put("salario empleado", this.SalarioEmpleado);
            j.put("id Empleado", this.idEmpleado);
            j.put("departamento", this.enumDepartamento);
            j.put("dni", this.dni);
            j.put("nombre", this.nombre);
            j.put("edad", this.edad);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return j ;
    }

    public static Empleado traerJSon (JSONObject o ){
        Empleado p = new Empleado();
        try{
            p.setIdEmpleado(o.getInt("id Empleado"));
            p.setSalarioEmpleado(o.getDouble("salario Empleado"));
            p.setDni(o.getString("dni"));
            p.setEdad(o.getInt("edad"));
            p.setNombre(o.getString("nombre"));

            if (o.has("departamento")) { // si json tiene una clave llamada genero /o.has("genero") devuelve true.
                String departamentostr = o.getString("departamento").toUpperCase(); // Toma el valor de la clave "genero" como texto, y lo pasa a mayúsculas.
                p.setEnumDepartamento(EnumDepartamento.valueOf(departamentostr));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
