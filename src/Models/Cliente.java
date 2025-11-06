package Models;

import Interfaces.Reserva;
import java.util.ArrayList;

public class Cliente {
    private int idCliente;
    private int puntosPorCompraVisita;
    private ArrayList<Reserva> reservaArrayList;

    public Cliente(int idCliente, int puntosPorCompraVisita, ArrayList<Reserva> reservaArrayList) {
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
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", puntosPorCompraVisita=" + puntosPorCompraVisita +
                ", reservaArrayList=" + reservaArrayList +
                '}';
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
