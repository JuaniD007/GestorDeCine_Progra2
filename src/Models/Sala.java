package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Objects;
import java.util.UUID;

public class Sala implements ItoJson, IIdentificable {

    private String id;
    private int numSala;
    private int capacidadTotal;
    private boolean es3D;

    /**
     * Constructor para crear una sala nueva.
     * El ID se genera automáticamente.
     */
    public Sala(int numSala, int capacidadTotal, boolean es3D) {
        this.id = UUID.randomUUID().toString().substring(0, 8); // Autogenera ID
        this.numSala = numSala;
        this.capacidadTotal = capacidadTotal;
        this.es3D = es3D;
    }

    /**
     * Constructor vacío, usado para cargar desde JSON.
     */
    public Sala() {}

    // --- Getters ---
    @Override
    public String getId() { return this.id; }
    public int getNumSala() { return numSala; }
    public int getCapacidadTotal() { return capacidadTotal; }
    public boolean isEs3D() { return es3D; }

    // --- Setters (Usados por traerDesdeJson) ---
    public void setId(String id) { this.id = id; }
    public void setNumSala(int numSala) { this.numSala = numSala; }
    public void setCapacidadTotal(int capacidadTotal) { this.capacidadTotal = capacidadTotal; }
    public void setEs3D(boolean es3D) { this.es3D = es3D; }


    // --- Equals / HashCode (Basado en ID) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return Objects.equals(id, sala.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Sala " + numSala + " (" + (es3D ? "3D" : "2D") + ", Cap: " + capacidadTotal + ")";
    }

    // --- Persistencia (JSON) ---
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("numSala", numSala);
            json.put("capacidadTotal", capacidadTotal);
            json.put("es3D", es3D);
        } catch (JSONException e) { e.printStackTrace(); }
        return json;
    }

    public static Sala traerDesdeJson(JSONObject obj) {
        Sala s = new Sala();
        try {
            s.setId(obj.getString("id"));
            s.setNumSala(obj.getInt("numSala"));
            s.setCapacidadTotal(obj.getInt("capacidadTotal"));
            s.setEs3D(obj.getBoolean("es3D"));
        } catch (JSONException e) { e.printStackTrace(); }
        return s;
    }
}