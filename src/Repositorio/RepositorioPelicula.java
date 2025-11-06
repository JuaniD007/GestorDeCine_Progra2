package Repositorio;

import Contenedoras.GestionDeElementos;
import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Pelicula;
import org.json.JSONArray;

public class RepositorioPelicula {
    private GestionDeElementos<Pelicula>peliculaGestionDeElementos;
    private JSONArray j;
    public RepositorioPelicula() {
        this.peliculaGestionDeElementos = new GestionDeElementos<>();
        this.j = new JSONArray();
    }

    public boolean agregarPelicula ( Pelicula p ) throws VerificarNulo, ElementoRepetido {
        return this.peliculaGestionDeElementos.agregarElemento(p);
    }

    public boolean EliminarPelicula ( int id) throws  VerificarNulo, ElementoRepetido, ElementoNoExiste {
        return peliculaGestionDeElementos.eliminarElemento(id);
    }
    public Pelicula buscarElemento(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido{
        return peliculaGestionDeElementos.buscarElemento(id);
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pelicula pelicula : this.peliculaGestionDeElementos.getElementos()) { // Recorre una por una todas las películas que están guardadas dentro de tu GestionDeElementos.
            jsonArray.put(pelicula.toJson());
        }

        return jsonArray; // devuelve arreglo JSON
    }
    
}
