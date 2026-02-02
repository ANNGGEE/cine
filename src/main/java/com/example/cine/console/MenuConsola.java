package com.example.cine.console;

import com.example.cine.entity.Asistente;
import com.example.cine.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuConsola implements CommandLineRunner {

    private final ProyeccionService proyeccionService;
    private final EntradaService entradaService;
    private final AsistenteService asistenteService;

    public MenuConsola(
            ProyeccionService proyeccionService,
            EntradaService entradaService,
            AsistenteService asistenteService
    ) {
        this.proyeccionService = proyeccionService;
        this.entradaService = entradaService;
        this.asistenteService = asistenteService;
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n ===== CINE APP =====");
            System.out.println("1️  Crear asistente");
            System.out.println("2️  Listar asistentes");
            System.out.println("3️  Listar proyecciones");
            System.out.println("4️  Comprar entrada");
            System.out.println("5️  Cancelar entrada");
            System.out.println("6️  Ver entradas de un asistente");
            System.out.println("7️  Ver ocupación de una proyección");
            System.out.println("0️  Salir");
            System.out.print("Opción: ");

            opcion = sc.nextInt();

            switch (opcion) {

                case 1 -> {
                    System.out.print("Nombre del asistente: ");
                    sc.nextLine();
                    String nombre = sc.nextLine();

                    Asistente asistente = new Asistente();
                    asistente.setNombre(nombre);

                    asistenteService.crearAsistente(asistente);

                    System.out.println("✅ Asistente creado");
                }

                case 2 -> asistenteService.obtenerTodos()
                        .forEach(System.out::println);

                case 3 -> proyeccionService.obtenerTodas()
                        .forEach(p -> System.out.println(
                                "ID: " + p.getIdProyeccion() +
                                        ", Fecha: " + p.getFecha() +
                                        ", Horario: " + p.getHorario() +
                                        ", Sala: " + p.getSala().getNumero() +
                                        ", Pelicula: " + p.getPelicula().getTitulo()
                        ));

                case 4 -> {
                    System.out.print("ID asistente: ");
                    Long idAsistente = sc.nextLong();

                    System.out.print("ID proyección: ");
                    Long idProyeccion = sc.nextLong();

                    var proyeccion = proyeccionService.obtenerPorId(idProyeccion);

                    System.out.println("\n🎬 PROYECCIÓN SELECCIONADA");
                    System.out.println("Película: " + proyeccion.getPelicula().getTitulo());
                    System.out.println("Sala nº: " + proyeccion.getSala().getNumero());
                    System.out.println("Fecha: " + proyeccion.getFecha());
                    System.out.println("Hora: " + proyeccion.getHorario());

                    var butacasLibres = entradaService.asientosLibres(idProyeccion);

                    if (butacasLibres.isEmpty()) {
                        System.out.println("❌ No hay butacas disponibles para esta proyección");
                        break;
                    }

                    System.out.println("🎟️ Butacas disponibles:");
                    butacasLibres.forEach(b ->
                            System.out.println(
                                    "ID: " + b.getIdButaca() +
                                            " | Fila: " + b.getFila() +
                                            " | Número: " + b.getNumero()
                            )
                    );

                    System.out.print("Elige ID butaca: ");
                    Long idButaca = sc.nextLong();

                    System.out.print("Precio: ");
                    Double precio = sc.nextDouble();

                    entradaService.comprarEntrada(
                            idAsistente,
                            idProyeccion,
                            idButaca,
                            precio
                    );

                    System.out.println("✅ Entrada comprada");
                }

                case 5 -> {
                    System.out.print("ID entrada: ");
                    Long idEntrada = sc.nextLong();
                    entradaService.cancelarEntrada(idEntrada);
                    System.out.println("Entrada cancelada");
                }

                case 6 -> {
                    System.out.print("ID asistente: ");
                    Long idAsistente = sc.nextLong();
                    entradaService.entradasPorAsistente(idAsistente)
                            .forEach(System.out::println);
                }

                case 7 -> {
                    System.out.print("ID proyección: ");
                    Long idProyeccion = sc.nextLong();
                    System.out.println("Ocupación: " +
                            entradaService.ocupacionProyeccion(idProyeccion));
                }

                case 0 -> System.out.println("Saliendo...");

                default -> System.out.println("Opción inválida");
            }

        } while (opcion != 0);
    }
}
