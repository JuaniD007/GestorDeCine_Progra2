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


    public Cliente(String nombre, String dni, int edad, String email, String contrasenia) {
        super(nombre, dni, edad, email, contrasenia);
    }

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


}
