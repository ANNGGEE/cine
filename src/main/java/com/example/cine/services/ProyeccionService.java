package com.example.cine.services;

import com.example.cine.entity.Pelicula;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.PeliculaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProyeccionService{

    private ProyeccionRepository proyeccionRepository;
    private EntradaRepository entradaRepository;
    private SalaRepository salaRepository;
    private PeliculaRepository peliculaRepository;

    public ProyeccionService(ProyeccionRepository proyeccionRepository, EntradaRepository entradaRepository, SalaRepository salaRepository, PeliculaRepository peliculaRepository) {
        this.proyeccionRepository = proyeccionRepository;
        this.entradaRepository = entradaRepository;
        this.salaRepository = salaRepository;
        this.peliculaRepository = peliculaRepository;
    }

    // =================== CREAR PROYECCIÓN ===========================
    public Proyeccion crearProyeccion(Proyeccion proyeccion){
        // Validamos que la película exista
        Long id_pelicula = proyeccion.getPelicula().getId_pelicula();
        Pelicula pelicula = peliculaRepository.findById(id_pelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        proyeccion.setPelicula(pelicula);

        // Validamos que la sala exista
        Long id_sala = proyeccion.getSala().getId_sala();
        Sala sala = salaRepository.findById(id_sala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        proyeccion.setSala(sala);

        // AÑADIR MÁS TARDE NO PERMITIR PROYECCIONES EN FECHAS PASADAS Y NO PERMITIR QUE UNA SALA TENGA DOS PROYECCIONES AL MISMOT TIEMPO
        return proyeccionRepository.save(proyeccion);
    }

    // =========================== OBTENEMOS TODAS LAS PROYECCIONES =====================================
    public List<Proyeccion> obtenerTodas(){
        return proyeccionRepository.findAll();
    }

    // ========================== OBTENER POR ID ============================
    public Proyeccion obtenerPorId(Long id_proyeccion){
        return proyeccionRepository.findById(id_proyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));
    }

    // ============= OBTENER POR PELICULA ================================
    public List<Proyeccion> proyeccionPorPelicula(Long id_pelicula){
        Pelicula pelicula = peliculaRepository.findById(id_pelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        return proyeccionRepository.findByPelicula(pelicula);
    }

    public List<Proyeccion> obtenerPorSala(Long id_sala){

        return proyeccionRepository.findBySalaId_sala(id_sala);
    }

    public List<Proyeccion> obtenerPorFecha(LocalDate fecha) {

        return proyeccionRepository.findByFecha(fecha);
    }

    public long ocupacionProyeccion(Long id_proyeccion){
        Proyeccion proyeccion = proyeccionRepository.findById(id_proyeccion)
                .orElseThrow(() -> new RuntimeException("Proyeccion no encontrada"));

        return entradaRepository.findByProyeccion(proyeccion)
                .stream()
                .filter(e -> !e.getCancelada())
                .count();
    }
}
