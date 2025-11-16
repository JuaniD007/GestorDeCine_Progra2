package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Pelicula;
import org.json.JSONArray;

import java.util.ArrayList;

public class RepositorioPelicula {
    private GestionDeRepositorio<Pelicula> peliculaGestionDeElementos;
    private JSONArray j;
    public RepositorioPelicula() {
        this.peliculaGestionDeElementos = new GestionDeRepositorio<>();
        this.j = new JSONArray();
    }

    public boolean agregarPelicula ( Pelicula p ) throws VerificarNulo, ElementoRepetido {
        return this.peliculaGestionDeElementos.agregarElemento(p);
    }

    public boolean EliminarPelicula ( String id) throws  VerificarNulo, ElementoRepetido, ElementoNoExiste {
        return peliculaGestionDeElementos.eliminarElemento(id);
    }
    public Pelicula buscarElemento(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido{
        return peliculaGestionDeElementos.buscarElemento(id);
    }

    public ArrayList<Pelicula> getListaPeliculas() {
        return this.peliculaGestionDeElementos.getElementos();



    }

    public JSONArray ArregloDePeliculas() {
        JSONArray jsonArray = new JSONArray();

        for (Pelicula pelicula : this.peliculaGestionDeElementos.getElementos()) { // Recorre una por una todas las películas que están guardadas dentro de tu GestionDeRepositorio.
            jsonArray.put(pelicula.toJson());
        }

        return jsonArray; // devuelve arreglo JSON
    }


}
