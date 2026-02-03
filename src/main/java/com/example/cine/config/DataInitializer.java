package com.example.cine.config;

import com.example.cine.entity.*;
import com.example.cine.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SalaRepository salaRepository;
    private final ButacaRepository butacaRepository;
    private final AsistenteRepository asistenteRepository;
    private final ProyeccionRepository proyeccionRepository;
    private final EntradaRepository entradaRepository;
    private final PeliculaRepository peliculaRepository;

    public DataInitializer(
            SalaRepository salaRepository,
            ButacaRepository butacaRepository,
            AsistenteRepository asistenteRepository,
            ProyeccionRepository proyeccionRepository,
            EntradaRepository entradaRepository,
            PeliculaRepository peliculaRepository
    ) {
        this.salaRepository = salaRepository;
        this.butacaRepository = butacaRepository;
        this.asistenteRepository = asistenteRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.entradaRepository = entradaRepository;
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public void run(String... args) {

        // Evitar duplicados al reiniciar
        if (salaRepository.count() > 0) return;

        // ==================== SALAS ====================
        Sala sala1 = new Sala();
        sala1.setNumero(1);
        sala1.setDescripcion("Sala Principal");
        sala1.setCapacidad(30);
        salaRepository.save(sala1);

        Sala sala2 = new Sala();
        sala2.setNumero(2);
        sala2.setDescripcion("Sala 3D");
        sala2.setCapacidad(20);
        salaRepository.save(sala2);

        // ==================== BUTACAS ==================
        crearButacas(sala1, 3, 10); // Filas A-C, 10 butacas por fila
        crearButacas(sala2, 2, 10); // Filas A-B, 10 butacas por fila

        // ==================== ASISTENTES ===============
        Asistente a1 = new Asistente();
        a1.setNombre("Juan Pérez");
        asistenteRepository.save(a1);

        Asistente a2 = new Asistente();
        a2.setNombre("María Gómez");
        asistenteRepository.save(a2);

        Asistente a3 = new Asistente();
        a3.setNombre("Luis Fernández");
        asistenteRepository.save(a3);

        Asistente a4 = new Asistente();
        a4.setNombre("Ana López");
        asistenteRepository.save(a4);

        // ==================== PELÍCULAS =================
        Pelicula p1 = new Pelicula();
        p1.setTitulo("Avengers: Endgame");
        p1.setDuracion(180);
        p1.setGenero("Acción");
        peliculaRepository.save(p1);

        Pelicula p2 = new Pelicula();
        p2.setTitulo("Jurassic Park");
        p2.setDuracion(127);
        p2.setGenero("Aventura");
        peliculaRepository.save(p2);

        Pelicula p3 = new Pelicula();
        p3.setTitulo("Titanic");
        p3.setDuracion(195);
        p3.setGenero("Drama");
        peliculaRepository.save(p3);

        // ==================== PROYECCIONES ===============
        Proyeccion pr1 = new Proyeccion();
        pr1.setFecha(LocalDate.now().plusDays(1));
        pr1.setHorario(LocalTime.of(18, 0));
        pr1.setSala(sala1);
        pr1.setPelicula(p1);
        proyeccionRepository.save(pr1);

        Proyeccion pr2 = new Proyeccion();
        pr2.setFecha(LocalDate.now().plusDays(1));
        pr2.setHorario(LocalTime.of(21, 0));
        pr2.setSala(sala1);
        pr2.setPelicula(p1);
        proyeccionRepository.save(pr2);

        Proyeccion pr3 = new Proyeccion();
        pr3.setFecha(LocalDate.now().plusDays(2));
        pr3.setHorario(LocalTime.of(20, 0));
        pr3.setSala(sala2);
        pr3.setPelicula(p2);
        proyeccionRepository.save(pr3);

        Proyeccion pr4 = new Proyeccion();
        pr4.setFecha(LocalDate.now().plusDays(3));
        pr4.setHorario(LocalTime.of(19, 30));
        pr4.setSala(sala2);
        pr4.setPelicula(p3);
        proyeccionRepository.save(pr4);

        // ==================== ENTRADAS ===================
        List<Butaca> butacasSala1 = butacaRepository.findBySala_IdSala(sala1.getIdSala());

        // Proyección 1 → parcialmente ocupada
        crearEntrada(a1, pr1, butacasSala1.get(0), false);
        crearEntrada(a2, pr1, butacasSala1.get(1), false);
        crearEntrada(a3, pr1, butacasSala1.get(2), true); // cancelada

        // Proyección 2 → casi llena
        for (int i = 0; i < 25; i++) {
            crearEntrada(a4, pr2, butacasSala1.get(i), false);
        }

        System.out.println("✅ Datos de ejemplo cargados correctamente");
    }

    // ==================== MÉTODOS AUXILIARES ====================
    private void crearButacas(Sala sala, int filas, int porFila) {
        for (char f = 'A'; f < 'A' + filas; f++) {
            for (int i = 1; i <= porFila; i++) {
                Butaca b = new Butaca();
                b.setSala(sala);
                b.setFila(String.valueOf(f));
                b.setNumero(i);
                b.setPosicion(f + String.valueOf(i));
                butacaRepository.save(b);
            }
        }
    }

    private void crearEntrada(Asistente a, Proyeccion p, Butaca b, boolean cancelada) {
        Entrada e = new Entrada();
        e.setAsistente(a);
        e.setProyeccion(p);
        e.setButaca(b);
        e.setPrecio(8.5);
        e.setFechacompra(LocalDateTime.now());
        e.setCancelada(cancelada);
        entradaRepository.save(e);
    }
}
