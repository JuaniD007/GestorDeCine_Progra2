package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects; // <-- Importado
import java.util.UUID;    // <-- Importado

// 1. Implementa IIdentificable (para usarla en GestionDeRepositorio)
public class Reserva implements ItoJson, IIdentificable {

    // 2. ID unificado (en lugar de idReserva)
    private String id;

    // 3. IDs en lugar de Objetos (evita el bucle infinito)
    private String idCliente; // <-- Agregado (para saber de quién es)
    private String idFuncion; // <-- Cambiado (antes era 'Funcion funcion')

    // 4. Atributos propios de la reserva
    private int numAsiento;
    private LocalDate fechaReserva; // El día que se HIZO la reserva
    private boolean pagado;
    private boolean estadoReserva; // Ej: Activa o Cancelada
    private Double precioTotal;
    /**
     * Constructor para el GestorDeVentas.
     * Auto-genera su propio ID.
     */
    public Reserva(String idCliente, String idFuncion, int numAsiento, LocalDate fechaReserva, boolean pagado, boolean estadoReserva, Double precioTotal) {
        this.id = UUID.randomUUID().toString().substring(0, 8); // Auto-ID
        this.idCliente = idCliente;
        this.idFuncion = idFuncion;
        this.numAsiento = numAsiento;
        this.fechaReserva = fechaReserva;
        this.pagado = pagado;
        this.estadoReserva = estadoReserva;
        this.precioTotal = precioTotal;
    }

    /**
     * Constructor vacío para cargar desde JSON
     */
    public Reserva() {}

    // 5. Getters y Setters para la arquitectura de IDs
    @Override
    public String getId() { return id; }
    public String getIdCliente() { return idCliente; }
    public String getIdFuncion() { return idFuncion; }
    public int getNumAsiento() { return numAsiento; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public boolean isPagado() { return pagado; }
    public boolean isEstadoReserva() { return estadoReserva; }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // --- Setters (Usados por traerDesdeJson) ---
    public void setId(String id) { this.id = id; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public void setIdFuncion(String idFuncion) { this.idFuncion = idFuncion; }
    public void setNumAsiento(int numAsiento) { this.numAsiento = numAsiento; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }
    public void setPagado(boolean pagado) { this.pagado = pagado; }
    public void setEstadoReserva(boolean estadoReserva) { this.estadoReserva = estadoReserva; }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean marcarComoPagado() {
        if (!this.pagado) {
            this.pagado = true;
            return true;
        }
        return false;
    }

    // 6. Equals/HashCode (necesarios para GestionDeRepositorio)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    // 7. MÉTODO QUE SE USARÁ EN OTRO LADO (COMENTADO)
    /*
     * << COMENTARIO PARA EL EQUIPO >>
     * Este método 'generarTicket' ya NO PUEDE funcionar aca.
     * La Reserva ya no conoce el objeto 'Funcion' (solo su ID).
     * * Esta lógica AHORA le pertenece al 'GestorDeVentas' o al 'Menu'.
     * Se creará un método en el gestor, por ejemplo:
     * public String getTicketDetallado(String idReserva)
     * * Ese método buscará la Reserva, la Funcion, la Pelicula y la Sala
     * (usando los Gestores) para armar el String del ticket.
     */
    //    public String generarTicket() {
    //        return "🎟️ TICKET DE RESERVA 🎟️\n" +
    //                "Código: " + id + "\n" +
    //                "Fecha reserva: " + fechaReserva + "\n" +
    //                "Función: " + (funcion != null ? funcion.getPelicula().getTitulo() : "  ") + "\n" +
    //                "Sala: " + (funcion != null ? funcion.getSala().getNumSala() : "  ") + "\n" +
    //                "Asiento: " + numAsiento + "\n" +
    //                "Pagado: " + (pagado ? "Sí" : "No") + "\n" +
    //                "Estado: " + (estadoReserva ? "Activa" : "Cancelada");
    //    }

    // 8. toJson (Refactorizado para guardar IDs)
    @Override
    public JSONObject toJson() {
        JSONObject j = new JSONObject();
        try {
            j.put("id", this.id);
            j.put("idCliente", this.idCliente);
            j.put("idFuncion", this.idFuncion); // <-- Guarda el ID (String)
            j.put("estadoReserva", this.estadoReserva);
            j.put("pagado", this.pagado); // <-- Unificado a minúscula
            j.put("fechaReserva", this.fechaReserva.toString());
            j.put("numAsiento", this.numAsiento);
            j.put("precioTotal", this.precioTotal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }

    // 9. traerDesdeJson (Refactorizado)
    public static Reserva traerDesdeJson(JSONObject o) throws JSONException {
        Reserva r = new Reserva();

        r.setId(o.getString("id"));
        r.setIdCliente(o.getString("idCliente"));
        r.setIdFuncion(o.getString("idFuncion"));
        r.setEstadoReserva(o.getBoolean("estadoReserva"));
        r.setPagado(o.getBoolean("pagado")); // <-- Unificado a minúscula
        r.setNumAsiento(o.getInt("numAsiento"));
        r.setFechaReserva(LocalDate.parse(o.getString("fechaReserva")));
        r.setPrecioTotal(o.getDouble("precioTotal"));

        return r;
    }
}