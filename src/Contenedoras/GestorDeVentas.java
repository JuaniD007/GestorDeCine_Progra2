package Contenedoras;

// Importaciones de Modelos
import Models.Reserva;
import Models.Funcion;
import Models.Pelicula;
import Models.Sala;

// Importaciones de Excepciones
import Excepciones.*;

// Importaciones de Utilidades y JSON
import ModelsJson.JsonUtiles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestorDeVentas {

    // --- 1. ATRIBUTOS ---
    private RepositorioReserva repoReservas;
    private GestorDeCartelera gestorCartelera;

    private static final String ARCHIVO_RESERVAS = "reservas.json";

    // --- 2. CONSTRUCTOR ---
    /**
     * El GestorDeVentas DEBE recibir al GestorDeCartelera para funcionar.
     */
    public GestorDeVentas(GestorDeCartelera gestorCartelera) {
        this.gestorCartelera = gestorCartelera; // Lo guarda para usarlo después
        this.repoReservas = new RepositorioReserva();
        cargarReservas();
    }

    // --- 3. MÉTODOS PÚBLICOS ( para el Menú) ---

    /**
     * Lógica principal de compra. Valida y crea una reserva.
     * Actualiza la función (ocupa el asiento) y persiste ambos cambios.
     */
    public void crearReserva(String idCliente, String idFuncion, int numAsiento, double precioTotal)
            throws ValidacionException, ElementoNoExiste, VerificarNulo, ElementoRepetido {

        // 1. Buscamos los objetos (usando el otro gestor)
        Funcion funcion = gestorCartelera.buscarFuncion(idFuncion);
        Sala sala = gestorCartelera.buscarSala(funcion.getIdSala());

        // 2. Validaciones de negocio
        if (numAsiento <= 0 || numAsiento > sala.getCapacidadTotal()) {
            throw new ValidacionException("Error: El número de asiento (" + numAsiento + ") no existe en la sala.");
        }
        if (funcion.isAsientoOcupado(numAsiento)) {
            throw new ValidacionException("Error: El asiento " + numAsiento + " ya está ocupado.");
        }
        if (funcion.getAsientosDisponibles() <= 0) {
            throw new ValidacionException("Error: La función está llena.");
        }

        // 3. Si todo está OK, procesamos la reserva

        // A. Marcamos el asiento como ocupado en la Función
        funcion.ocuparAsiento(numAsiento);

        // B.  Guardamos el cambio en el archivo de funciones
        gestorCartelera.guardarFunciones();

        // C. Creamos la nueva reserva
        Reserva nuevaReserva = new Reserva(idCliente, idFuncion, numAsiento, LocalDate.now(), false, true, precioTotal ); // (pagado=false, activa=true)

        // D. Guardamos la reserva en su propio repositorio y archivo
        repoReservas.agregarReserva(nuevaReserva);
        guardarReservas();
    }

    public double pagarReserva(String idReserva)
            throws ValidacionException, ElementoNoExiste, VerificarNulo, ElementoRepetido {

        // 1. Buscamos la reserva
        Reserva reserva = repoReservas.buscarReserva(idReserva);

        // 2. Validación
        if (reserva.isPagado()) {
            throw new ValidacionException("Error: Esta reserva ya fue pagada anteriormente.");
        }

        // 3. Actualizamos el objeto
        reserva.setPagado(true);

        // 4. Persistimos el cambio en el JSON
        guardarReservas();

        // 5. Devolvemos el total
        return reserva.getPrecioTotal();
    }


    /**
     * Busca solo las reservas NO pagadas de un cliente.
     * (El Menú usará esto para la nueva opción)
     */
    public ArrayList<Reserva> buscarReservasPendientesPorCliente(String idCliente) {
        ArrayList<Reserva> filtradas = new ArrayList<>();
        for (Reserva r : repoReservas.getListaReservas()) {
            if (r.getIdCliente().equals(idCliente) && !r.isPagado() && r.isEstadoReserva()) {
                filtradas.add(r);
            }
        }
        return filtradas;
    }

    /**
     * Busca todas las reservas de un cliente específico.
     * (El Menú usará esto para la opción "Mis Reservas")
     */
    public ArrayList<Reserva> buscarReservasPorCliente(String idCliente) {
        ArrayList<Reserva> filtradas = new ArrayList<>();
        for (Reserva r : repoReservas.getListaReservas()) {
            if (r.getIdCliente().equals(idCliente)) {
                filtradas.add(r);
            }
        }
        return filtradas;
    }

    /**
     * Refactorización de tu método "generarTicket()".
     * Ahora el GESTOR busca los IDs y arma el String.
     */

    public String getTicketDetalladoCliente(String idReserva, String nombreCliente)
            throws ElementoNoExiste, VerificarNulo, ElementoRepetido {

        // 1. Buscar los 4 objetos (esto es igual)
        Reserva reserva = repoReservas.buscarReserva(idReserva);
        Funcion funcion = gestorCartelera.buscarFuncion(reserva.getIdFuncion());
        Pelicula pelicula = gestorCartelera.buscarPelicula(funcion.getIdPelicula());
        Sala sala = gestorCartelera.buscarSala(funcion.getIdSala());

        // 2. Formatear
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm 'hs'");

        String ticket = "🎟️ TICKET DE RESERVA 🎟️\n" +
                "  Cliente: " + nombreCliente + "\n" +
                "  Película: " + pelicula.getTitulo() + "\n" +
                "  Sala: " + sala.getNumSala() + " (" + (sala.isEs3D() ? "3D" : "2D") + ")\n" +
                "  Horario: " + funcion.getHorario().format(formatoHora) + " - " + funcion.getHorario().format(formatoFecha) + "\n" +
                "  Asiento: " + reserva.getNumAsiento() + "\n" +
                "  Pagado: " + (reserva.isPagado() ? "Sí" : "No");

        return ticket;
    }


    // --- 4. MÉTODOS PRIVADOS DE CARGA/GUARDADO ---

    public void guardarReservas() {
        JsonUtiles.grabarUnJson(repoReservas.arregloDeReservasJson(), ARCHIVO_RESERVAS);
    }

    private void cargarReservas() {
        JSONTokener tokener = JsonUtiles.leerUnJson(ARCHIVO_RESERVAS);
        if (tokener != null) {
            try {
                JSONArray jsonArray = new JSONArray(tokener);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Reserva r = Reserva.traerDesdeJson(obj);
                    repoReservas.agregarReserva(r);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean funcionTieneReservas(String idFuncion) {

        // Obtenemos la lista de TODAS las reservas del cine
        ArrayList<Reserva> listaTotalReservas = repoReservas.getListaReservas();

        for (Reserva r : listaTotalReservas) {

            // Comprobamos si la reserva es de esa función Y si está activa

            if (r.getIdFuncion().equals(idFuncion) && r.isEstadoReserva()) {

                // Encontró una Es peligroso borrar.
                return true;
            }
        }

        // Si el bucle termina, es porque no encontró ninguna.
        // Es seguro borrar.
        return false;
    }




}

