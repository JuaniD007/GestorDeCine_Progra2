package Contenedoras;

import Models.Pelicula;
import ModelsJson.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import Enum.Genero;
import Excepciones.*;
import Models.*;

import java.util.ArrayList;

public class GestorDePelicula {

    private RepositorioPelicula repoPelicula;
    private static final String ARCHIVO_PELICULAS = "peliculas.json";

    // --- CONSTRUCTOR (Carga JSON) ---
    public GestorDePelicula() {
        this.repoPelicula = new RepositorioPelicula();

        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_PELICULAS);

        if (tokener != null) {
            try {
                JSONArray peliculasJson = new JSONArray(tokener);
                cargarPeliculasDesdeJson(peliculasJson);

            }
            catch (JSONException e) {e.printStackTrace();
            }
        }
    }

    private void cargarPeliculasDesdeJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                Pelicula pelicula = Pelicula.traerDesdeJson(obj);
                repoPelicula.agregarPelicula(pelicula);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    /**
     * Guarda el estado actual del repositorio en el JSON.
     * (Este método no cambia)
     */
    public void guardarDatos() {
        JSONArray arrayParaGuardar = repoPelicula.ArregloDePeliculas();
        JsonUtiles.grabarUnJson(arrayParaGuardar, ARCHIVO_PELICULAS);
    }

    // --- MÉTODOS DE LÓGICA (Ahora lanzan excepciones) ---

    /**
     * Valida y crea una nueva película, persistiendo los cambios.
     * Lanza excepciones si la validación o el repositorio fallan.
     */
    public void crearPelicula(String titulo, int duracionMinutos, Genero genero, int duracion, double precioBase, String id)
            throws ValidacionException, ElementoRepetido, VerificarNulo {

        // 1. Validación (lanza excepción si falla)
        if (!Validaciones.isStringValido(titulo)) {
            throw new ValidacionException("Error: El título no puede estar vacío.");
        }
        if (duracionMinutos <= 0) {
            throw new ValidacionException("Error: La duración debe ser mayor a 0.");
        }

        // 2. Creación del objeto
        Pelicula nuevaPelicula = new Pelicula(duracion,genero,id, precioBase,titulo);
        // 3. Habla con el Repositorio (deja que sus excepciones se propaguen)
        repoPelicula.agregarPelicula(nuevaPelicula);

        // 4. Persiste (solo si todo lo anterior tuvo éxito)
        guardarDatos();
    }

    /**
     * Elimina una película por su ID y persiste los cambios.
     * Lanza excepciones si el repositorio falla.
     */
    public void eliminarPelicula(String id)
            throws ElementoNoExiste, VerificarNulo, ElementoRepetido {

        repoPelicula.EliminarPelicula(id);
        guardarDatos();
    }

    /**
     * Busca una película por su ID.
     * Devuelve el objeto Pelicula o lanza una excepción si no lo encuentra.
     */
    public Pelicula buscarPelicula(String id)
            throws ElementoNoExiste, VerificarNulo, ElementoRepetido {

        // Simplemente llama al repositorio y deja que maneje las excepciones
        return repoPelicula.buscarElemento(id);
    }

    /**
     * Devuelve la lista de objetos Pelicula para que el Menú la muestre.
     * (Este no lanza excepciones, solo devuelve la lista, vacía o no).
     */
    public ArrayList<Pelicula> getListaPeliculas() {
        return repoPelicula.getListaPeliculas();
    }
}
