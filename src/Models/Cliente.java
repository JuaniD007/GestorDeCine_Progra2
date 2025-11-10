package Models;

import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente  extends Usuario implements ItoJson {
    private int idCliente; /// LO SACARIA Y HARIA SOLO ID USUARIO GENERADO AUTOM.
    private int puntosPorCompraVisita;
    private ArrayList<Reserva> reservaArrayList;


    public Cliente(String nombre, String dni, int edad, String email) {
        super(nombre, dni, edad, email);
        this.puntosPorCompraVisita = 0;
    }

    public Cliente(){
        super("","",0,"");
    }
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
                "idCliente=" + idCliente +
                ", puntosPorCompraVisita=" + puntosPorCompraVisita +
                ", reservaArrayList=" + reservaArrayList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        if (!super.equals(o)) return false;
        return idCliente == cliente.idCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCliente);
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

    public JSONObject toJson (){
        JSONObject j = new JSONObject();
        JSONArray arreglo = new JSONArray();
        try {
            j.put("id cliente", this.idCliente);
            j.put("puntos por compra o visita", this.puntosPorCompraVisita);
            j.put("dni", this.dni);
            j.put("edad", this.edad);
            j.put("nombre", this.nombre);
            for (Reserva reserva : reservaArrayList){
              //  arreglo.put(reserva.);
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return j ;
    }

    public static Cliente traerJSon (JSONObject o ){
        Cliente p = new Cliente();
        try{
            p.setDni(o.getString("dni"));
            p.setEdad(o.getInt("edad"));
            p.setNombre(o.getString("nombre"));
            p.setIdCliente(o.getInt("idCliente"));
            p.setPuntosPorCompraVisita(o.getInt("puntos Por compra o visita"));
            JSONArray array =o.getJSONArray("RESERVA: ");

            for (int i =0; i<array.length();i++){

                JSONObject obj = array.getJSONObject(i);
             //   Reserva r = Reserva.traerJSon(obj);       RESERVA NECESITA METODO TOJSON :|
             //   curso.agregar(a);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
        
}
