package Models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala
{
    private int numSala;
    private int capacidadTotal;
    private boolean es3D;
    private String  idFuncion;
    private ArrayList <Funcion> funcionesDelDia;


    public Sala(int capacidadTotal, boolean es3D, String idFuncion, int numSala) {
        this.capacidadTotal = capacidadTotal;
        this.es3D = es3D;
        this.funcionesDelDia = new ArrayList<>();
        this.idFuncion = idFuncion;
        this.numSala = numSala;
    }
    public Sala (){}
    public int getCapacidadTotal() {
        return capacidadTotal;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    public boolean isEs3D() {
        return es3D;
    }

    public void setEs3D(boolean es3D) {
        this.es3D = es3D;
    }

    public ArrayList<Funcion> getFuncionesDelDia() {
        return funcionesDelDia;
    }

    public void setFuncionesDelDia(ArrayList<Funcion> funcionesDelDia) {
        this.funcionesDelDia = funcionesDelDia;
    }

    public String getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(String idFuncion) {
        this.idFuncion = idFuncion;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public boolean AgregarFuncion (Funcion f ){
        return funcionesDelDia.add(f);
    }
    public Funcion buscarFuncion(LocalDateTime horario){
        for ( Funcion f : funcionesDelDia){
            if(f.getHorario().equals(horario)){
                return f;
            }
        }
        return null;  // no lo encontroo
    }


    public ArrayList<Funcion> buscarFuncionDelDia(LocalDate dia) {
        ArrayList<Funcion> funcionesEncontradas = new ArrayList<>();

        for (Funcion f : funcionesDelDia) {
            if (f.getHorario().equals(dia)) {
                funcionesEncontradas.add(f);
            }
        }
        return funcionesEncontradas; // devuelve la todas funciones del dia
    }

    @Override
    public String toString() {
        return "Sala{" +
                "capacidadTotal=" + capacidadTotal +
                ", numSala=" + numSala +
                ", es3D=" + es3D +
                ", idFuncion='" + idFuncion + '\'' +
                ", funcionesDelDia=" + funcionesDelDia +
                '}';
    }
}
