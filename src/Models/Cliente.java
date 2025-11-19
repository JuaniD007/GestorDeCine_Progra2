package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

// La clase Cliente es solo un modelo de datos.

public class Cliente extends Usuario  {


    public Cliente(String nombre, String dni, int edad, String email, String contrasenia) {
        super(nombre, dni, edad, email, contrasenia);
    }

    @Override
    public String toString() {
        return "Cliente{" + super.toString() + "}";
    }

    // Solo guarda el "tipo"
    @Override
    public JSONObject toJson() {
        JSONObject j = super.toJson();
        try {
            j.put("tipo", "Cliente");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }


}
