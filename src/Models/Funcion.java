package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime; // <-- Importado
import java.util.ArrayList;
import java.util.Objects; // <-- Importado
import java.util.UUID;    // <-- Importado

public class Funcion implements ItoJson, IIdentificable {
    private String id;
    private String idPelicula;
    private String idSala;
    private LocalDateTime horario;
    private ArrayList<Integer> asientosOcupados;
    private int capacidadTotal;

    public Funcion(String idPelicula, String idSala, LocalDateTime fechaHora, int capacidadTotal) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.idPelicula = idPelicula;
        this.idSala = idSala;
        this.horario = fechaHora;
        this.capacidadTotal = capacidadTotal;
        this.asientosOcupados = new ArrayList<>(); // Inicia vacío
    }

    // Constructor vacío para JSON
    public Funcion() {
        this.asientosOcupados = new ArrayList<>();
    }

    // --- Getters ---
    @Override
    public String getId() {
        return id;
    }

    public String getIdPelicula() {
        return idPelicula;
    }

    public String getIdSala() {
        return idSala;
    }

    public LocalDateTime getHorario() {
        return horario;
    }



    public int getAsientosDisponibles() {
        return capacidadTotal - asientosOcupados.size();
    }

    // --- MÉTODOS DE LÓGICA (para el Gestor) ---
    public boolean isAsientoOcupado(int numAsiento) {
        return asientosOcupados.contains(numAsiento);
    }

    public void ocuparAsiento(int numAsiento) {
        this.asientosOcupados.add(numAsiento);
    }

    // --- Setters (para traerDesdeJson) ---
    public void setId(String id) {
        this.id = id;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public void setAsientosOcupados(ArrayList<Integer> asientosOcupados) {
        this.asientosOcupados = asientosOcupados;
    }

    public void setCapacidadTotal(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
    }

    // --- Equals y HashCode (necesarios para el Repositorio) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcion funcion = (Funcion) o;
        return Objects.equals(id, funcion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // --- JSON (Ahora guarda la lista de asientos) ---
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            // --- Campos que faltaban ---
            json.put("id", this.id);
            json.put("idPelicula", this.idPelicula);
            json.put("idSala", this.idSala);
            json.put("horario", this.horario.toString());
            // ---
            json.put("capacidadTotal", capacidadTotal);
            json.put("asientosOcupados", new JSONArray(this.asientosOcupados));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Funcion traerDesdeJson(JSONObject obj) {
        Funcion f = new Funcion();
        try {
            // --- Campos que faltaban ---
            f.setId(obj.getString("id"));
            f.setIdPelicula(obj.getString("idPelicula"));
            f.setIdSala(obj.getString("idSala"));
            f.setHorario(LocalDateTime.parse(obj.getString("horario")));
            // ---
            f.setCapacidadTotal(obj.getInt("capacidadTotal"));

            // Reconstruimos el ArrayList<Integer> desde el JSONArray
            JSONArray asientosJSON = obj.getJSONArray("asientosOcupados");
            ArrayList<Integer> asientos = new ArrayList<>();
            for (int i = 0; i < asientosJSON.length(); i++) {
                asientos.add(asientosJSON.getInt(i));
            }
            f.setAsientosOcupados(asientos);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return f;
    }

}