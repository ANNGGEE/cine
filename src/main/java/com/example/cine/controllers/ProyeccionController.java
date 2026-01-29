package com.example.cine.controllers;


import com.example.cine.entity.Proyeccion;
import com.example.cine.services.EntradaService;
import com.example.cine.services.ProyeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/proyecciones")
public class ProyeccionController {
    private final ProyeccionService proyeccionService;
    private final EntradaService entradaService;

    public ProyeccionController(ProyeccionService proyeccionService, EntradaService entradaService) {
        this.proyeccionService = proyeccionService;
        this.entradaService = entradaService;
    }

    // ============= CREAR PROYECCIÓN ================
    @PostMapping("/crear")
    public ResponseEntity<Proyeccion> crearProyeccion(@RequestBody Proyeccion proyeccion, @RequestParam Long idPelicula, @RequestParam Long idSala){
        return ResponseEntity.ok(proyeccionService.crearProyeccion(proyeccion, idPelicula, idSala));
    }

    // ================= OBTENEMOS TODAS LAS PROYECCIONES ==================================
    @GetMapping
    public ResponseEntity<List<Proyeccion>> todasLasProyecciones(){
        return ResponseEntity.ok(proyeccionService.obtenerTodas());
    }

    // ================== OBTENEMOS POR ID ==============================
    @GetMapping("/{id}")
    public ResponseEntity<Proyeccion> obtenerProyeccion(@PathVariable Long id){
        return ResponseEntity.ok(proyeccionService.obtenerPorId(id));
    }

    // ===================== ACTUALIZAR PROYECCIÓN =========================
    @PutMapping("/{id}")
    public  ResponseEntity<Proyeccion> actualizarProyeccion(@PathVariable Long id, @RequestBody Proyeccion proyeccion){
        return ResponseEntity.ok(proyeccionService.actualizarProyeccion(id, proyeccion));
    }

    // ==================== ELIMINAR PROYECCIÓN ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyeccion(@PathVariable Long id){
        proyeccionService.eliminarProyeccion(id);
        return ResponseEntity.noContent().build();
    }

    // =========================== OBTENER PROYECCIONES POR PELÍCULA ================
    @GetMapping("/pelicula/{idPelicula}")
    public ResponseEntity<List<Proyeccion>> listarPorPelicula(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(proyeccionService.obtenerPorPelicula(idPelicula));
    }

    // ==================== OBTENEMOS PROYECCIONES POR SALA =============================
    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorSala(@PathVariable Long idSala){
        return ResponseEntity.ok(
                proyeccionService.obtenerPorSala(idSala)
        );
    }

    // =========================== OBTENEMOS PROYECCIONES POR FECHA ===============================
    @GetMapping("/fecha")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorFecha(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(
                proyeccionService.obtenerPorFecha(fecha)
        );
    }

    // ===================== OCUPACIÓN DE UNA PROYECCIÓN ===========================================
    @GetMapping("/ocupacion/{idProyeccion}")
    public ResponseEntity<Long> ocupacion(@PathVariable Long idProyeccion){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(idProyeccion)
        );
    }
}
