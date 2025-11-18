package UI;
import Contenedoras.GestorDeCartelera;
import Contenedoras.GestorDeVentas;
import Contenedoras.GestorUsuario;
import Models.*;
import Enum.*;
import Excepciones.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    // --- Los 3 "Cerebros" (se reciben del Cine) ---
    private GestorUsuario gestorUsuario;
    private GestorDeCartelera gestorDeCartelera;
    private GestorDeVentas gestorDeVentas;

    private Scanner scanner;
    private Usuario usuarioLogueado; // Para saber quién está usando el sistema

    /**
     * El constructor recibe los gestores (creados por la clase Cine)
     */
    public Menu(GestorUsuario gestorUsuario, GestorDeCartelera gestorDeCartelera, GestorDeVentas gestorDeVentas) {
        this.gestorUsuario = gestorUsuario;
        this.gestorDeCartelera = gestorDeCartelera;
        this.gestorDeVentas = gestorDeVentas;
        this.scanner = new Scanner(System.in);
        this.usuarioLogueado = null;
    }

    /**
     * Bucle principal del programa.
     * Muestra el menú de login/registro.
     */
    public void mostrarMenuPrincipal() {
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- BIENVENIDO AL CINE ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Iniciar como Administrador");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine()); // Lee como String y convierte
            } catch (NumberFormatException e) {
                System.err.println("Error: Debe ingresar un número.");
                opcion = 0; // Resetea la opción
            }

            switch (opcion) {
                case 1:
                    uiLoginCliente();
                    break;
                case 2:
                    uiRegistroCliente();
                    break;
                case 3:
                    uiLoginAdmin();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.err.println("Opción no válida.");
            }

            // Si el login fue exitoso (en case 1 o 3), se entra al menú correspondiente
            if (this.usuarioLogueado != null) {
                if (this.usuarioLogueado instanceof Administrador) {
                    mostrarMenuAdmin(); // Bucle del menú de Admin
                } else {
                    mostrarMenuCliente(); // Bucle del menú de Cliente
                }
                // Al salir de los sub-menús, se cierra la sesión
                this.usuarioLogueado = null;
                System.out.println("\nSesión cerrada.");
            }
        }
    }

    // --- MENÚS MODULARIZADOS ---

    /**
     * Bucle del Menú de Administrador.
     * Se llama después de un login de admin exitoso.
     */
    private void mostrarMenuAdmin() {
        int opcion = 0;
        while (opcion != 10) {
            System.out.println("\n--- 🧑‍💼 MENÚ DE ADMINISTRADOR ---");
            System.out.println("--- Inventario ---");
            System.out.println("1. Agregar Película");
            System.out.println("2. Agregar Sala");
            System.out.println("3. Agregar Función");
            System.out.println("\n--- Consultas ---");
            System.out.println("4. Listar Películas");
            System.out.println("5. Listar Salas");
            System.out.println("6. Listar Funciones");
            System.out.println("\n--- Mantenimiento ---");
            System.out.println("7. Eliminar Película");
            System.out.println("8. Eliminar Sala");
            System.out.println("9. Eliminar Función");
            System.out.println("\n10. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: Debe ingresar un número.");
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    uiCrearPelicula();
                    break;
                case 2:
                    uiCrearSala();
                    break;
                case 3:
                    uiCrearFuncion();
                    break;
                case 4:
                    uiListarPeliculas();
                    break;
                case 5:
                    uiListarSalas();
                    break;
                case 6:
                    uiListarFunciones();
                    break;
                case 7:
                    uiEliminarPelicula();
                    break;

                case 8:
                    uiEliminarSala();
                    break;


                case 9:
                    uiEliminarFuncion();
                    break;

                case 10:
                    System.out.println("Cerrando sesión de administrador...");
                    break;
                default:
                    System.err.println("Opción no válida.");
            }
        }
    }


    private void uiEliminarPelicula() {
        System.out.println("\n--- Eliminar Película ---");
        uiListarPeliculas(); // Mostramos la lista para que el admin vea los IDs

        try {
            System.out.print("Ingrese el ID de la Película que desea ELIMINAR: ");
            String id = scanner.nextLine();

            // Llama al Gestor (que lanza excepciones si falla)
            gestorDeCartelera.eliminarPelicula(id);

            System.out.println("¡Película eliminada con éxito!");

        } catch (Exception e) { // Atrapa ElementoNoExiste, etc.
            System.err.println("Error al eliminar la película: " + e.getMessage());
        }
    }

    /**
     * Pide un ID de Sala y llama al gestor para eliminarla.
     */
    private void uiEliminarSala() {
        System.out.println("\n--- Eliminar Sala ---");
        uiListarSalas(); // Mostramos la lista para que el admin vea los IDs

        try {
            System.out.print("Ingrese el ID de la Sala que desea ELIMINAR: ");
            String id = scanner.nextLine();

            gestorDeCartelera.eliminarSala(id);
            System.out.println("¡Sala eliminada con éxito!");

        } catch (Exception e) {
            System.err.println("Error al eliminar la sala: " + e.getMessage());
        }
    }

    /**
     * Pide un ID de Función y llama al gestor para eliminarla.
     */
    private void uiEliminarFuncion() {
        System.out.println("\n--- Eliminar Función ---");

        // 1. Mostramos la lista de funciones para que el Admin vea los IDs
        uiListarFunciones();

        System.out.print("Ingrese el ID de la Función que desea ELIMINAR: ");
        String idFuncion = scanner.nextLine();

        // Salida rápida si no ingresa nada
        if (idFuncion == null || idFuncion.trim().isEmpty()) {
            System.out.println("Operación cancelada.");
            return;
        }

        try {
            // --- VALIDACIÓN DE BLOQUEO ---

            // 2. Le preguntamos al Gestor de Ventas si la función está en uso.
            //    (El Menú es el único que conoce a ambos gestores).
            boolean tieneReservas = gestorDeVentas.funcionTieneReservas(idFuncion);

            if (tieneReservas) {
                // 3. Si está en uso (true), BLOQUEAMOS la eliminación.
                //    Esto es un mensaje de error para el usuario, está bien usar .err.
                System.err.println("\nError: No se puede eliminar la función (ID: " + idFuncion + ").");
                System.err.println("Motivo: Ya tiene reservas activas vendidas.");

            } else {
                // 4. Si está libre (false), SÍ procedemos a borrar.
                //    Llamamos al Gestor de Catálogo (el que sabe borrar funciones).
                gestorDeCartelera.eliminarFuncion(idFuncion);
                System.out.println("¡Función eliminada con éxito!.");
            }

        } catch (ElementoNoExiste e) {
            // Esta excepción salta si el ID que escribió el admin no existe
            System.err.println("Error al eliminar: " + e.getMessage());
        } catch (Exception e) {
            // Atrapa cualquier otro error inesperado
            System.err.println("Error inesperado al procesar la eliminación: " + e.getMessage());
            e.printStackTrace(); // Para depuración
        }
    }


    /**
     * Bucle del Menú de Cliente.
     * Se llama después de un login de cliente exitoso.
     */
    private void mostrarMenuCliente() {
        int opcion = 0;
        while (opcion != 9) {
            System.out.println("\n--- 🎟️ MENÚ DE CLIENTE ---");
            System.out.println("1. Comprar Entrada");
            System.out.println("2. Ver Mis Reservas");
            System.out.println("3. Ver Cartelera");
            System.out.println("4. Pagar Reserva");
            System.out.println("9. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error: Debe ingresar un número.");
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    uiCrearReserva();
                    break;
                case 2:
                    uiVerMisReservas();
                    break;
                case 3:
                    uiListarPeliculasCliente();
                    break;
                case 4:

                    uiPagarReserva();

                    break;
                case 9:
                    System.out.println("Cerrando sesión de cliente...");
                    break;
                default:
                    System.err.println("Opción no válida.");
            }
        }
    }


    // --- UIs de LOGIN y REGISTRO ---

    private void uiLoginCliente() {
        System.out.println("\n--- Login Cliente ---");
        System.out.print("Ingrese DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese Contraseña: ");
        String pass = scanner.nextLine();

        try {
            // Llama al Gestor (que devuelve un Usuario o lanza Excepción)
            this.usuarioLogueado = gestorUsuario.iniciarSesion(dni, pass);
            System.out.println("¡Login exitoso! Bienvenido, " + this.usuarioLogueado.getNombre());
        } catch (Exception e) {
            // El Menú atrapa la excepción y muestra el error
            System.err.println("Error de login: " + e.getMessage());
            this.usuarioLogueado = null;
        }
    }


    private void uiLoginAdmin() {
        System.out.println("\n--- Login Administrador ---");
        System.out.print("Ingrese DNI Admin: ");
        String dni = scanner.nextLine();
        System.out.print("Ingrese Contraseña Admin: ");
        String pass = scanner.nextLine();

        try {
            this.usuarioLogueado = gestorUsuario.iniciarSesion(dni, pass);

            // Validamos que sea un Administrador
            if (this.usuarioLogueado instanceof Administrador) {
                System.out.println("Acceso de Administrador CONCEDIDO. Bienvenido, " + this.usuarioLogueado.getNombre());
            } else {
                System.err.println("Acceso DENEGADO: El usuario no es un Administrador.");
                this.usuarioLogueado = null;
            }
        } catch (Exception e) {
            System.err.println("Error de login: " + e.getMessage());
            this.usuarioLogueado = null;
        }
    }

    private void uiRegistroCliente() {
        System.out.println("\n--- Registro de Nuevo Cliente ---");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("DNI: ");
            String dni = scanner.nextLine();
            System.out.print("Edad: ");
            String edad = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Contraseña: ");
            String pass = scanner.nextLine();

            // Llama al Gestor (que valida y lanza excepciones)
            gestorUsuario.crearUsuario("Cliente", nombre, dni, edad, email, pass);
            System.out.println("¡Registro exitoso! Ahora puedes iniciar sesión.");

        } catch (Exception e) {
            // El Menú atrapa cualquier error de validación
            System.err.println("Error de registro: " + e.getMessage());
        }
    }

    private void uiListarPeliculasCliente() {
        System.out.println("\n--- Películas en Cartelera ---");
        ArrayList<Pelicula> lista = gestorDeCartelera.getListaPeliculas();
        if (lista.isEmpty()) {
            System.out.println("No hay películas cargadas en este momento.");
            return;
        }
        for (Pelicula p : lista) {
            // Llama al nuevo método 'getDetalleCliente()' de Pelicula.java
            System.out.println("• " + p.getDetalleParaCartelera());
        }
    }


    // --- UIs de ADMINISTRADOR ---

    private void uiCrearPelicula() {
        System.out.println("\n--- Crear Nueva Película ---");
        try {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();


            Genero genero = uiSeleccionarGenero();

            System.out.print("Duración (minutos): ");
            int duracion = Integer.parseInt(scanner.nextLine());

            System.out.print("Precio Base (ej: 8.4): ");
            double precio = Double.parseDouble(scanner.nextLine());

            // Llama al Gestor
            gestorDeCartelera.crearPelicula(titulo, genero, duracion, precio);
            System.out.println("¡Película creada con éxito!");

        } catch (IllegalArgumentException e) {
            System.err.println("Error: El Género ingresado no es válido.");
        } catch (Exception e) { // Atrapa ValidacionException, ElementoRepetido, etc.
            System.err.println("Error al crear la película: " + e.getMessage());
        }
    }

    private void uiCrearSala() {
        System.out.println("\n--- Crear Nueva Sala ---");
        try {
            System.out.print("Número de Sala: ");
            int numSala = Integer.parseInt(scanner.nextLine());
            System.out.print("Capacidad Total: ");
            int capacidad = Integer.parseInt(scanner.nextLine());
            System.out.print("¿Es 3D? (true/false): ");
            boolean es3D = Boolean.parseBoolean(scanner.nextLine());

            gestorDeCartelera.crearSala(numSala, capacidad, es3D);
            System.out.println("¡Sala creada con éxito!");

        } catch (Exception e) {
            System.err.println("Error al crear la sala: " + e.getMessage());
        }
    }

    private void uiCrearFuncion() {
        System.out.println("\n--- Crear Nueva Función ---");
        try {
            // 1. Pedir Película
            uiListarPeliculas(); // Mostramos la lista para ayudar
            System.out.print("Ingrese el ID de la Película: ");
            String idPelicula = scanner.nextLine();

            // 2. Pedir Sala
            uiListarSalas(); // Mostramos la lista
            System.out.print("Ingrese el ID de la Sala: ");
            String idSala = scanner.nextLine();

            System.out.println("\n--- Programar Horario ---");

            // Pedimos la FECHA
            System.out.print("Ingrese Fecha (Formato DD/MM/AAAA): ");
            String fechaStr = scanner.nextLine();
            // Creamos un formateador para "Día/Mes/Año"
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaStr, formatoFecha);

            // Pedimos la HORA
            System.out.print("Ingrese Hora (Formato HH:MM, 24hs): ");
            String horaStr = scanner.nextLine();
            // Creamos un formateador para "Hora:Minuto"
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime hora = LocalTime.parse(horaStr, formatoHora);

            // Combinamos la fecha y la hora en un solo objeto
            LocalDateTime fechaHoraCompleta = LocalDateTime.of(fecha, hora);

            gestorDeCartelera.crearFuncion(idPelicula, idSala, fechaHoraCompleta);
            System.out.println("¡Función creada con éxito!");

        } catch (DateTimeParseException e) {
            System.err.println("Error: El formato de fecha y hora es incorrecto.");
        } catch (Exception e) {
            System.err.println("Error al crear la función: " + e.getMessage());
        }
    }

    // --- UIs de CLIENTE ---

    // En Menu.java

    private void uiCrearReserva() {
        System.out.println("\n--- Comprar Entrada ---");

        // 1. OBTENEMOS LA LISTA DE FUNCIONES
        ArrayList<Funcion> funcionesDisponibles = gestorDeCartelera.getFuncionesDisponiblesParaVenta();

        if (funcionesDisponibles.isEmpty()) {
            System.out.println("No hay funciones programadas.");
            System.out.println("No hay funciones disponibles para comprar.");
            return;
        }

        // 2. MOSTRAMOS LA LISTA CON NÚMEROS
        System.out.println("--- Listado de Funciones ---");

        // Usamos un bucle 'for i' para poder tener un índice numérico
        for (int i = 0; i < funcionesDisponibles.size(); i++) {
            Funcion f = funcionesDisponibles.get(i);
            String detalle = "";
            try {
                // Obtenemos el detalle bonito (Ej: "Dune 2 | Sala 1...")
                detalle = gestorDeCartelera.getDetalleFuncion(f.getId());
            } catch (Exception e) {
                detalle = "Error al cargar detalle de función " + f.getId();
            }

            //  (i + 1) porque el array empieza desde 0 y no queremos eso para el usuario
            System.out.printf("[%d] %s\n", (i + 1), detalle);
            System.out.println("-----");
        }

        try {
            // 3. se pide  el numero (ej: 1, 2, 3...)
            System.out.print("Seleccione el número [#] de la Función que desea: ");
            int seleccion = Integer.parseInt(scanner.nextLine());

            // Validamos que el número esté en el rango
            if (seleccion < 1 || seleccion > funcionesDisponibles.size()) {
                System.err.println("Error: Selección no válida.");
                return;
            }

            // 4. TRADUCIMOS EL NÚMERO AL OBJETO REAL
            Funcion funcionElegida = funcionesDisponibles.get(seleccion - 1);
            String idFuncion = funcionElegida.getId(); // <-- Obtenemos el "b9a5d1c4" por ejemplo


            System.out.println("--- Las butacas ocupadas se veran con la cruz ---");

// Hacemos un bucle de 1 hasta la capacidad total
            // 4. MAPA DE ASIENTOS (VISUAL CENTRADO)
            Sala sala = gestorDeCartelera.buscarSala(funcionElegida.getIdSala());

            System.out.println("\n");
            // Dibujo de la Pantalla (Centrado manual con espacios)
            System.out.println("                     ___________________________________");
            System.out.println("                    |        PANTALLA DEL CINE          |");
            System.out.println("                     ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯");
            System.out.println("                                (FRENTE)\n");

            int asientosPorFila = 10;
            String margenIzquierdo = "                "; // El "padding" para centrar los asientos

            // Imprimimos el margen de la primera fila
            System.out.print(margenIzquierdo);

            for (int i = 1; i <= sala.getCapacidadTotal(); i++) {

                // Lógica visual: Ocupado vs Libre
                if (funcionElegida.isAsientoOcupado(i)) {
                    System.out.print("[XX] "); // Ocupado (Marca visual clara)
                } else {
                    System.out.printf("[%02d] ", i); // Libre (con 0 a la izquierda: 01, 05, 10)
                }

                // Salto de línea al terminar la fila
                if (i % asientosPorFila == 0) {
                    System.out.println(); // Baja renglón
                    // Si no es la última fila, imprime el margen para la siguiente
                    if (i < sala.getCapacidadTotal()) {
                        System.out.print(margenIzquierdo);
                    }
                }
            }
            System.out.println("\n");
            System.out.println("\n------------------------");
            System.out.print("Ingrese el Número de Asiento (ej: 5): ");
            int numAsiento = Integer.parseInt(scanner.nextLine());


            // 6. El Menú ahora debe buscar la película para saber el precio
            Pelicula pelicula = gestorDeCartelera.buscarPelicula(funcionElegida.getIdPelicula());
            double precioACobrar = pelicula.getPrecioBase();
            // (Aquí podrías agregar lógica extra, ej: if (sala.is3D()) precioACobrar *= 1.25;)

            System.out.printf("El precio de esta entrada es: $%.2f\n", precioACobrar);

            if (sala.isEs3D()) {
                double recargo = precioACobrar * 0.25; // 25% de recargo
                precioACobrar += recargo;
                System.out.printf("Info: Se aplicó un recargo de $%.2f por ser Sala 3D.\n", recargo);
            }
            // --- FIN DE LA LÓGICA DE PRECIO ---

            System.out.printf("El precio de esta entrada es: $%.2f\n", precioACobrar);


            String confirmacion = "";
            boolean entradaValida = false;

            // Bucle 'while' que se repite hasta que la entrada sea 'S' o 'N'
            while (!entradaValida) {
                System.out.print("¿Confirmar Reserva? (S/N): ");
                confirmacion = scanner.nextLine();

                // .toUpperCase() convierte "s" en "S" y "n" en "N"
                // .equals() comprueba si es S o N
                if (confirmacion.toUpperCase().equals("S") || confirmacion.toUpperCase().equals("N")) {
                    entradaValida = true; // La entrada es válida, salimos del bucle
                } else {
                    // Si no es S o N, mostramos error y el bucle se repite
                    System.err.println("Error: Por favor, ingrese solo 'S' o 'N'.");
                }
            }
            // --- FIN DE LA VALIDACIÓN ---


            // 7. VERIFICAR CONFIRMACIÓN
            // Usamos .equalsIgnoreCase() para ser flexibles (acepta 's' o 'S')
            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("Compra cancelada.");
                return;
            }

            // 6. Llama al Gestor de Ventas (usando el ID que encontramos)
            gestorDeVentas.crearReserva(this.usuarioLogueado.getId(), idFuncion, numAsiento, precioACobrar);
            System.out.println("¡Reserva creada con éxito! (Pendiente de pago)");

        } catch (NumberFormatException e) {
            System.err.println("Error: Debe ingresar un número.");
        } catch (Exception e) { // Atrapa ValidacionException, ElementoNoExiste, etc.
            System.err.println("Error al crear la reserva: " + e.getMessage());
        }
    }

    private void uiVerMisReservas() {
        System.out.println("\n--- Mis Reservas ---");
        // El Gestor de Ventas filtra las reservas por el ID del cliente logueado
        ArrayList<Reserva> misReservas = gestorDeVentas.buscarReservasPorCliente(this.usuarioLogueado.getId());

        if (misReservas.isEmpty()) {
            System.out.println("No tienes ninguna reserva.");
            return;
        }

        // Por cada reserva, pedimos el ticket detallado
        for (Reserva r : misReservas) {
            try {
                String ticket = gestorDeVentas.getTicketDetalladoCliente(r.getId(), this.usuarioLogueado.getNombre());
                System.out.println(ticket);
                System.out.println("--------------------");
            } catch (Exception e) {
                System.err.println("Error al cargar detalle de reserva " + r.getId());
            }
        }
    }

    // --- UIs COMPARTIDAS (Listados) ---

    private void uiListarPeliculas() {
        System.out.println("\n--- Listado de Películas ---");
        ArrayList<Pelicula> lista = gestorDeCartelera.getListaPeliculas();
        if (lista.isEmpty()) {
            System.out.println("No hay películas cargadas.");
            return;
        }
        for (Pelicula p : lista) {
            System.out.println(p.toString());
        }
    }

    private void uiListarSalas() {
        System.out.println("\n--- Listado de Salas ---");
        ArrayList<Sala> lista = gestorDeCartelera.getListaSalas();
        if (lista.isEmpty()) {
            System.out.println("No hay salas cargadas.");
            return;
        }

        System.out.println("(ID | Detalle)");
        System.out.println("---------------------------------");
        for (Sala s : lista) {
            // Imprime el ID primero, seguido del toString()
            System.out.println(s.getId() + " | " + s.toString());
        }
        System.out.println("---------------------------------");
    }


    private void uiListarFunciones() {
        System.out.println("\n--- Listado de Funciones ---");
        ArrayList<Funcion> lista = gestorDeCartelera.getListaFunciones();
        if (lista.isEmpty()) {
            System.out.println("No hay funciones programadas.");
            return;
        }
        for (Funcion f : lista) {
            try {
                // Pedimos al gestor que arme el detalle (esto es más lento pero más útil)
                String detalle = gestorDeCartelera.getDetalleFuncion(f.getId());
                System.out.println("ID: " + f.getId() + " | " + detalle);
                System.out.println("-----");
            } catch (Exception e) {
                System.err.println("Error al cargar datos de función " + f.getId());
            }
        }
    }

    private void uiPagarReserva() {
        System.out.println("\n--- Pagar Reserva Pendiente ---");

        // 1. Usamos el nuevo método del gestor
        ArrayList<Reserva> pendientes = gestorDeVentas.buscarReservasPendientesPorCliente(this.usuarioLogueado.getId());

        if (pendientes.isEmpty()) {
            System.out.println("No tienes reservas pendientes de pago.");
            return;
        }

        // 2. Mostramos la lista de pendientes con un índice
        System.out.println("Tus reservas pendientes de pago:");
        for (int i = 0; i < pendientes.size(); i++) {
            Reserva r = pendientes.get(i);
            String detalle = "";
            try {
                // Obtenemos el detalle (Peli, Sala, Hora)
                detalle = gestorDeCartelera.getDetalleFuncion(r.getIdFuncion());
            } catch (Exception e) {
                detalle = "Error al cargar detalle de función.";
            }

            System.out.printf("[%d] Asiento %d | %s | Precio: $%.2f\n",
                    (i + 1),
                    r.getNumAsiento(),
                    detalle,
                    r.getPrecioTotal() // <-- Mostramos el precio
            );
            System.out.println("-----");
        }

        try {
            // 3. Pedimos al usuario que elija
            System.out.print("Seleccione el número [#] de la reserva que desea pagar: ");
            int seleccion = Integer.parseInt(scanner.nextLine());

            if (seleccion < 1 || seleccion > pendientes.size()) {
                System.err.println("Error: Selección no válida.");
                return;
            }

            // 4. Traducimos la selección al ID de la reserva
            Reserva reservaAPagar = pendientes.get(seleccion - 1);
            String idReserva = reservaAPagar.getId();

            // 5. Llamamos al Gestor para pagar
            double totalPagado = gestorDeVentas.pagarReserva(idReserva);

            // 6. Mostramos el resultado (¡tu requisito!)
            System.out.println("\n¡Pago procesado con éxito!");
            System.out.printf("Total pagado: $%.2f\n", totalPagado);

        } catch (NumberFormatException e) {
            System.err.println("Error: Debe ingresar un número.");
        } catch (Exception e) {
            System.err.println("Error al procesar el pago: " + e.getMessage());
        }
    }

    private Genero uiSeleccionarGenero() {
        System.out.println("Seleccione un Género:");
        int i = 1;
        // Itera sobre TODOS los valores del Enum Genero
        for (Genero g : Genero.values()) {
            System.out.printf("[%d] %s\n", i, g.name());
            i++;
        }

        while (true) { // Bucle hasta que elija bien
            try {
                System.out.print("Seleccione el número [#]: ");
                int seleccion = Integer.parseInt(scanner.nextLine());
                return Genero.values()[seleccion - 1]; // Devuelve el Enum
            } catch (Exception e) {
                System.err.println("Error: Selección no válida.");
            }
        }
    }


}