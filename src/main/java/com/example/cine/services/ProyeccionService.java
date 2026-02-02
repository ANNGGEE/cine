package com.example.cine.services;

import com.example.cine.entity.Pelicula;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.PeliculaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Proyeccion crearProyeccion(Proyeccion proyeccion, Long idPelicula, Long idSala) {
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        proyeccion.setPelicula(pelicula);
        proyeccion.setSala(sala);

        return proyeccionRepository.save(proyeccion);
    }

    // =================== OBTENER PAGINADO ========================
    public Page<Proyeccion> obtenerPaginado(Pageable pageable){
        return proyeccionRepository.findAll(pageable);
    }

    // =========================== OBTENEMOS TODAS LAS PROYECCIONES =====================================
    public List<Proyeccion> obtenerTodas(){
        return proyeccionRepository.findAllWithSalaAndPelicula();
    }

    // ========================== OBTENER POR ID ============================
    public Proyeccion obtenerPorId(Long idProyeccion){
        return proyeccionRepository.findByIdWithSalaAndPelicula(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));
    }

    // ============= OBTENER POR PELICULA ================================
    public List<Proyeccion> proyeccionPorPelicula(Long idPelicula){
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        return proyeccionRepository.findByPelicula(pelicula);
    }

    // ================== LISTAR POR PELICULA ===========================
    public List<Proyeccion> obtenerPorSala(Long idSala){
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        return proyeccionRepository.findBySala(sala);
    }

    public Page<Proyeccion> obtenerPorFecha(LocalDate fecha, Pageable pageable) {
        return proyeccionRepository.findByFecha(fecha, pageable);
    }

    public long ocupacionProyeccion(Long idProyeccion){
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyeccion no encontrada"));

        return entradaRepository.findByProyeccion(proyeccion)
                .stream()
                .filter(e -> !e.getCancelada())
                .count();
    }

    // =================== ACTUALIZAR PROYECCIÓN ======================
    public Proyeccion actualizarProyeccion(Long id, Proyeccion proyeccionActualizada){
        Proyeccion proyeccion = obtenerPorId(id);
        proyeccion.setHorario(proyeccionActualizada.getHorario());
        proyeccion.setFecha(proyeccionActualizada.getFecha());
        // Actualizamos la sala o película si es necesario
        return proyeccionRepository.save(proyeccion);
    }

    // ======================== ELIMINAR PROYECCIÓN =====================
    public void eliminarProyeccion(Long id){
        Proyeccion proyeccion = obtenerPorId(id);
        proyeccionRepository.delete(proyeccion);
    }

    // =============== LISTAMOS LA PROYECCIÓN POR PELICULA ================
    public List<Proyeccion> obtenerPorPelicula(Long idPelicula){
        Pelicula pelicula = peliculaRepository.findById(idPelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        return proyeccionRepository.findByPelicula(pelicula);
    }
}
