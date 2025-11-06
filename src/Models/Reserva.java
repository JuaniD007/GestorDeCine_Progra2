package Models;

import java.time.LocalDate;

public class Reserva  {
    private int idReserva;
    private boolean estadoReserva;
    private int numAsiento;
    private LocalDate fechaReserva;
    private boolean pagado;
    private Funcion funcion;

    public Reserva(Funcion funcion, boolean estadoReserva, LocalDate fechaReserva, int idReserva, int numAsiento, boolean pagado) {
        this.estadoReserva = estadoReserva;
        this.fechaReserva = fechaReserva;
        this.idReserva = idReserva;
        this.numAsiento = numAsiento;
        this.pagado = pagado;
        this.funcion = funcion;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public boolean isEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(boolean estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public boolean marcarComoPagado() {
        if (!this.pagado) {
            this.pagado = true;
            return true;
        }
        return false;
    }
    public String generarTicket() {
        return "🎟️ TICKET DE RESERVA 🎟️\n" +
                "Código: " + idReserva + "\n" +
                "Fecha reserva: " + fechaReserva + "\n" +
                "Función: " + (funcion != null ? funcion.getPelicula().getTitulo() : "  ") + "\n" +
                "Sala: " + (funcion != null ? funcion.getSala().getNumSala() : "  ") + "\n" +
                "Asiento: " + numAsiento + "\n" +
                "Pagado: " + (pagado ? "Sí" : "No") + "\n" + // condición ? valorSiVerdadero : valorSiFalso (como un if- else )
                "Estado: " + (estadoReserva ? "Activa" : "Cancelada");
    }

}
