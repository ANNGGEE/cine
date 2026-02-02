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

        // ================== SALAS ==================
        Sala sala1 = new Sala(1, "Sala 1", 50);
        Sala sala2 = new Sala(2, "Sala 2", 30);
        Sala sala3 = new Sala(3, "Sala 3", 30);
        Sala sala4 = new Sala(4, "Sala 4", 40);
        Sala sala5 = new Sala(5, "Sala 5", 25);

        // Crear butacas para cada sala
        crearButacas(sala1);
        crearButacas(sala2);
        crearButacas(sala3);
        crearButacas(sala4);
        crearButacas(sala5);

        // Guardar salas
        sala1 = salaService.crearSala(sala1);
        sala2 = salaService.crearSala(sala2);
        sala3 = salaService.crearSala(sala3);
        sala4 = salaService.crearSala(sala4);
        sala5 = salaService.crearSala(sala5);

        // ================== PELÍCULAS ==================
        Pelicula p1 = new Pelicula("Matrix", 136, "Ciencia Ficción");
        Pelicula p2 = new Pelicula("Titanic", 195, "Romance");
        Pelicula p3 = new Pelicula("El Padrino", 175, "Crimen");
        Pelicula p4 = new Pelicula("Killers", 145, "Acción");
        Pelicula p5 = new Pelicula("Trolls", 180, "Musical");
        Pelicula p6 = new Pelicula("Zootopia", 200, "Acción");
        Pelicula p7 = new Pelicula("Mentes Criminales, The Film", 250, "Acción");

        p1 = peliculaService.crearPelicula(p1);
        p2 = peliculaService.crearPelicula(p2);
        p3 = peliculaService.crearPelicula(p3);
        p4 = peliculaService.crearPelicula(p4);
        p5 = peliculaService.crearPelicula(p5);
        p6 = peliculaService.crearPelicula(p6);
        p7 = peliculaService.crearPelicula(p7);

        // ================== PROYECCIONES ==================
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(18, 0)), p1.getIdPelicula(), sala1.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(20, 30)), p2.getIdPelicula(), sala2.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(19, 0)), p3.getIdPelicula(), sala3.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(21, 0)), p4.getIdPelicula(), sala1.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(17, 30)), p5.getIdPelicula(), sala4.getIdSala());
        proyeccionService.crearProyeccion(new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(20, 0)), p6.getIdPelicula(), sala5.getIdSala());

        System.out.println("✅ Datos iniciales cargados correctamente: salas, butacas, películas y proyecciones");
    }

    // ================== MÉTODO AUXILIAR ==================
    private void crearButacas(Sala sala) {
        List<Butaca> butacas = new ArrayList<>();
        for (int i = 1; i <= sala.getCapacidad(); i++) {
            Butaca butaca = new Butaca();
            butaca.setNumero(i);
            butaca.setSala(sala);
            butacas.add(butaca);
        }
        sala.setButacas(butacas);
    }
}
