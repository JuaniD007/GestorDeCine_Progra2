package Models;

import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente  extends Usuario implements ItoJson {

    private int puntosPorCompraVisita;
    private ArrayList<Reserva> reservaArrayList;


    public Cliente(String nombre, String dni, int edad, String email) {
        super(nombre, dni, edad, email);
        this.puntosPorCompraVisita = 0;
    }

    public Cliente(){
        super("","",0,"");
    }



    public int getPuntosPorCompraVisita() {
        return puntosPorCompraVisita;
    }

    public void setPuntosPorCompraVisita(int puntosPorCompraVisita) {
        this.puntosPorCompraVisita = puntosPorCompraVisita;
    }

    public ArrayList<Reserva> getReservaArrayList() {
        return reservaArrayList;
    }

    public void setReservaArrayList(ArrayList<Reserva> reservaArrayList) {
        this.reservaArrayList = reservaArrayList;
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() +
                "idCliente=" +
                ", puntosPorCompraVisita=" + puntosPorCompraVisita +
                ", reservaArrayList=" + reservaArrayList +
                '}';
    }


    public boolean agregarReserva (Reserva r){
        return reservaArrayList.add(r);
    }

    public int sumarPuntos(int puntos) {
        if (puntos > 0) {
            puntosPorCompraVisita += puntos;
        }
        return puntosPorCompraVisita;
    }

    @Override
    public JSONObject toJson() {
        // 1. Llama al padre (Usuario) para obtener el JSON base
        JSONObject j = super.toJson();

        // 2. Agrega solo los campos de Cliente
        try {
            j.put("puntosPorCompraVisita", this.puntosPorCompraVisita);

            // 3. Maneja el Arreglo de Reservas
            JSONArray arregloReservas = new JSONArray();
            if (this.reservaArrayList != null) {
                for (Reserva reserva : this.reservaArrayList) {
                    // Asumimos que Reserva tiene su propio método toJson()
                    arregloReservas.put(reserva.toJson());
                }
            }
            j.put("reservas", arregloReservas); // Le damos una clave al arreglo

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }

    public static Cliente traerJSon(JSONObject o) {
        // 1. Crea la instancia vacía
        Cliente c = new Cliente();

        // 2. Llama al helper ESTÁTICO de Usuario para rellenar
        //    los campos base (id, nombre, dni, edad, email)
        Usuario.traerDesdeJson(c, o);

        // 3. Lee solo los campos de Cliente
        try {
            // El helper de Usuario ya se encargó de id, nombre, dni, etc.

            c.setPuntosPorCompraVisita(o.getInt("puntosPorCompraVisita")); // Usamos la clave del toJson()

            // 4. Maneja el Arreglo de Reservas
            // Nota: 'setReservaArrayList' no es necesario si inicializas en el constructor
            // c.setReservaArrayList(new ArrayList<>());

            JSONArray arrayReservas = o.getJSONArray("reservas"); // Usamos la clave del toJson()

            for (int i = 0; i < arrayReservas.length(); i++) {
                JSONObject objReserva = arrayReservas.getJSONObject(i);

                // Asumimos que Reserva tiene su propio método estático 'traerJSon'
                // Reserva r = Reserva.traerJSon(objReserva);
                // c.agregarReserva(r);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // 5. Retorna el cliente completo
        return c;
    }
        
}
