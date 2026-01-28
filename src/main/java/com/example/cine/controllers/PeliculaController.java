package com.example.cine.controllers;

import com.example.cine.entity.Pelicula;
import com.example.cine.services.PeliculaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    // ================= LISTAR TODAS LAS PELICULAS ================
    @GetMapping
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliculaService.obtenerTodas());
    }

    // ================= OBTENER PELICULA POR ID ================
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPelicula(@PathVariable Long id){
        return ResponseEntity.ok(peliculaService.obtenerPorId(id));
    }

    // ==================== CREAR PELICULA ====================
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@RequestBody Pelicula pelicula){
        return ResponseEntity.ok(peliculaService.crearPelicula(pelicula));
    }

    // ==================== ACTUALIZAR PELICULA =========================
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula pelicula){
        return ResponseEntity.ok(peliculaService.actualizarPelicula(id, pelicula));
    }

    // ========================= ELIMINAR PELICULA ===========================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id){
        peliculaService.eliminarPelicula(id);
        return ResponseEntity.noContent().build();
    }
}
