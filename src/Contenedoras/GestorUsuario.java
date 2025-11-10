package Contenedoras;

import Models.Usuario;

import Models.Cliente;
import Models.Empleado;

import java.util.Scanner;
import java.util.regex.Pattern;

public class GestorUsuario {
    // Repositorio genérico para almacenar usuarios (Cliente o Empleado)
    private RepositorioUsuario<Usuario> repoUsuario = new RepositorioUsuario<>();


    /**
     * Método para crear un usuario (Cliente o Empleado) según el tipo indicado.
     * Realiza validaciones para nombre, DNI, edad y email antes de crear el objeto.
     */

    private void crearUsuario(String tipo) {
        Scanner sc = new Scanner(System.in);

        // Validar nombre
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine().trim(); // trim() elimina espacios al inicio y final
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Validar DNI
        System.out.print("Ingrese DNI: ");
        String dni = sc.nextLine().trim();
        // Verifica que no esté vacío y que solo contenga números usando regex
        if (dni.isEmpty() || !dni.matches("\\d+")) {
            System.out.println("DNI inválido. Debe ser numérico y no vacío.");
            return;
        }

        // Validar edad
        System.out.print("Ingrese edad: ");
        int edad;
        try {
            edad = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Edad inválida. Debe ser un número.");
            return;
        }
        if (edad < 18 || edad > 90) {
            System.out.println("Edad inválida. Debe estar entre 18 y 90.");
            return;
        }

        // Validar email
        System.out.print("Ingrese email: ");
        String email = sc.nextLine().trim();
        if (!esEmailValido(email)) {
            System.out.println("Email inválido.");
            return;
        }


        // === CREACIÓN DEL USUARIO ===
        // Según el tipo (Cliente o Empleado), instancia la clase correspondiente

        Usuario usuario = tipo.equalsIgnoreCase("Cliente") ?
                new Cliente(nombre, dni, edad, email) :
                new Empleado(nombre, dni, edad, email);

        // Agregar al repositorio
        try {
            // Llama al método del repositorio que valida duplicados y agrega el usuario
            if (repoUsuario.verificarUsuario(usuario)) {
                System.out.println(tipo + " agregado correctamente: " + usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al agregar usuario: " + e.getMessage());
        }
    }


    /**
     * Método auxiliar para validar email usando expresión regular.
     * Retorna true si el email cumple el patrón, false si no.
     */

    private boolean esEmailValido(String email) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }

    /*^ → inicio de la cadena.
    [\\w.-]+ → uno o más caracteres alfanuméricos (\w), puntos (.) o guiones (-) → esto es la parte antes del @.
    @ → símbolo arroba obligatorio.
    [\\w.-]+ → uno o más caracteres válidos para el dominio.
    \\. → punto literal antes de la extensión.
    [a-zA-Z]{2,} → al menos 2 letras (ej. .com, .ar, .org).
    $ → fin de la cadena.*/

}
