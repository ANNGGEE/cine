package com.example.cine.controllers;


import com.example.cine.entity.Proyeccion;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.services.EntradaService;
import com.example.cine.services.ProyeccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
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

    // Proyecciones por sala
    @GetMapping("/sala/{id_sala}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorSala(@PathVariable Long id_sala){
        return ResponseEntity.ok(
                proyeccionService.obtenerPorSala(id_sala)
        );
    }

    // Proyecciones por fecha
    @GetMapping("/fecha")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorFecha(@RequestParam LocalDate fecha) {
        return ResponseEntity.ok(
                proyeccionService.obtenerPorFecha(fecha)
        );
    }

    // Ocupación de una proyección
    @GetMapping("/ocupacion/{id_proyeccion}")
    public ResponseEntity<Long> ocupacion(@PathVariable Long id_proyeccion){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(id_proyeccion)
        );
    }
}
