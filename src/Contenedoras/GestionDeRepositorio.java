package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Interfaces.IIdentificable;

import java.util.ArrayList;

public class GestionDeRepositorio<T extends IIdentificable> {
    private ArrayList<T> elementos;

    public GestionDeRepositorio() {
        this.elementos = new ArrayList<>();
    }

    public ArrayList<T> getElementos() {
        return elementos;
    }


    public boolean agregarElemento(T objeto) throws VerificarNulo, ElementoRepetido {
        if (objeto == null) {
            throw new VerificarNulo("El objeto a agregar es nulo.");
        }

        // --- LÓGICA ARREGLADA ---
        try {
            // 1. Buscamos primero. Ahora podemos usar .getId()
            buscarElemento(objeto.getId());

            // 2. Si lo encuentra (y no lanza excepción), significa que está repetido.
            throw new ElementoRepetido("Error: Ya existe un elemento con ID " + objeto.getId());

        } catch (ElementoNoExiste e) {
            // 3. Si lanza ElementoNoExiste, Lo agregamos.
            return this.elementos.add(objeto);
        }
        // (El catch de VerificarNulo y ElementoRepetido de buscarElemento se propagan solos)
    }


    public T buscarElemento(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        if (this.elementos == null) {
            throw new VerificarNulo("La colección de elementos es nula.");
        }

        T elementoBuscar = null;
        int contador = 0;


        for (T buscado : this.elementos) {
            if (buscado.getId().equals(id)) { // <-- Sin 'instanceof'
                if (elementoBuscar == null) {
                    elementoBuscar = buscado;
                }
                contador++;
            }
        }

        if (contador > 1) {
            throw new ElementoRepetido("Error: Se encontraron " + contador + " elementos con el ID " + id);
        }

        if (elementoBuscar == null) {
            throw new ElementoNoExiste("No se encontró elemento con ID " + id);
        }

        return elementoBuscar;
    }

    public boolean eliminarElemento(String id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido {
        T elementoAEliminar = null;

        try {
            elementoAEliminar = buscarElemento(id);
        } catch (VerificarNulo e) {
            throw new VerificarNulo("La colección de datos es nula. No se puede eliminar");
        } catch (ElementoRepetido e) {
            throw new ElementoRepetido("ERROR: Se encontraron multiples elementos con ID " + id + " Imposible eliminar");
        } catch (ElementoNoExiste e) {
            throw new ElementoNoExiste("No se encontro ningun elemento con ID " + id + " para eliminar");
        }

        boolean eliminado = this.elementos.remove(elementoAEliminar);

        return eliminado;
    }



}
