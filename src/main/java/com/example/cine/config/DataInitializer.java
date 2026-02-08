package com.example.cine.config;

import com.example.cine.entity.*;
import com.example.cine.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

        if (salaRepository.count() > 0) return;

        // ==================== SALAS ====================
        Sala sala1 = crearSala(1, "Sala Principal", 30);
        Sala sala2 = crearSala(2, "Sala 3D", 20);
        Sala sala3 = crearSala(3, "Sala VIP", 15);

        // ==================== BUTACAS ==================
        crearButacas(sala1);
        crearButacas(sala2);
        crearButacas(sala3);

        // ==================== ASISTENTES ===============
        String[] nombres = {
                "Juan Pérez", "María Gómez", "Luis Fernández",
                "Ana López", "Carlos Ruiz", "Sofía Martínez",
                "Ana Rodríguez", "Juanjo Velero"
        };

        for (String nombre : nombres) {
            Asistente a = new Asistente();
            a.setNombre(nombre);
            asistenteRepository.save(a);
        }

        List<Asistente> asistentes = asistenteRepository.findAll();

        // ==================== PELÍCULAS =================
        Pelicula p1 = crearPelicula("Avengers: Endgame", 180, "Acción");
        Pelicula p2 = crearPelicula("Jurassic Park", 127, "Aventura");
        Pelicula p3 = crearPelicula("Titanic", 195, "Drama");
        Pelicula p4 = crearPelicula("Inception", 148, "Ciencia Ficción");
        Pelicula p5 = crearPelicula("Coco", 105, "Animación");

        // ==================== PROYECCIONES =================
        // Todas FUTURAS
        crearProyeccion(p1, sala1, LocalDate.now().plusDays(1), LocalTime.of(18, 0));
        crearProyeccion(p1, sala1, LocalDate.now().plusDays(2), LocalTime.of(21, 0));
        crearProyeccion(p2, sala2, LocalDate.now().plusDays(3), LocalTime.of(20, 0));
        crearProyeccion(p3, sala3, LocalDate.now().plusDays(4), LocalTime.of(19, 30));
        crearProyeccion(p4, sala1, LocalDate.now().plusDays(5), LocalTime.of(21, 30));
        crearProyeccion(p4, sala1, LocalDate.now().plusDays(6), LocalTime.of(22, 0));
        crearProyeccion(p5, sala2, LocalDate.now().plusDays(7), LocalTime.of(19, 0));
        crearProyeccion(p5, sala3, LocalDate.now().plusDays(8), LocalTime.of(20, 30));

        // ==================== ENTRADAS ===================
        List<Proyeccion> proyecciones = proyeccionRepository.findAll();

        for (Proyeccion pr : proyecciones) {
            List<Butaca> butacas = butacaRepository.findBySala_IdSala(pr.getSala().getIdSala());

            // 2 entradas activas
            for (int i = 0; i < Math.min(2, butacas.size()); i++) {
                crearEntrada(asistentes.get(i), pr, butacas.get(i), false);
            }

            // 1 entrada cancelada
            if (!butacas.isEmpty()) {
                crearEntrada(asistentes.get(0), pr, butacas.get(butacas.size() - 1), true);
            }
        }

        System.out.println("✅ DataInitializer COMPLETO cargado correctamente con proyecciones FUTURAS");
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private Sala crearSala(int numero, String descripcion, int capacidad) {
        Sala s = new Sala();
        s.setNumero(numero);
        s.setDescripcion(descripcion);
        s.setCapacidad(capacidad);
        return salaRepository.save(s);
    }

    private void crearButacas(Sala sala) {
        int columnas = 10;
        char fila = 'A';
        int creadas = 0;

        while (creadas < sala.getCapacidad()) {
            for (int n = 1; n <= columnas && creadas < sala.getCapacidad(); n++) {
                Butaca b = new Butaca();
                b.setSala(sala);
                b.setFila(String.valueOf(fila));
                b.setNumero(n);
                b.setPosicion(fila + String.valueOf(n));
                butacaRepository.save(b);
                creadas++;
            }
            fila++;
        }
    }

    private Pelicula crearPelicula(String titulo, int duracion, String genero) {
        Pelicula p = new Pelicula();
        p.setTitulo(titulo);
        p.setDuracion(duracion);
        p.setGenero(genero);
        return peliculaRepository.save(p);
    }

    private Proyeccion crearProyeccion(Pelicula p, Sala s, LocalDate fecha, LocalTime horario) {
        Proyeccion pr = new Proyeccion();
        pr.setPelicula(p);
        pr.setSala(s);
        pr.setFecha(fecha);
        pr.setHorario(horario);
        return proyeccionRepository.save(pr);
    }

    private void crearEntrada(Asistente a, Proyeccion p, Butaca b, boolean cancelada) {
        Entrada e = new Entrada();
        e.setAsistente(a);
        e.setProyeccion(p);
        e.setButaca(b);
        e.setPrecio(8.5);
        e.setFechacompra(java.time.LocalDateTime.now());
        e.setCancelada(cancelada);
        entradaRepository.save(e);
    }
}
