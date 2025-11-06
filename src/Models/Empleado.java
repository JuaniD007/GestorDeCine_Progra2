package Models;
import Enum.EnumDepartamento;

public class Empleado extends Persona{
    private int idEmpleado;
    private double SalarioEmpleado;
    private EnumDepartamento enumDepartamento;

    public Empleado(String nombre, String dni, String edad,EnumDepartamento enumDepartamento, int idEmpleado, double salarioEmpleado) {
        super(nombre, dni, edad);
        this.enumDepartamento = enumDepartamento;
        this.idEmpleado = idEmpleado;
        SalarioEmpleado = salarioEmpleado;
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
}
