package com.example.cine.controllers;


import com.example.cine.entity.Proyeccion;
import com.example.cine.services.EntradaService;
import com.example.cine.services.ProyeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<Proyeccion> crearProyeccion(@RequestBody Proyeccion proyeccion){
        return ResponseEntity.ok(proyeccionService.crearProyeccion(proyeccion));
    }

    // ================= OBTENEMOS TODAS LAS PROYECCIONES ==================================
    @GetMapping
    public ResponseEntity<List<Proyeccion>> todasLasProyecciones(){
        return ResponseEntity.ok(proyeccionService.obtenerTodas());
    }

    // ========================== OBTENER PROYECCIONES POR PELÍCULA =================================
    @GetMapping("/pelicula/{id_pelicula}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorPelicula(
            @PathVariable Long id_pelicula
    ){
        return ResponseEntity.ok(proyeccionService.proyeccionesPorPelicula(id_pelicula));
    }

    // =========================== OBTENER PROYECCIONES POR PELÍCULA ================
    @GetMapping("/pelicula/{id_pelicula}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorPelicula(
            @PathVariable Long id_proyeccion
    ){
        return ResponseEntity.ok(proyeccionService.obtenerPorId(id_proyeccion));
    }

    // ==================== OBTENEMOS PROYECCIONES POR SALA =============================
    @GetMapping("/sala/{id_sala}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorSala(@PathVariable Long id_sala){
        return ResponseEntity.ok(
                proyeccionService.obtenerPorSala(id_sala)
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
    @GetMapping("/ocupacion/{id_proyeccion}")
    public ResponseEntity<Long> ocupacion(@PathVariable Long id_proyeccion){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(id_proyeccion)
        );
    }
}
