package com.example.cine.config;

import com.example.cine.entity.*;
import com.example.cine.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

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
    public void run(String... args) {

        // ================== CREAR SALAS ==================
        Sala sala1 = new Sala();
        sala1.setNumero(1);
        sala1.setDescripcion("Sala 1");
        sala1.setCapacidad(50);

        Sala sala2 = new Sala();
        sala2.setNumero(2);
        sala2.setDescripcion("Sala 2");
        sala2.setCapacidad(30);

        Sala sala3 = new Sala();
        sala3.setNumero(3);
        sala3.setDescripcion("Sala 3");
        sala3.setCapacidad(30);

        Sala sala4 = new Sala();
        sala4.setNumero(4);
        sala4.setDescripcion("Sala 4");
        sala4.setCapacidad(40);

        Sala sala5 = new Sala();
        sala5.setNumero(5);
        sala5.setDescripcion("Sala 5");
        sala5.setCapacidad(25);

        // Guardamos salas + butacas
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

        // ================== CREAR PROYECCIONES ==================
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(18, 0)),
                p1.getIdPelicula(),
                sala1.getIdSala()
        );
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(1), LocalTime.of(20, 30)),
                p2.getIdPelicula(),
                sala2.getIdSala()
        );
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(19, 0)),
                p3.getIdPelicula(),
                sala3.getIdSala()
        );
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(2), LocalTime.of(21, 0)),
                p4.getIdPelicula(),
                sala1.getIdSala()
        );
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(17, 30)),
                p5.getIdPelicula(),
                sala4.getIdSala()
        );
        proyeccionService.crearProyeccion(
                new Proyeccion(LocalDate.now().plusDays(3), LocalTime.of(20, 0)),
                p6.getIdPelicula(),
                sala5.getIdSala()
        );

        System.out.println("✅ Datos iniciales cargados: salas, butacas, películas y proyecciones");
    }
}
