package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Pelicula;
import org.json.JSONArray;

import java.util.ArrayList;

public class RepositorioPelicula {
    private GestionDeRepositorio<Pelicula> peliculaGestionDeRepositorio;
    public RepositorioPelicula() {
        this.peliculaGestionDeRepositorio = new GestionDeRepositorio<>();

    }

    public boolean agregarPelicula ( Pelicula p ) throws VerificarNulo, ElementoRepetido {
        return this.peliculaGestionDeRepositorio.agregarElemento(p);
    }

    public boolean EliminarPelicula ( String id) throws  VerificarNulo, ElementoRepetido, ElementoNoExiste {
        return peliculaGestionDeRepositorio.eliminarElemento(id);
    }

    public Pelicula buscarElemento(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido{
        return peliculaGestionDeRepositorio.buscarElemento(id);
    }

    public ArrayList<Pelicula> getListaPeliculas() {
        return this.peliculaGestionDeRepositorio.getElementos();



    }
    public JSONArray arregloDePeliculasJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pelicula pelicula : this.peliculaGestionDeRepositorio.getElementos()) { // Recorre una por una todas las películas que están guardadas dentro de tu GestionDeRepositorio.
            jsonArray.put(pelicula.toJson());
        }

        return jsonArray; // devuelve arreglo JSON
    }

}
