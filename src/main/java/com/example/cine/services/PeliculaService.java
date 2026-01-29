package com.example.cine.services;

import com.example.cine.entity.Pelicula;
import com.example.cine.repositories.PeliculaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {
    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    // ================ LISTAMOS TODAS LAS PELICULAS ================
    public List<Pelicula> obtenerTodas(){
        return peliculaRepository.findAll();
    }

    // ===================== OBTENER POR ID ======================
    public Pelicula obtenerPorId(Long id_pelicula){
        return peliculaRepository.findById(id_pelicula)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
    }

    // ================= CREAR PELICULA ========================
    public Pelicula crearPelicula(Pelicula pelicula){
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isBlank()) {
            throw new RuntimeException("El título es obligatorio");
        }

        return peliculaRepository.save(pelicula);
    }

    // ===================== ACTUALIZAR PELICULA ===========================
    public Pelicula actualizarPelicula(Long id, Pelicula peliculaActualizada){
        Pelicula pelicula = obtenerPorId(id);
        pelicula.setTitulo(peliculaActualizada.getTitulo());
        pelicula.setDuracion(peliculaActualizada.getDuracion());
        pelicula.setGenero(peliculaActualizada.getGenero());
        return peliculaRepository.save(pelicula);
    }

    // ==================== ELIMINAR PELICULA ====================
    public void eliminarPelicula(Long id){
        Pelicula pelicula = obtenerPorId(id);
        peliculaRepository.delete(pelicula);
    }
}
