package Models;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class Funcion {
    private Pelicula pelicula;
    private Sala sala;
    private LocalDate horario;
    private HashMap <Integer, Cliente> butacaOcupadas;

    public Funcion(HashMap<Integer, Cliente> butacaOcupadas, LocalDate horario, Pelicula pelicula, Sala sala) {
        this.butacaOcupadas = butacaOcupadas;
        this.horario = horario;
        this.pelicula = pelicula;
        this.sala = sala;
    }

    public HashMap<Integer, Cliente> isButacaOcupadas() {
        return butacaOcupadas;
    }

    public void setButacaOcupadas(HashMap<Integer, Cliente> butacaOcupadas) {
        this.butacaOcupadas = butacaOcupadas;
    }

    public LocalDate getHorario() {
        return horario;
    }

    public void setHorario(LocalDate horario) {
        this.horario = horario;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "butacaOcupadas=" + butacaOcupadas +
                ", pelicula=" + pelicula +
                ", sala=" + sala +
                ", horario=" + horario +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Funcion funcion)) return false;
        return Objects.equals(horario, funcion.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(horario);
    }

    public boolean ButacaOcupada ( int numButaca){
        return butacaOcupadas.containsKey(numButaca);
    }
    public boolean ocuparButaca(int nroButaca, Cliente cliente) {
        if (!ButacaOcupada(nroButaca)) { // si no esta ocupada...
            butacaOcupadas.put(nroButaca, cliente);
            return true;
        }
        return false; // ya estaba ocupada
    }
    public int butacasDisponibles() {
        int total = sala.getCapacidadTotal();
        int ocupadas = butacaOcupadas.size();
        return total - ocupadas;
    }
    public String mostrarDetalleFuncion() {
        return ("🎬 FUNCION DETALLE 🎬")+
        ("Pelicula: " + (pelicula != null ? pelicula.getTitulo() : "No asignada"))+
        ("Sala: " + (sala != null ? sala.getNumSala() : "No asignada"))+
      ("Horario: " + horario)+
      ("Butacas ocupadas: " + butacaOcupadas.size())+
      ("Butacas disponibles: " + butacasDisponibles());
    }
}
