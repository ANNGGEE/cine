package com.example.cine.console;

import com.example.cine.dto.AsistenteDTO;
import com.example.cine.dto.EntradaDTO;
import com.example.cine.entity.Asistente;
import com.example.cine.mappers.EntradaMapper;
import com.example.cine.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MenuConsola implements CommandLineRunner {

    private final ProyeccionService proyeccionService;
    private final EntradaService entradaService;
    private final AsistenteService asistenteService;
    private final PeliculaService peliculaService;

    public MenuConsola(
            ProyeccionService proyeccionService,
            EntradaService entradaService,
            AsistenteService asistenteService,
            PeliculaService peliculaService
    ) {
        this.proyeccionService = proyeccionService;
        this.entradaService = entradaService;
        this.asistenteService = asistenteService;
        this.peliculaService = peliculaService;
    }

    @Override
    public void run(String... args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n===== 🎬 CINE APP =====");
            System.out.println("1️⃣ Crear asistente");
            System.out.println("2️⃣ Listar asistentes");
            System.out.println("3️⃣ Listar proyecciones");
            System.out.println("4️⃣ Listar películas");
            System.out.println("5️⃣ Comprar entrada");
            System.out.println("6️⃣ Cancelar entrada");
            System.out.println("7️⃣ Ver entradas de un asistente");
            System.out.println("8️⃣ Ver ocupación de una proyección");
            System.out.println("0️⃣ Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();

            try {

                switch (opcion) {

                    // ================= CREAR ASISTENTE =================
                    case 1 -> {
                        sc.nextLine(); // limpiar buffer
                        System.out.print("Nombre del asistente: ");
                        String nombre = sc.nextLine();

                        Asistente asistente = new Asistente();
                        asistente.setNombre(nombre);

                        asistenteService.crearAsistente(asistente);
                        System.out.println("✅ Asistente creado correctamente");
                    }

                    // ================= LISTAR ASISTENTES =================
                    case 2 -> {
                        List<AsistenteDTO> asistentes = asistenteService.obtenerTodosDTO();

                        if (asistentes.isEmpty()) {
                            System.out.println("📭 No hay asistentes");
                        } else {
                            asistentes.forEach(a ->
                                    System.out.println(
                                            "ID: " + a.getIdAsistente() +
                                                    " | Nombre: " + a.getNombre() +
                                                    " | Entradas: " + a.getTotalEntradas()
                                    )
                            );
                        }
                    }

                    // ================= LISTAR PROYECCIONES =================
                    case 3 -> proyeccionService.obtenerTodas()
                            .forEach(p -> System.out.println(
                                    "ID: " + p.getIdProyeccion() +
                                            " | " + p.getFecha() + " " + p.getHorario() +
                                            " | Sala: " + p.getSala().getNumero() +
                                            " | Película: " + p.getPelicula().getTitulo()
                            ));

                    // ================= LISTAR PELÍCULAS =================
                    case 4 -> {
                        var peliculas = peliculaService.obtenerTodas();

                        if (peliculas.isEmpty()) {
                            System.out.println("📭 No hay películas registradas");
                        } else {
                            peliculas.forEach(p ->
                                    System.out.println(
                                            "ID: " + p.getIdPelicula() +
                                                    " | Título: " + p.getTitulo() +
                                                    " | Género: " + p.getGenero() +
                                                    " | Duración: " + p.getDuracion() + " min"
                                    )
                            );
                        }
                    }

                    // ================= COMPRAR ENTRADA =================
                    case 5 -> {
                        System.out.print("ID asistente: ");
                        Long idAsistente = sc.nextLong();

                        System.out.print("ID proyección: ");
                        Long idProyeccion = sc.nextLong();

                        var proyeccion = proyeccionService.obtenerPorId(idProyeccion);

                        System.out.println("\n🎬 PROYECCIÓN");
                        System.out.println("Película: " + proyeccion.getPelicula().getTitulo());
                        System.out.println("Sala: " + proyeccion.getSala().getNumero());
                        System.out.println("Fecha: " + proyeccion.getFecha());
                        System.out.println("Hora: " + proyeccion.getHorario());

                        var libres = entradaService.asientosLibres(idProyeccion);

                        if (libres.isEmpty()) {
                            System.out.println("❌ No hay butacas disponibles");
                            break;
                        }

                        System.out.println("\n🎟️ Butacas libres:");
                        libres.forEach(b ->
                                System.out.println(
                                        "ID: " + b.getIdButaca() +
                                                " | " + b.getPosicion()
                                )
                        );

                        System.out.print("ID butaca: ");
                        Long idButaca = sc.nextLong();

                        System.out.print("Precio: ");
                        Double precio = sc.nextDouble();

                        entradaService.comprarEntrada(
                                idAsistente,
                                idProyeccion,
                                idButaca,
                                precio
                        );

                        System.out.println("✅ Entrada comprada correctamente");
                    }

                    // ================= CANCELAR ENTRADA =================
                    case 6 -> {
                        System.out.print("ID entrada: ");
                        Long idEntrada = sc.nextLong();

                        entradaService.cancelarEntrada(idEntrada);
                        System.out.println("✅ Entrada cancelada");
                    }

                    // ================= VER ENTRADAS POR ASISTENTE =================
                    case 7 -> {
                        System.out.print("ID asistente: ");
                        Long idAsistente = sc.nextLong();

                        List<EntradaDTO> entradas = entradaService
                                .entradasPorAsistente(idAsistente)
                                .stream()
                                .map(EntradaMapper::toDTO)
                                .toList();

                        if (entradas.isEmpty()) {
                            System.out.println("📭 No tiene entradas");
                        } else {
                            entradas.forEach(e ->
                                    System.out.println(
                                            "🎟️ " + e.getTituloPelicula() +
                                                    " | Sala: " + e.getSala() +
                                                    " | " + e.getFecha() + " " + e.getHorario() +
                                                    " | Butaca: " + e.getFila() + e.getNumeroButaca() +
                                                    (e.getCancelada() ? " ❌ CANCELADA" : "")
                                    )
                            );
                        }
                    }

                    // ================= OCUPACIÓN =================
                    case 8 -> {
                        System.out.print("ID proyección: ");
                        Long idProyeccion = sc.nextLong();

                        System.out.println("👥 Ocupación: " +
                                entradaService.ocupacionProyeccion(idProyeccion));
                    }

                    case 0 -> System.out.println("👋 Saliendo...");

                    default -> System.out.println("❌ Opción inválida");
                }

            } catch (RuntimeException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }
}
