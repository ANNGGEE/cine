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
    public void run(String... args) throws Exception {
        // ================== SALAS ==================
        Sala sala1 = new Sala();
        sala1.setNumero(1);
        sala1.setDescripcion("Sala 1");
        sala1.setCapacidad(50);
        sala1.setNumButaca(50);
        salaService.crearSala(sala1);

        Sala sala2 = new Sala();
        sala2.setNumero(2);
        sala2.setDescripcion("Sala 2");
        sala2.setCapacidad(30);
        sala2.setNumButaca(30);
        salaService.crearSala(sala2);

        Sala sala3 = new Sala();
        sala2.setNumero(3);
        sala2.setDescripcion("Sala 3");
        sala2.setCapacidad(30);
        sala2.setNumButaca(30);
        salaService.crearSala(sala3);

        Sala sala4 = new Sala();
        sala2.setNumero(4);
        sala2.setDescripcion("Sala 4");
        sala2.setCapacidad(40);
        sala2.setNumButaca(40);
        salaService.crearSala(sala4);

        Sala sala5 = new Sala();
        sala2.setNumero(5);
        sala2.setDescripcion("Sala 5");
        sala2.setCapacidad(25);
        sala2.setNumButaca(25);
        salaService.crearSala(sala5);

        // ================== PELICULAS ==================
        Pelicula p1 = new Pelicula();
        p1.setTitulo("Matrix");
        p1.setDuracion(136);
        p1.setGenero("Ciencia Ficción");
        peliculaService.crearPelicula(p1);

        Pelicula p2 = new Pelicula();
        p2.setTitulo("Titanic");
        p2.setDuracion(195);
        p2.setGenero("Romance");
        peliculaService.crearPelicula(p2);

        Pelicula p3 = new Pelicula();
        p3.setTitulo("El Padrino");
        p3.setDuracion(175);
        p3.setGenero("Crimen");
        peliculaService.crearPelicula(p3);

        Pelicula p4 = new Pelicula();
        p4.setTitulo("Killers");
        p4.setDuracion(145);
        p4.setGenero("Accion");
        peliculaService.crearPelicula(p4);

        Pelicula p5 = new Pelicula();
        p5.setTitulo("Trolls");
        p5.setDuracion(180);
        p5.setGenero("Musical");
        peliculaService.crearPelicula(p5);

        Pelicula p6 = new Pelicula();
        p6.setTitulo("Zootopia");
        p6.setDuracion(200);
        p6.setGenero("Accion");
        peliculaService.crearPelicula(p6);

        Pelicula p7 = new Pelicula();
        p7.setTitulo("Mentes Criminales, The Film");
        p7.setDuracion(250);
        p7.setGenero("Accion");
        peliculaService.crearPelicula(p7);

        // ================== PROYECCIONES ==================
        Proyeccion pro1 = new Proyeccion();
        pro1.setFecha(LocalDate.now().plusDays(1));
        pro1.setHorario(LocalTime.of(18, 0));
        proyeccionService.crearProyeccion(pro1, p1.getIdPelicula(), sala1.getIdSala());

        Proyeccion pro2 = new Proyeccion();
        pro2.setFecha(LocalDate.now().plusDays(1));
        pro2.setHorario(LocalTime.of(20, 30));
        proyeccionService.crearProyeccion(pro2, p2.getIdPelicula(), sala2.getIdSala());

        Proyeccion pro3 = new Proyeccion();
        pro3.setFecha(LocalDate.now().plusDays(2));
        pro3.setHorario(LocalTime.of(19, 0));
        proyeccionService.crearProyeccion(pro3, p3.getIdPelicula(), sala3.getIdSala());

        Proyeccion pro4 = new Proyeccion();
        pro4.setFecha(LocalDate.now().plusDays(2));
        pro4.setHorario(LocalTime.of(21, 0));
        proyeccionService.crearProyeccion(pro4, p4.getIdPelicula(), sala1.getIdSala());

        Proyeccion pro5 = new Proyeccion();
        pro5.setFecha(LocalDate.now().plusDays(3));
        pro5.setHorario(LocalTime.of(17, 30));
        proyeccionService.crearProyeccion(pro5, p5.getIdPelicula(), sala4.getIdSala());

        Proyeccion pro6 = new Proyeccion();
        pro6.setFecha(LocalDate.now().plusDays(3));
        pro6.setHorario(LocalTime.of(20, 0));
        proyeccionService.crearProyeccion(pro6, p6.getIdPelicula(), sala5.getIdSala());

        System.out.println("✅ Datos iniciales cargados: salas, películas y proyecciones");
    }
}
