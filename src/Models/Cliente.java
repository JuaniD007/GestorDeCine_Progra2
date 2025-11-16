package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

// La clase Cliente es solo un modelo de datos.
// NO guarda la lista de reservas.
public class Cliente extends Usuario implements ItoJson {

    // (El ArrayList<Reserva> se elimina)



    public Cliente(String nombre, String dni, int edad, String email, String contrasenia) {
        super(nombre, dni, edad, email, contrasenia);
    }

    // Constructor vacío para JSON
    public Cliente() {
        super("", "", 0, "","");
    }

    // (Los getters/setters de reservaArrayList se eliminan)

    @Override
    public String toString() {
        return "Cliente{" + super.toString() + "}";
    }

    // El toJson() ahora es mucho más simple.
    // Solo guarda el "tipo" (heredando el resto de Usuario.toJson())
    @Override
    public JSONObject toJson() {
        JSONObject j = super.toJson(); // Llama al toJson() de Usuario
        try {
            j.put("tipo", "Cliente");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }


    public static Cliente traerJSon(JSONObject o) {
        Cliente c = new Cliente();

        // Llama al método estático de Usuario para rellenar los datos
        Usuario.traerDesdeJson(c, o);

        // Ya no necesita cargar la lista de reservas (eso lo hará el GestorDeVentas)
        return c;
    }
}
