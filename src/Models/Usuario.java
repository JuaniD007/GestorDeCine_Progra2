package Models;

import java.util.Objects;
import java.util.UUID;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String dni;
    protected int edad;
    protected String email;

    public Usuario( String nombre, String dni, int edad, String email) {
        this.id = UUID.randomUUID().toString(); // Genera ID único
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
        this.email = email;
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

    public void setEmail(String email) {this.email = email;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(dni, usuario.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

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
}
