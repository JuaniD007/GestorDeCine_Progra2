package Models;
import Enum.Genero;
public class Pelicula {

    private int idPelicula;
    private  String titulo;
    private Genero genero;
    private int duracion;
    private double precioBase;

    public Pelicula(int duracion, Genero genero, int idPelicula, double precioBase, String titulo) {
        this.duracion = duracion;
        this.genero = genero;
        this.idPelicula = idPelicula;
        this.precioBase = precioBase;
        this.titulo = titulo;
    }

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

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
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
    public String toString() {
        return "Pelicula{" +
                "duracion=" + duracion +
                ", idPelicula=" + idPelicula +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", precioBase=" + precioBase +
                '}';
    }
}
