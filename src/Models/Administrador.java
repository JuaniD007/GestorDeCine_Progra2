package Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Administrador extends  Usuario{

    public Administrador(String nombre, String dni, int edad, String email, String contrasenia) {
        super(nombre, dni, edad, email, contrasenia);
    }

    public Administrador() {
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "email='" + email + '\'' +
                ", dni='" + dni + '\'' +
                ", id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                '}';
    }

    @Override
    public JSONObject toJson() {

            JSONObject json = super.toJson(); // Llama al toJson() de Usuario (que ya guarda la contra)
            try {
                json.put("tipo", "Administrador");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
    }
}
