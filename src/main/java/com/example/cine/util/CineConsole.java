package com.example.cine.util;

import com.example.cine.entity.*;
import com.example.cine.services.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class CineConsole {

    private final Scanner scanner = new Scanner(System.in);

    private final AsistenteService asistenteService;
    private final SalaService salaService;
    private final PeliculaService peliculaService;
    private final ProyeccionService proyeccionService;
    private final EntradaService entradaService;

    public CineConsole(AsistenteService asistenteService, SalaService salaService,
                       PeliculaService peliculaService, ProyeccionService proyeccionService,
                       EntradaService entradaService) {
        this.asistenteService = asistenteService;
        this.salaService = salaService;
        this.peliculaService = peliculaService;
        this.proyeccionService = proyeccionService;
        this.entradaService = entradaService;
    }

    public void run() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = Integer.parseInt(scanner.nextLine());
            switch (opcion) {
                case 1 -> crearAsistente();
                case 2 -> listarAsistentes();
                case 3 -> crearSala();
                case 4 -> listarSalas();
                case 5 -> crearPelicula();
                case 6 -> listarPeliculas();
                case 7 -> crearProyeccion();
                case 8 -> listarProyecciones();
                case 9 -> comprarEntrada();
                case 10 -> listarEntradasPorAsistente();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n=== MENÚ DEL CINE ===");
        System.out.println("1. Crear Asistente");
        System.out.println("2. Listar Asistentes");
        System.out.println("3. Crear Sala");
        System.out.println("4. Listar Salas");
        System.out.println("5. Crear Película");
        System.out.println("6. Listar Películas");
        System.out.println("7. Crear Proyección");
        System.out.println("8. Listar Proyecciones");
        System.out.println("9. Comprar Entrada");
        System.out.println("10. Listar Entradas por Asistente");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }

    // ================== MÉTODOS ==================
    private void crearAsistente() {
        System.out.print("Nombre del asistente: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Asistente a = new Asistente();
        a.setNombre(nombre);
        asistenteService.crearAsistente(a);
        System.out.println("Asistente creado con éxito");
    }

    private void listarAsistentes() {
        List<Asistente> lista = asistenteService.obtenerTodos();
        System.out.println("--- LISTA DE ASISTENTES ---");
        lista.forEach(a -> System.out.println(a.getId() + ": " + a.getNombre() + " (" + a.getEmail() + ")"));
    }

    private void crearSala() {
        System.out.print("Nombre de la sala: ");
        String nombre = scanner.nextLine();
        System.out.print("Número de butacas: ");
        int numButacas = Integer.parseInt(scanner.nextLine());
        Sala s = new Sala();
        s.setNombre(nombre);
        s.setNumButaca(numButacas);
        salaService.crearSala(s);
        System.out.println("Sala creada con éxito");
    }

    private void listarSalas() {
        List<Sala> salas = salaService.obtenerTodas();
        System.out.println("--- LISTA DE SALAS ---");
        salas.forEach(s -> System.out.println(s.getId() + ": " + s.getNombre() + " (" + s.getNumButaca() + " butacas)"));
    }

    private void crearPelicula() {
        System.out.print("Título de la película: ");
        String titulo = scanner.nextLine();
        System.out.print("Duración en minutos: ");
        int duracion = Integer.parseInt(scanner.nextLine());
        Pelicula p = new Pelicula();
        p.setTitulo(titulo);
        p.setDuracion(duracion);
        peliculaService.crearPelicula(p);
        System.out.println("Película creada con éxito");
    }

    private void listarPeliculas() {
        List<Pelicula> peliculas = peliculaService.obtenerTodas();
        System.out.println("--- LISTA DE PELÍCULAS ---");
        peliculas.forEach(p -> System.out.println(p.getId() + ": " + p.getTitulo() + " (" + p.getDuracion() + " min)"));
    }

    private void crearProyeccion() {
        System.out.print("ID de la película: ");
        Long idPeli = Long.parseLong(scanner.nextLine());
        System.out.print("ID de la sala: ");
        Long idSala = Long.parseLong(scanner.nextLine());
        System.out.print("Fecha (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        Proyeccion p = new Proyeccion();
        p.setFecha(fecha);
        p.setHorario(hora);
        proyeccionService.crearProyeccion(p, idPeli, idSala);
        System.out.println("Proyección creada con éxito");
    }

    private void listarProyecciones() {
        List<Proyeccion> proyecciones = proyeccionService.obtenerTodas();
        System.out.println("--- LISTA DE PROYECCIONES ---");
        proyecciones.forEach(p -> System.out.println(
                p.getId() + ": " + p.getPelicula().getTitulo() + " en " +
                        p.getSala().getNombre() + " el " + p.getFecha() + " a las " + p.getHorario()
        ));
    }

    private void comprarEntrada() {
        System.out.print("ID del asistente: ");
        Long idAsistente = Long.parseLong(scanner.nextLine());
        System.out.print("ID de la proyección: ");
        Long idProy = Long.parseLong(scanner.nextLine());
        System.out.print("ID de la butaca: ");
        Long idButaca = Long.parseLong(scanner.nextLine());
        System.out.print("Precio: ");
        Double precio = Double.parseDouble(scanner.nextLine());

        entradaService.comprarEntrada(idAsistente, idProy, idButaca, precio);
        System.out.println("Entrada comprada con éxito");
    }

    private void listarEntradasPorAsistente() {
        System.out.print("ID del asistente: ");
        Long idAsistente = Long.parseLong(scanner.nextLine());
        List<Entrada> entradas = entradaService.entradasPorAsistente(idAsistente);
        System.out.println("--- ENTRADAS DEL ASISTENTE ---");
        entradas.forEach(e -> System.out.println(
                "Entrada " + e.getId() + ": " +
                        e.getProyeccion().getPelicula().getTitulo() + " - Butaca " + e.getButaca().getId()
        ));
    }
}
