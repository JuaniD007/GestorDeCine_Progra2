package Models;

import Contenedoras.RepositorioCliente;
import Contenedoras.RepositorioPelicula;

import java.util.HashMap;

public class Cine
{
    private String nombreCine;
    private RepositorioCliente cliente;
    private RepositorioPelicula repositorioPelicula;
    private HashMap<Integer, Sala> funcionesPorSala;

    public Cine(RepositorioCliente cliente, String nombreCine, RepositorioPelicula repositorioPelicula) {
        this.cliente = cliente;
        this.funcionesPorSala = new HashMap<>();
        this.nombreCine = nombreCine;
        this.repositorioPelicula = repositorioPelicula;
    }

    public RepositorioCliente getCliente() {
        return cliente;
    }

    public void setCliente(RepositorioCliente cliente) {
        this.cliente = cliente;
    }

    public HashMap<Integer, Sala> getFuncionesPorSala() {
        return funcionesPorSala;
    }

    public void setFuncionesPorSala(HashMap<Integer, Sala> funcionesPorSala) {
        this.funcionesPorSala = funcionesPorSala;
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

    public RepositorioPelicula getRepositorioPelicula() {
        return repositorioPelicula;
    }

    public void setRepositorioPelicula(RepositorioPelicula repositorioPelicula) {
        this.repositorioPelicula = repositorioPelicula;
    }

    @Override
    public String toString() {
        return "Cine{" +
                "cliente=" + cliente +
                ", nombreCine='" + nombreCine + '\'' +
                ", repositorioPelicula=" + repositorioPelicula +
                ", funcionesPorSala=" + funcionesPorSala +
                '}';
    }
}
