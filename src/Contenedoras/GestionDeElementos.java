package Contenedoras;

import Excepciones.ElementoNoExiste;
import Excepciones.ElementoRepetido;
import Excepciones.VerificarNulo;
import Models.Cliente;
import Models.Pelicula;

import java.util.ArrayList;

public class GestionDeElementos<T>
{
    private ArrayList<T> elementos;

    public GestionDeElementos() {
        this.elementos = new ArrayList<>();
    }

    public ArrayList<T> getElementos() {
        return elementos;
    }




    public boolean agregarElemento(T objeto) throws VerificarNulo, ElementoRepetido
    {
        if (objeto == null) {
            throw new VerificarNulo("El objeto a agregar es nulo. No se puede añadir a la colección");
        }

        int idDelObjeto = -1;

        if (objeto instanceof Cliente cliente)
        {
            idDelObjeto = cliente.getIdCliente();
        } else if (objeto instanceof Pelicula pelicula)
        {
            idDelObjeto = pelicula.getIdPelicula();
        }

        if (idDelObjeto != -1) {
            try {

                T elementoExistente = buscarElemento(idDelObjeto);


                throw new ElementoRepetido("Error: Ya existe un elemento con ID " + idDelObjeto + " No se agrega");

            } catch (ElementoNoExiste e)
            {
                e.getMessage();
            } catch (VerificarNulo e) {
                throw new VerificarNulo("Error interno: La coleccion es nula.No se pudo validar");
            } catch (ElementoRepetido e) {
                throw new ElementoRepetido("Error interno: El ID " + idDelObjeto + " ya está duplicado en la coleccion");
            }
        }

        return this.elementos.add(objeto);
    }




    public T buscarElemento(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido
    {
        if (this.elementos == null) {
            throw new VerificarNulo("La colección de elementos es nula y no se puede buscar");
        }
        T elementoBuscar = null;
        int contador = 0;

        for (T buscado: this.elementos)
        {
            boolean encontrado = false;

            if (buscado instanceof Cliente cliente) {
                if (cliente.getIdCliente() == id) {
                    encontrado = true;
                }
            }

            if (buscado instanceof Pelicula pelicula)
            {
                if (pelicula.getIdPelicula() == id) {
                    encontrado = true;
                }
            }

            if (encontrado) {
                if (elementoBuscar == null) {
                    elementoBuscar = buscado;
                    contador++;
                } else {
                    contador++;
                }
            }
        }

        if (contador > 1) {
            throw new ElementoRepetido("Se encontraron " + contador +
                    " elementos con el ID " + id + " Los IDs deben ser únicos");
        }

        if (elementoBuscar == null)
        {
            throw new ElementoNoExiste("El elemento que se intento buscar no existe");
        }

        return elementoBuscar;
    }


    public boolean eliminarElemento(int id) throws ElementoNoExiste, VerificarNulo, ElementoRepetido
    {
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

        if (eliminado) {
            return true;
        } else {
            return false;
        }
    }


}
