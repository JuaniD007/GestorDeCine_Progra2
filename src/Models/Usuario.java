package Models;

import Interfaces.IIdentificable;
import Interfaces.ItoJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

public abstract class Usuario implements ItoJson, IIdentificable {
    protected String id;
    protected String nombre;
    protected String dni;
    protected int edad;
    protected String email;
    protected String contrasenia;

    public Usuario( String nombre, String dni, int edad, String email, String contrasenia) {
        this.id = UUID.randomUUID().toString(); // Genera ID único
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
        this.email = email;
        this.contrasenia = contrasenia;
    }


    public Usuario() { this.nombre = ""; this.dni = ""; this.edad = 0; }

    public String getId() {return id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {return email;}

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {this.email = email;}

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", edad=" + edad +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(dni, usuario.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public JSONObject toJson() {

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put("id", id);
                jsonObject.put("nombre", nombre);
                jsonObject.put("dni", dni);
                jsonObject.put("edad", edad);
                jsonObject.put("email", email);
                jsonObject.put("contrasenia", contrasenia);
            }catch (JSONException e) {
                e.printStackTrace();
            }

        return jsonObject;
    }


   public static Usuario traerDesdeJson (Usuario u, JSONObject o) {


       try{
       u.setId(o.getString("id"));
       u.setNombre(o.getString("nombre"));
       u.setDni(o.getString("dni"));
       u.setEdad(o.getInt("edad"));
       u.setEmail(o.getString("email"));
       u.setContrasenia(o.getString("contrasenia"));
       } catch (JSONException e) {
          e.printStackTrace();
       }
       return u;


   }

}
