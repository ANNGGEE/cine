package com.example.cine.config;

import com.example.cine.entity.*;
import com.example.cine.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SalaService salaService;
    private final PeliculaService peliculaService;
    private final ProyeccionService proyeccionService;

    public DataInitializer(SalaService salaService,
                           PeliculaService peliculaService,
                           ProyeccionService proyeccionService) {
        this.salaService = salaService;
        this.peliculaService = peliculaService;
        this.proyeccionService = proyeccionService;
    }

    @Override
    public void run(String... args) throws Exception {

        // ================== CREAR SALAS + BUTACAS ==================
        Sala sala1 = crearSalaConButacas(1, "Sala 1", 50);
        Sala sala2 = crearSalaConButacas(2, "Sala 2", 30);
        Sala sala3 = crearSalaConButacas(3, "Sala 3", 30);
        Sala sala4 = crearSalaConButacas(4, "Sala 4", 40);
        Sala sala5 = crearSalaConButacas(5, "Sala 5", 25);

        // Guardar salas (y automáticamente sus butacas)
        sala1 = salaService.crearSala(sala1);
        sala2 = salaService.crearSala(sala2);
        sala3 = salaService.crearSala(sala3);
        sala4 = salaService.crearSala(sala4);
        sala5 = salaService.crearSala(sala5);

        // ================== CREAR PELICULAS ==================
        Pelicula p1 = peliculaService.crearPelicula(new Pelicula("Matrix", 136, "Ciencia Ficción"));
        Pelicula p2 = peliculaService.crearPelicula(new Pelicula("Titanic", 195, "Romance"));
        Pelicula p3 = peliculaService.crearPelicula(new Pelicula("El Padrino", 175, "Crimen"));
        Pelicula p4 = peliculaService.crearPelicula(new Pelicula("Killers", 145, "Accion"));
        Pelicula p5 = peliculaService.crearPelicula(new Pelicula("Trolls", 180, "Musical"));
        Pelicula p6 = peliculaService.crearPelicula(new Pelicula("Zootopia", 200, "Accion"));
        Pelicula p7 = peliculaService.crearPelicula(new Pelicula("Mentes Criminales, The Film", 250, "Accion"));

        // ================== CREAR PROYECCIONES ==================
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(18, 0)), p1.getIdPelicula(), sala1.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(20, 30)), p2.getIdPelicula(), sala2.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(19, 0)), p3.getIdPelicula(), sala3.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(21, 0)), p4.getIdPelicula(), sala1.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(17, 30)), p5.getIdPelicula(), sala4.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(20, 0)), p6.getIdPelicula(), sala5.getIdSala());

        System.out.println("✅ Datos iniciales cargados correctamente: salas, butacas, películas y proyecciones");
    }


    // Método helper para crear una sala y generar automáticamente sus butacas
    private Sala crearSalaConButacas(int numero, String descripcion, int capacidad) {
        Sala sala = new Sala(numero, descripcion, capacidad);

        List<Butaca> butacas = new ArrayList<>();
        for (int fila = 1; fila <= (capacidad / 10 + 1); fila++) {
            for (int num = 1; num <= 10 && butacas.size() < capacidad; num++) {
                Butaca b = new Butaca();
                char letraFila = (char) ('A' + fila - 1);
                b.setFila(String.valueOf(letraFila)); // fila como letra
                b.setNumero(num);
                b.setSala(sala);
                b.setPosicion(letraFila + String.valueOf(num)); // "A1", "A2", etc.
                butacas.add(b);
            }
        }
        sala.setButacas(butacas);
        return sala;
    }
}
