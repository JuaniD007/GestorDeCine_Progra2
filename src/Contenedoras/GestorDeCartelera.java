package Contenedoras;

import Models.*;
import Excepciones.*;
import Enum.Genero;
import ModelsJson.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;


public class GestorDeCartelera {

    // --- 1. ATRIBUTOS (Maneja 3 repositorios) ---
    private RepositorioPelicula repoPeliculas;
    private RepositorioSala repoSalas;
    private RepositorioFuncion repoFunciones;

    // Nombres de los archivos de persistencia
    private static final String ARCHIVO_PELICULAS = "peliculas.json";
    private static final String ARCHIVO_SALAS = "salas.json";
    private static final String ARCHIVO_FUNCIONES = "funciones.json";

    // --- 2. CONSTRUCTOR (Carga los 3 JSON al iniciar) ---
    public GestorDeCartelera() {
        // Inicializa los "almacenes" de memoria
        this.repoPeliculas = new RepositorioPelicula();
        this.repoSalas = new RepositorioSala();
        this.repoFunciones = new RepositorioFuncion();

        // Carga los datos desde los archivos JSON
        cargarPeliculas();
        cargarSalas();
        cargarFunciones();
    }


    // --- API de PELÍCULAS ---

    /**
     * Valida y crea una nueva película.
     * Lanza excepciones si la validación falla o el título ya existe.
     */
    public void crearPelicula(String titulo, Genero genero, int duracion, double precioBase)
            throws ValidacionException, ElementoRepetido, VerificarNulo {

        // Validaciones de formato
        if (!Validaciones.isStringValido(titulo)) {
            throw new ValidacionException("El título no puede estar vacío.");
        }
        if (duracion <= 0) {
            throw new ValidacionException("La duración debe ser positiva.");
        }

        int minDuracion = 60;
        int maxDuracion = 300;

        // Usamos el validador de rango
        if (!Validaciones.isRangoValido(duracion, minDuracion, maxDuracion)) {
            throw new ValidacionException("Error: La duración debe ser un valor lógico (entre " + minDuracion + " y " + maxDuracion + " minutos).");
        }

        if (buscarPeliculaPorTitulo(titulo) != null) {
            throw new ValidacionException("Ya existe una película con el título: " + titulo);
        }

        Pelicula nueva = new Pelicula(duracion, genero, precioBase, titulo);
        repoPeliculas.agregarPelicula(nueva);
        guardarPeliculas(); // Persiste el cambio en el JSON
    }

    public Pelicula buscarPelicula(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return repoPeliculas.buscarElemento(id);
    }

    public Pelicula buscarPeliculaPorTitulo(String titulo) {
        for (Pelicula p : repoPeliculas.getListaPeliculas()) {
            if (p.getTitulo().equalsIgnoreCase(titulo.trim())) {
                return p;
            }
        }
        return null; // No se encontró
    }

