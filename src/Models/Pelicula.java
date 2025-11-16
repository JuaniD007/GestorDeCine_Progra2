package Models;
import Enum.Genero;
import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

public class Pelicula implements ItoJson, IIdentificable {

    private String id;
    private  String titulo;
    private Genero genero;
    private int duracion;
    private double precioBase;

    public Pelicula(int duracion, Genero genero, double precioBase, String titulo) {
        this.duracion = duracion;
        this.genero = genero;
        this.id= UUID.randomUUID().toString().substring(0, 8); // Un ID corto;
        this.precioBase = precioBase;
        this.titulo = titulo;
    }
   public Pelicula(){}
    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pelicula pelicula)) return false;
        return Objects.equals(id, pelicula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        String tituloStr = (this.titulo != null) ? this.titulo : "Sin Título";
        String generoStr = (this.genero != null) ? this.genero.name() : "Sin Género";
        String idStr = (this.id != null) ? this.id : "NO-ID";

        // Formato: [ID: abc-123]   "Título de la Película" (Género) - 120 min.
        return String.format("[ID: %s] \t\"%s\" (%s) - %d min.",
                idStr,
                tituloStr,
                generoStr,
                this.duracion
        );
    }

    public JSONObject toJson (){
        JSONObject j = new JSONObject();
        try {
            j.put("titulo", this.titulo);
            j.put("genero", this.genero.name());
            j.put("duracion", this.duracion);
            j.put("idPelicula", this.id);
            j.put("precioBase", this.precioBase);

        } catch (JSONException e) {
               e.printStackTrace();
        }
        return j ;
    }

    public static Pelicula traerDesdeJson (JSONObject o ){
        Pelicula p = new Pelicula();
        try{
            p.setPrecioBase(o.getDouble("precioBase"));
            p.setDuracion(o.getInt("duracion"));
            p.setId(o.getString("idPelicula"));
            p.setTitulo(o.getString("titulo"));
            if (o.has("genero")) { // si json tiene una clave llamada genero /o.has("genero") devuelve true.
                String generoStr = o.getString("genero").toUpperCase(); // Toma el valor de la clave "genero" como texto, y lo pasa a mayúsculas.
                p.setGenero(Genero.valueOf(generoStr));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }


}
