package com.example.cine.controllers;

import com.example.cine.entity.Sala;
import com.example.cine.services.SalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    // ================= CREAR SALA =================
    @PostMapping
    public ResponseEntity<Sala> crear(@RequestBody Sala sala) {
        return ResponseEntity.ok(salaService.crearSala(sala));
    }

    // ================= LISTAR TODAS LAS SALAS ============
    @GetMapping
    public ResponseEntity<List<Sala>> listarSalas(){
        return ResponseEntity.ok(salaService.obtenerTodas());
    }

    // =============== OBTENER SALA POR ID ===================
    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerSala(@PathVariable Long id){
        return ResponseEntity.ok(salaService.obtenerPorId(id));
    }

    // ================ CREAR SALA ==========================
    @PostMapping
    public ResponseEntity<Sala> crearSala(@RequestBody Sala sala){
        return ResponseEntity.ok(salaService.crearSala(sala));
    }

    // ================= ELIMINAR =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        salaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
