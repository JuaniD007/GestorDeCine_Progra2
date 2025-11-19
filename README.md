🎬 Sistema de Gestión de Cine
Este proyecto es una aplicación de consola en Java diseñada para administrar la operación completa de un cine, desde la configuración de salas y películas hasta la venta de entradas y gestión de usuarios.

📋 Descripción General
El sistema permite dos roles principales:

Administrador: Encargado de gestionar el "Inventario" (Películas, Salas y Funciones) y mantener el sistema.

Cliente: Usuario final que puede registrarse, consultar la cartelera y comprar entradas (reservas).

El proyecto destaca por una arquitectura robusta de capas, uso de Tipos Genéricos para la reutilización de código y persistencia de datos mediante archivos JSON.

🚀 Guía de Inicio Rápido
1. Requisitos Previos
Java Development Kit (JDK): Versión 8 o superior.

Librería JSON: El proyecto requiere la librería org.json para la persistencia de datos.

2. Ejecución
Ejecuta la clase principal: Main.java. El sistema verificará automáticamente la existencia de los archivos de datos. Si es la primera vez que se ejecuta, creará los archivos necesarios y un usuario Administrador por defecto.

3. Credenciales de Acceso (Admin)
Para acceder a las funcionalidades de gestión, utiliza el siguiente usuario pre-cargado:

DNI: 1111

Contraseña: admin

🛠️ Arquitectura del Sistema
El diseño sigue una separación estricta de responsabilidades para facilitar el mantenimiento y la escalabilidad:

🔹 Capa de Vista (UI / Vistas)
Menu.java: Es la única clase que interactúa con el usuario. Maneja las entradas por consola, muestra la información y captura errores para mostrarlos de forma amigable.

🔹 Capa de Lógica / Controladores (Contenedoras)
Son los "cerebros" del sistema. Validan las reglas de negocio antes de guardar cualquier dato.

GestorUsuario: Maneja el registro y login.

GestorDeCatalogo: Centraliza la gestión de Películas, Salas y Funciones. Incluye validaciones complejas (ej. evitar superposición de horarios en una sala).

GestorDeVentas: Maneja la transacción de compra, validación de asientos disponibles y generación de tickets.

🔹 Capa de Datos (Repositorios)
GestionDeElementos<T>: Una clase genérica que implementa las operaciones CRUD básicas (Agregar, Buscar, Eliminar) para cualquier entidad del sistema.

Repositorios Específicos: (RepositorioPelicula, RepositorioUsuario, etc.) Utilizan la clase genérica para almacenar las listas en memoria.

🔹 Capa de Modelo (Models)
Clases (Pelicula, Sala, Funcion, Reserva, Usuario) que representan los datos. Implementan interfaces para ser serializables a JSON e identificables por ID.

✨ Funcionalidades Clave
Para el Administrador:
Gestión de Películas: Alta y baja de títulos, géneros y duración.

Gestión de Salas: Creación de salas (2D/3D) y definición de capacidad.

Programación de Funciones: Asignación de una película a una sala en un horario específico.

Validación: El sistema impide crear una función si el horario choca con otra película en la misma sala (considerando la duración).

Seguridad de Datos: Bloqueo de eliminación de Salas o Películas si tienen reservas activas asociadas.

Para el Cliente:
Registro e Inicio de Sesión.

Visualización de Cartelera: Ver películas disponibles.

Compra de Entradas:

Selección de Función.

Mapa de Asientos: Visualización gráfica en consola de butacas libres [ 1 ] y ocupadas [ X ].

Cálculo automático de precios (con recargo para salas 3D).

Mis Reservas: Visualización de tickets comprados con detalle completo.

💾 Persistencia de Datos
El sistema guarda automáticamente todos los cambios en archivos locales .json ubicados en la raíz del proyecto:

usuarios.json

peliculas.json

salas.json

funciones.json

reservas.json

Nota: Se utiliza un sistema de IDs relacionales (UUID) para evitar la redundancia de datos y errores de referencia circular.

👥 Autores
Juan Ignacio Dominguez
Yanel Levis
Johana Hermida
Agostina Martinez

Trabajo Práctico Final Programación II