    public void modificarPelicula (String id, String nuevoTitulo, Genero nuevoGenero, int nuevaDuracion, double nuevoPrecio)
            throws ElementoNoExiste, VerificarNulo, ElementoRepetido {

            Pelicula p = repoPeliculas.buscarElemento(id);

            if(nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {

                Pelicula peliAux = buscarPeliculaPorTitulo(nuevoTitulo);

                if(peliAux != null  && !peliAux.getId().equals(id)) {

                    throw new ElementoRepetido("El título " + nuevoTitulo + " ya está usado por otra película.");
                }

                    p.setTitulo(nuevoTitulo);

            }

        if (nuevoGenero != null) {
            p.setGenero(nuevoGenero);
        }


        if (nuevaDuracion > 0) {
            // Podrías validar rango aquí también
            p.setDuracion(nuevaDuracion);
        }


        if (nuevoPrecio > 0) {
            p.setPrecioBase(nuevoPrecio);
        }

        guardarPeliculas();

    }


    public ArrayList<Pelicula> getListaPeliculas() {
        return repoPeliculas.getListaPeliculas();
    }

    public void eliminarPelicula(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        repoPeliculas.EliminarPelicula(id);
        guardarPeliculas();
    }

    // --- API de SALAS ---

    /**
     * Valida y crea una nueva sala.
     * Lanza excepciones si la capacidad es inválida o el número de sala ya existe.
     */
    public void crearSala(int numSala, int capacidad, boolean es3D)
            throws ValidacionException, ElementoRepetido, VerificarNulo {

        if (capacidad <= 0) {
            throw new ValidacionException("La capacidad debe ser positiva.");
        }
        if (buscarSalaPorNumero(numSala) != null) {
            throw new ValidacionException("Ya existe una sala con el número " + numSala);
        }

        Sala nueva = new Sala(numSala, capacidad, es3D);
        repoSalas.agregarSala(nueva);
        guardarSalas(); // Persiste el cambio en el JSON
    }

    public Sala buscarSala(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return repoSalas.buscarSala(id);
    }

    public Sala buscarSalaPorNumero(int numSala) {
        for (Sala s : repoSalas.getListaSalas()) {
            if (s.getNumSala() == numSala) {
                return s;
            }
        }
        return null; // No se encontró
    }

    public ArrayList<Sala> getListaSalas() {
        return repoSalas.getListaSalas();
    }

    public void eliminarSala(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        repoSalas.eliminarSala(id);
        guardarSalas();
    }

    //  API de FUNCIONES (Lógica de negocio principal)

    /**
     * Valida y crea una nueva función.
     * Lanza excepciones si la película/sala no existen o si el horario se superpone.
     */
    public void crearFuncion(String idPelicula, String idSala, LocalDateTime fechaHora)
            throws ValidacionException, ElementoRepetido, VerificarNulo, ElementoNoExiste {

        // --- 1. BUSCAR OBJETOS PRINCIPALES ---
        Pelicula peliculaNueva;
        Sala sala;
        try {
            peliculaNueva = repoPeliculas.buscarElemento(idPelicula);
            sala = repoSalas.buscarSala(idSala);
        } catch (ElementoNoExiste e) {
            throw new ValidacionException("La película o la sala seleccionada no existen.");
        }

        // --- 2. DEFINIR RANGO DE LA NUEVA FUNCIÓN ---
        LocalDateTime inicioNueva = fechaHora;
        long duracionNueva = peliculaNueva.getDuracion(); // Ej: 120 minutos
        LocalDateTime finNueva = inicioNueva.plusMinutes(duracionNueva);

        // --- 3. VALIDACIÓN DE LÓGICA

        ArrayList<Funcion> funcionesDeLaSala = buscarFuncionesPorSala(idSala);

        for (Funcion fExistente : funcionesDeLaSala) {

            // Por cada función, necesitamos saber cuándo empieza y termina
            Pelicula pExistente = repoPeliculas.buscarElemento(fExistente.getIdPelicula());
            long duracionExistente = pExistente.getDuracion();
            /// Long es una palabra reservada que se u sa para almacenar numeros enteros que son extremadamente grandes
            LocalDateTime inicioExistente = fExistente.getHorario();
            LocalDateTime finExistente = inicioExistente.plusMinutes(duracionExistente);

            if (inicioExistente.isBefore(finNueva) && finExistente.isAfter(inicioNueva)) {

                throw new ValidacionException(
                        "Error: El horario se pisa con la función de '" + pExistente.getTitulo() + "' " +
                                "(que termina a las " + finExistente.toLocalTime() + ").");
            }
        }
        // --- FIN DE LA VALIDACIÓN ---

        // 4. Si el bucle for termina sin lanzar excepción, el horario está libre.
        Funcion nuevaFuncion = new Funcion(idPelicula, idSala, fechaHora, sala.getCapacidadTotal());
        repoFunciones.agregarFuncion(nuevaFuncion);
        guardarFunciones();
    }

    public Funcion buscarFuncion(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        return repoFunciones.buscarFuncion(id);
    }

    public ArrayList<Funcion> buscarFuncionesPorSala(String idSala) {
        ArrayList<Funcion> filtradas = new ArrayList<>();
        for (Funcion f : repoFunciones.getListaFunciones()) {
            if (f.getIdSala().equals(idSala)) {
                filtradas.add(f);
            }
        }
        return filtradas;
    }

    public ArrayList<Funcion> getListaFunciones() {
        return repoFunciones.getListaFunciones();
    }

    public void eliminarFuncion(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        repoFunciones.eliminarFuncion(id);
        guardarFunciones();
    }


    // --- 4. MÉTODOS PRIVADOS DE CARGA/GUARDADO ---

    // --- Métodos de Guardado ---
    public void guardarPeliculas() {
        JsonUtiles.grabarUnJson(repoPeliculas.arregloDePeliculasJson(), ARCHIVO_PELICULAS);
    }

    public void guardarSalas() {
        JsonUtiles.grabarUnJson(repoSalas.arregloDeSalasJson(), ARCHIVO_SALAS);
    }

    public void guardarFunciones() {
        JsonUtiles.grabarUnJson(repoFunciones.ArregloDeFunciones(), ARCHIVO_FUNCIONES);
    }

    // --- Métodos de Carga (Llamados por el constructor) ---
    private void cargarPeliculas() {
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_PELICULAS);
        if (tokener != null) {
            try {
                JSONArray jsonArray = new JSONArray(tokener);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Pelicula p = Pelicula.traerDesdeJson(obj);
                    repoPeliculas.agregarPelicula(p);
                }
            } catch (Exception e) {
                System.err.println("ALERTA: " + ARCHIVO_PELICULAS + " corrupto o error al cargar.");
            }
        }
    }

    private void cargarSalas() {
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_SALAS);
        if (tokener != null) {
            try {
                JSONArray jsonArray = new JSONArray(tokener);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Sala s = Sala.traerDesdeJson(obj);
                    repoSalas.agregarSala(s);
                }
            } catch (Exception e) {
                System.err.println("ALERTA: " + ARCHIVO_SALAS + " corrupto o error al cargar.");
            }
        }
    }

    private void cargarFunciones() {
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_FUNCIONES);
        if (tokener != null) {
            try {
                JSONArray jsonArray = new JSONArray(tokener);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Funcion f = Funcion.traerDesdeJson(obj);
                    repoFunciones.agregarFuncion(f);
                }
            } catch (Exception e) {
                System.err.println("ALERTA: " + ARCHIVO_FUNCIONES + " corrupto o error al cargar.");
            }
        }
    }

    /**
     * Recibe el ID de una función y devuelve un String "bonito"
     * con los detalles (título, sala, hora) conectando los repositorios.
     * * @param idFuncion El ID de la función a detallar.
     *
     * @return Un String formateado con los detalles.
     * @throws ElementoNoExiste Si la función, película o sala no se encuentran.
     */

    public String getDetalleFuncion(String idFuncion)
            throws ElementoNoExiste, VerificarNulo, ElementoRepetido {

        // 1. Buscar los 3 objetos necesarios
        Funcion funcion = this.repoFunciones.buscarFuncion(idFuncion);
        Pelicula pelicula = this.repoPeliculas.buscarElemento(funcion.getIdPelicula());
        Sala sala = this.repoSalas.buscarSala(funcion.getIdSala());

        // 2. Formatear la hora para que sea legible
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM 'a las' HH:mm 'hs'");
        String horarioFormateado = funcion.getHorario().format(formato);

        // 3. Armar el String final (en un solo renglón para la lista)
        String detalle = String.format("🎬 %s | Sala %d | %s | %d asientos disp.",
                pelicula.getTitulo(),
                sala.getNumSala(),
                horarioFormateado,
                funcion.getAsientosDisponibles()
        );

        return detalle;
    }


    public ArrayList<Funcion> getFuncionesDisponiblesParaVenta() {

        ArrayList<Funcion> filtradas = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        for (Funcion f : repoFunciones.getListaFunciones()) {
            // Si la hora de la función es DESPUÉS de la hora actual
            if (f.getHorario().isAfter(ahora)) {
                filtradas.add(f);
            }
        }
        return filtradas;
    }

}