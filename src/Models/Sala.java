package Models;
import Excepciones.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala
{
    private int numSala;
    private int capacidadTotal;
    private boolean es3D;
    private ArrayList <Funcion> funcionesDelDia;


    public Sala(int capacidadTotal, boolean es3D,  int numSala) {
        this.capacidadTotal = capacidadTotal;
        this.es3D = es3D;
        this.funcionesDelDia = new ArrayList<>();
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

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }

    public boolean agregarFuncion(Funcion nuevaFuncion) throws VerificarNulo, FuncionSuperpuesta {
        // Validar que la función y su sala no sean nulas
        if (nuevaFuncion == null) {
            throw new VerificarNulo("La función no puede ser nula.");
        }
        if (nuevaFuncion.getSala() == null) {
            throw new VerificarNulo("La función debe tener una sala asignada.");
        }

        // Verificar que la función pertenece a esta misma sala
        if (nuevaFuncion.getSala().getNumSala() != this.numSala) {
            throw new VerificarNulo("La función pertenece a otra sala ("
                    + nuevaFuncion.getSala().getNumSala() + ").");
        }

        // Verificar si ya hay una función en ese mismo horario
        for (Funcion existente : funcionesDelDia) {
            if (existente.getHorario().equals(nuevaFuncion.getHorario())) {
                throw new FuncionSuperpuesta("Ya existe una función programada a las " + nuevaFuncion.getHorario() + " en la sala " + numSala + ".");
            }
        }

        // Si pasa todas las validaciones, agregar la función
        funcionesDelDia.add(nuevaFuncion);
        return true;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "capacidadTotal=" + capacidadTotal +
                ", numSala=" + numSala +
                ", es3D=" + es3D +
                ", funcionesDelDia=" + funcionesDelDia +
                '}';
    }
}
