package com.example.cine.controllers;

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

    // ============== CREAR ASISTENTE ===============================
    @PostMapping("/crear")
    public ResponseEntity<Asistente> crearAsistente(@RequestBody Asistente asistente){
        Asistente creado = asistenteService.crearAsistente(asistente);
        return ResponseEntity.ok(creado);
    }

    // ================ OBTENER TODOS LOS ASISTENTES =================
    @GetMapping
    public ResponseEntity<List<Asistente>> obtenerTodos(){
        return ResponseEntity.ok(asistenteService.obtenerTodos());
    }


    // ================= OBTENER POR ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Asistente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asistenteService.obtenerPorId(id));
    }

    // ================= ACTUALIZAR ASISTENTE =================
    @PutMapping("/{id}")
    public ResponseEntity<Asistente> actualizarAsistente(
            @PathVariable Long id,
            @RequestBody Asistente asistente
    ) {
        return ResponseEntity.ok(
                asistenteService.actualizarAsistente(id, asistente)
        );
    }

    // ================= ELIMINAR ASISTENTE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistente(@PathVariable Long id) {
        asistenteService.eliminarAsistente(id);
        return ResponseEntity.noContent().build();
    }
}
