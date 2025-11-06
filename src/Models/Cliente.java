package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente  extends Persona{
    private int idCliente;
    private int puntosPorCompraVisita;
    private ArrayList<Reserva> reservaArrayList;

    public Cliente(String nombre, String dni, String edad,int idCliente, int puntosPorCompraVisita, ArrayList<Reserva> reservaArrayList) {
        super(nombre, dni, edad);
        this.idCliente = idCliente;
        this.puntosPorCompraVisita = puntosPorCompraVisita;
        this.reservaArrayList = reservaArrayList;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getPuntosPorCompraVisita() {
        return puntosPorCompraVisita;
    }

    public void setPuntosPorCompraVisita(int puntosPorCompraVisita) {
        this.puntosPorCompraVisita = puntosPorCompraVisita;
    }

    public ArrayList<Reserva> getReservaArrayList() {
        return reservaArrayList;
    }

    public void setReservaArrayList(ArrayList<Reserva> reservaArrayList) {
        this.reservaArrayList = reservaArrayList;
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() +
                "idCliente=" + idCliente +
                ", puntosPorCompraVisita=" + puntosPorCompraVisita +
                ", reservaArrayList=" + reservaArrayList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        if (!super.equals(o)) return false;
        return idCliente == cliente.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCliente);
    }

    public boolean agregarReserva (Reserva r){
        return reservaArrayList.add(r);
    }

    public int sumarPuntos(int puntos) {
        if (puntos > 0) {
            puntosPorCompraVisita += puntos;
        }
        return puntosPorCompraVisita;
    }
        
}
