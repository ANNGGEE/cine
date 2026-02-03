package com.example.cine.config;

import com.example.cine.entity.*;
import com.example.cine.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

        // ==================== SALA ====================
        Sala sala = new Sala();
        sala.setDescripcion("Sala Principal");
        sala.setNumero(1);
        sala.setCapacidad(20);
        salaRepository.save(sala);

        // ==================== BUTACAS =================
        for (int i = 1; i <= sala.getCapacidad(); i++) {
            Butaca b = new Butaca();
            b.setSala(sala);
            b.setNumero(i);
            b.setFila("A");
            b.setPosicion("A" + i);
            butacaRepository.save(b);
        }

        // ==================== ASISTENTES ===============
        Asistente a1 = new Asistente();
        a1.setNombre("Juan Pérez");
        asistenteRepository.save(a1);

        Asistente a2 = new Asistente();
        a2.setNombre("María Gómez");
        asistenteRepository.save(a2);

        // ==================== PELÍCULAS =================
        Pelicula p1 = new Pelicula();
        p1.setTitulo("Avengers: Endgame");
        p1.setGenero("Acción");
        p1.setDuracion(180);
        peliculaRepository.save(p1);

        Pelicula p2 = new Pelicula();
        p2.setTitulo("Jurassic Park");
        p2.setGenero("Aventura");
        p2.setDuracion(127);
        peliculaRepository.save(p2);

        // ==================== PROYECCIONES ===============
        Proyeccion pr1 = new Proyeccion();
        pr1.setSala(sala);
        pr1.setPelicula(p1);
        pr1.setFecha(LocalDate.now().plusDays(1));
        pr1.setHorario(LocalTime.of(18, 0));
        proyeccionRepository.save(pr1);

        Proyeccion pr2 = new Proyeccion();
        pr2.setSala(sala);
        pr2.setPelicula(p2);
        pr2.setFecha(LocalDate.now().plusDays(1));
        pr2.setHorario(LocalTime.of(21, 0));
        proyeccionRepository.save(pr2);

        // ==================== ENTRADAS ===================
        Butaca b1 = butacaRepository.findBySala_IdSala(sala.getIdSala()).get(0);
        Butaca b2 = butacaRepository.findBySala_IdSala(sala.getIdSala()).get(1);

        Entrada e1 = new Entrada();
        e1.setAsistente(a1);
        e1.setProyeccion(pr1);
        e1.setButaca(b1);
        e1.setPrecio(8.0);
        e1.setFechacompra(LocalDateTime.now());
        e1.setCancelada(false);
        entradaRepository.save(e1);

        Entrada e2 = new Entrada();
        e2.setAsistente(a1);
        e2.setProyeccion(pr1);
        e2.setButaca(b2);
        e2.setPrecio(8.0);
        e2.setFechacompra(LocalDateTime.now());
        e2.setCancelada(false);
        entradaRepository.save(e2);

        System.out.println("✅ Datos iniciales cargados correctamente");
    }
}
