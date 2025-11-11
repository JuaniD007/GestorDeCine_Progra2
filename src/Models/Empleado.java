package Models;
import Enum.EnumDepartamento;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

public class Empleado extends Usuario implements ItoJson {

    private double SalarioEmpleado;
    private EnumDepartamento enumDepartamento;


    public Empleado(String nombre, String dni, int edad, String email) {
        super(nombre, dni, edad, email);
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
                ", SalarioEmpleado=" + SalarioEmpleado +
                ", enumDepartamento=" + enumDepartamento +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject j = super.toJson();
        try {
            j.put("salarioEmpleado", this.SalarioEmpleado);
            j.put("departamento", getEnumDepartamento().name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }
    public static Empleado traerDesdeJson(JSONObject o) {

        // 1. Crea el Empleado vacío
        Empleado e = new Empleado();

        // 2. Llama al 'helper' ESTÁTICO de Usuario
        //    Le pasamos el Empleado 'e' para que rellene
        //    los campos base (id, nombre, etc.)
        Usuario.traerDesdeJson(e, o);

        // 3. Rellena los campos propios de Empleado
        try {
            e.setSalarioEmpleado(o.getDouble("salarioEmpleado"));

            String deptoString = o.getString("departamento");
            e.setEnumDepartamento(EnumDepartamento.valueOf(deptoString)); // Asumo Enum

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        // 4. Retorna el Empleado completo
        return e;
    }
}

