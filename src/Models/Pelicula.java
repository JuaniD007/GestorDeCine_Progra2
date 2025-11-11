package Models;
import Enum.Genero;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Pelicula implements ItoJson {

    private String idPelicula;
    private  String titulo;
    private Genero genero;
    private int duracion;
    private double precioBase;

    public Pelicula(int duracion, Genero genero, String idPelicula, double precioBase, String titulo) {
        this.duracion = duracion;
        this.genero = genero;
        this.idPelicula = idPelicula;
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

    public String getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(String idPelicula) {
        this.idPelicula = idPelicula;
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
        return idPelicula == pelicula.idPelicula;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPelicula);
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "duracion=" + duracion +
                ", idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", precioBase=" + precioBase +
                '}';
    }

    public JSONObject toJson (){
        JSONObject j = new JSONObject();
        try {
            j.put("titulo", this.titulo);
            j.put("genero", this.genero);
            j.put("duracion", this.duracion);
            j.put("idpelicula", this.idPelicula);
            j.put("precio base", this.precioBase);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return j ;
    }

    public static Pelicula traerDesdeJson (JSONObject o ){
        Pelicula p = new Pelicula();
        try{
            p.setPrecioBase(o.getDouble("precio base"));
            p.setDuracion(o.getInt("duracion"));
            p.setIdPelicula(o.getString("idPelicula"));
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
