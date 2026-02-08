package com.example.cine.controllers;

import com.example.cine.dto.AsistenteDTO;
import com.example.cine.entity.Asistente;
import com.example.cine.services.AsistenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asistentes")
public class AsistenteController {
    private final AsistenteService asistenteService;

    public AsistenteController(AsistenteService asistenteService) {
        this.asistenteService = asistenteService;
    }

    // ================= CREAR ASISTENTE ===========================
    @PostMapping("/crear")
    public ResponseEntity<Asistente> crearAsistente(@RequestBody Asistente asistente){
        return ResponseEntity.ok(asistenteService.crearAsistente(asistente));
    }

    // =================== OBTENER TODOS LOS ASISTENTES (DTO) ======================================
    @GetMapping
    public ResponseEntity<List<AsistenteDTO>> obtenerTodos(){
        return ResponseEntity.ok(asistenteService.obtenerTodosDTO());
    }

    // ============================ OBTENER POR ID (DTO) ==============================
    @GetMapping("/{id}")
    public ResponseEntity<AsistenteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asistenteService.obtenerPorIdDTO(id));
    }

    // ================================ ACTUALIZAR ASISTENTE ===========================================
    @PutMapping("/{id}")
    public ResponseEntity<Asistente> actualizarAsistente(
            @PathVariable Long id,
            @RequestBody Asistente asistente
    ) {
        return ResponseEntity.ok(asistenteService.actualizarAsistente(id, asistente));
    }

    // ================================== ELIMINAR ASISTENTE ==============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistente(@PathVariable Long id) {
        asistenteService.eliminarAsistente(id);
        return ResponseEntity.noContent().build();
    }
}
