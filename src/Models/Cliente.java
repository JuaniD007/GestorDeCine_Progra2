package Models;

import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente extends Usuario implements ItoJson {
    private ArrayList<Reserva> reservaArrayList;

    public Cliente(String nombre, String dni, int edad, String email) {
        super(nombre, dni, edad, email);
        this.reservaArrayList = new ArrayList<>();
    }

    public Cliente() {
        super("", "", 0, "");
        this.reservaArrayList = new ArrayList<>();
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
                ", reservaArrayList=" + reservaArrayList +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject j = super.toJson();
        try {
            JSONArray arregloReservas = new JSONArray();
            if (reservaArrayList != null) {
                for (Reserva reserva : reservaArrayList) {
                    arregloReservas.put(reserva.toJson());
                }
            }
            j.put("reservas", arregloReservas);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }


    public static Cliente traerJSon(JSONObject o) {
        Cliente c = new Cliente();
        Usuario.traerDesdeJson(c, o);
        try {
            JSONArray arrayReservas = o.optJSONArray("reservas");
            if (arrayReservas != null) {
                ArrayList<Reserva> lista = new ArrayList<>();
                for (int i = 0; i < arrayReservas.length(); i++) {
                    JSONObject objReserva = arrayReservas.getJSONObject(i);
                    Reserva r = Reserva.traerJSon(objReserva); // Debe existir este método en Reserva
                    lista.add(r);
                }
                c.setReservaArrayList(lista);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return c;
    }


}
