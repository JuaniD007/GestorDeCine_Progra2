package Models;
import Enum.EnumDepartamento;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

public class Empleado extends Usuario implements ItoJson {
    private int idEmpleado; /// LO SACARIA Y HARIA SOLO ID USUARIO GENERADO AUTOM.
    private double SalarioEmpleado;
    private EnumDepartamento enumDepartamento;


    public Empleado(String nombre, String dni, int edad, String email) {
        super(nombre, dni, edad, email);
        this.idEmpleado = 0;
        this.SalarioEmpleado = 0.0;
        this.enumDepartamento = null; // o EnumDepartamento.DEFAULT
    }


    public Empleado (){
        super("", "", 0,"");
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
        return "Empleado{" +
                super.toString() +
                "idEmpleado=" + idEmpleado +
                ", SalarioEmpleado=" + SalarioEmpleado +
                ", enumDepartamento=" + enumDepartamento +
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
