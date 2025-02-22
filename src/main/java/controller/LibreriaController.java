package controller;

import dao.AutorDAO;
import dao.EditorialDAO;
import dao.LibreriaDAO;
import dao.LibroDAO;
import model.Autor;
import model.Editorial;
import model.Libreria;
import model.Libro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibreriaController {

    private AutorDAO autorDAO;
    private EditorialDAO editorialDAO;
    private LibreriaDAO libreriaDAO;
    private LibroDAO libroDAO;
    Scanner scanner = new Scanner(System.in);

    public LibreriaController() {
        autorDAO = new AutorDAO();
        editorialDAO = new EditorialDAO();
        libreriaDAO = new LibreriaDAO();
        libroDAO = new LibroDAO();
    }

    public void opciones() {
        System.out.println("\n--- Libreria ---");
        System.out.println("1. Dar de alta a un autor");
        System.out.println("2. Dar de alta una editorial");
        System.out.println("3. Dar de alta un libro");
        System.out.println("4. Dar de alta una libreria");
        System.out.println("5. Mostrar todos los libros dados de alta, con su editorial y su autor");
        System.out.println("6.Mostrar todos los autores dados de alta, con sus libros asociados");
        System.out.println("7.Mostrar todas las librerías, con solamente sus libros asociados");
        System.out.println("8.Mostrar todos los libros dados de alta, y en la librería en la que están.");
        System.out.println("9.Salir");
        System.out.print("Elige una opción: ");
    }

    public void menu() {
        int option;
        Scanner scanner = new Scanner(System.in);

        do {
            opciones();
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    agregarAutor();
                    break;
                case 2:
                    agregarEditorial();
                    break;
                case 3:
                    agregarLibro();
                    break;
                case 4:
                    agregarLibreria();
                    break;
                case 5:
                    listarLibros();
                    break;
                case 6:
                    listarAutores();
                    break;
                case 7:
                    listarLibrerias();
                    break;
                case 8:
                    todosLibros();
                    break;
                case 9:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción invalida. Vuelva a intentarlo");
            }
        } while (option != 9);
    }

    private void todosLibros() {
        List<Libro> libros = libroDAO.obtenerTodosLibros();

        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo() + " | Precio: " + libro.getPrecio());

            List<Libreria> librerias = libreriaDAO.obtenerLibreriasPorLibro(libro);

            if (librerias != null && !librerias.isEmpty()) {
                System.out.print("   - En las siguientes librerías: ");
                for (Libreria libreria : librerias) {
                }
                System.out.println();
            } else {
                System.out.println("   - No está asociado a ninguna librería.");
            }
        }

    }

    private void listarLibrerias() {
        List<Libreria> librerias = libreriaDAO.obtenerTodasLibrerias();
        for (Libreria libreria : librerias) {
            System.out.println("===============================================");
            System.out.println("Librería: " + libreria.getNombre());
            System.out.println("Dueño: " + libreria.getNombreDuenio());
            System.out.println("Dirección: " + libreria.getDireccion());
            System.out.println("===============================================");
            List<Libro> libros = libreria.getLibros();

            if (libros != null && !libros.isEmpty()) {
                System.out.println("Libros disponibles:");
                System.out.println("-----------------------------------------------");
                for (Libro libro : libros) {
                    System.out.println("   - Título: " + libro.getTitulo() + " | Precio: " + libro.getPrecio());
                }
                System.out.println("-----------------------------------------------");
            } else {
                System.out.println("Esta librería no tiene libros asociados.");
            }


        }

    }

    private void listarAutores() {
        List<Autor> autores = autorDAO.obtenerTodosAutor();
        for (Autor autor : autores) {
            System.out.println("Autor: " + autor.getNombre() + " " + autor.getApellido());

            // Obtener los libros asociados a este autor
            List<Libro> libros = libroDAO.obtenerLibrosPorAutor(autor.getId());

            if (libros != null && !libros.isEmpty()) {
                System.out.println("Libros asociados:");
                for (Libro libro : libros) {
                    System.out.println("   - Título: " + libro.getTitulo() + ", Precio: " + libro.getPrecio());
                }
                System.out.println("---------------------------");
            } else {
                System.out.println("Este autor no tiene libros asociados.");
            }
        }

    }

    private void listarLibros() {
        List<Libro> libros = libroDAO.obtenerTodosLibros();
        System.out.println("Lista de libros registrados:");
        System.out.println("--------------------------------");
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Precio: " + libro.getPrecio());
            System.out.println("Autor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellido());
            System.out.println("Editorial: " + libro.getEditorial().getNombre());
            System.out.println("--------------------------------");
        }
    }

    private void agregarLibreria() {

        System.out.println("Introduce el nombre de la librería:");
        String nombre = scanner.nextLine();

        System.out.println("Introduce el nombre del dueño:");
        String duenio = scanner.nextLine();

        System.out.println("Introduce la dirección de la librería:");
        String direccion = scanner.nextLine();

        List<Libro> librosDisponibles = libroDAO.obtenerTodosLibros();
        System.out.println("Selecciona los 4 o más libros que deseas asociar a la librería:");
        for(Libro item : librosDisponibles){
            System.out.println("El nombre del libro es: " + item.getTitulo() + " y su id es: " + item.getId());
        }

        List<Libro> librosSeleccionados = new ArrayList<>();

        while (librosSeleccionados.size() < 4) {
            System.out.println("Introduce el número del libro que deseas agregar (de 1 a " + librosDisponibles.size() + "):");
            int seleccion = scanner.nextInt();

            // Validar que la selección esté dentro del rango
            if (seleccion > 0 && seleccion <= librosDisponibles.size()) {
                Libro libroSeleccionado = librosDisponibles.get(seleccion - 1);
                if (!librosSeleccionados.contains(libroSeleccionado)) {
                    librosSeleccionados.add(libroSeleccionado);
                } else {
                    System.out.println("Este libro ya ha sido seleccionado. Elige otro.");
                }
            } else {
                System.out.println("Selección inválida. Por favor, elige un número entre 1 y " + librosDisponibles.size() + ".");
            }

            // Si ya tiene 4 libros seleccionados, no seguir pidiendo más
            if (librosSeleccionados.size() == 4) {
                break;
            }
        }

        Libreria libreria = new Libreria();
        libreria.setNombre(nombre);
        libreria.setNombreDuenio(duenio);
        libreria.setDireccion(direccion);

        // Paso 5: Asociar los libros seleccionados a la librería
        libreria.setLibros(librosSeleccionados);

        // Paso 6: Guardar la librería en la base de datos
        libreriaDAO.crearLibreria(libreria);
        System.out.println("Librería creada con éxito.");

    }

    private void agregarLibro() {
        double precio = 0;

        // Mostrar los autores disponibles
        System.out.println("Lista de autores disponibles:");
        for (Autor item : autorDAO.getAllAutores()) {
            System.out.println("El nombre del autor es: " + item.getNombre() + " y su id es: " + item.getId());
        }
        System.out.println("Introduce el id del autor al que quieres asociar este libro:");
        int idA = scanner.nextInt();

        // Buscar el autor por el id
        Autor autor = autorDAO.getAutor(idA);
        if (autor == null) {
            System.out.println("No se encontró un autor con ese id.");
            return;
        }

        // Mostrar las editoriales disponibles
        System.out.println("Lista de editoriales disponibles:");
        for (Editorial item1 : editorialDAO.getAllEditorial()) {
            System.out.println("El nombre de la editorial es: " + item1.getNombre() + " y su id es: " + item1.getId());
        }
        System.out.println("Introduce el id de la editorial al que quieres asociar este libro:");
        int idE = scanner.nextInt();

        // Buscar la editorial por el id
        Editorial editorial = editorialDAO.getEditorial(idE);
        if (editorial == null) {
            System.out.println("No se encontró una editorial con ese id.");
            return;
        }

        // Solicitar el título y precio del libro
        System.out.println("Introduce el titulo del libro:");
        scanner.nextLine();  // Limpiar el buffer
        String titulo = scanner.nextLine();

        System.out.println("Introduce el precio del libro:");
        precio = scanner.nextDouble();

        libroDAO.crearLibro(new Libro(titulo,precio,autor,editorial));

        System.out.println("¡Libro creado con éxito!");
    }

    private void agregarEditorial() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre de la editorial:");
        String nombre = scanner.next();
        System.out.println("Introduce su dirección:");
        String direccion = scanner.next();
        editorialDAO.crearEditorial(new Editorial(nombre,direccion));
    }

    private void agregarAutor() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNacimiento = null;

        System.out.println("Introduce el nombre:");
        String nombre = scanner.next();
        System.out.println("Introduce el apellido:");
        String apellido = scanner.next();
        System.out.println("Introduce el fecha de nacimiento:");
        String nacimiento = scanner.next();

        try {
            fechaNacimiento = formato.parse(nacimiento);
            autorDAO.crearAutor(new Autor(nombre, apellido, fechaNacimiento));
            System.out.println("Autor agregado exitosamente.");
        } catch (ParseException e) {
            System.out.println("Formato de fecha incorrecto. Por favor, introduce la fecha en el formato dd/MM/yyyy.");

        }
    }
}
