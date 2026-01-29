package com.example.cine.controllers;

import com.example.cine.entity.Butaca;
import com.example.cine.services.ButacaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/butacas")
public class ButacaController {
    private final ButacaService butacaService;

    public ButacaController(ButacaService butacaService) {
        this.butacaService = butacaService;
    }

    // ================= CREAR BUTACA =================
    @PostMapping("/crear")
    public ResponseEntity<Butaca> crearButaca(
            @RequestBody Butaca butaca,
            @RequestParam Long idSala
    ) {
        return ResponseEntity.ok(
                butacaService.crearButaca(butaca, idSala)
        );
    }

    // ================= OBTENER TODAS =================
    @GetMapping
    public ResponseEntity<List<Butaca>> obtenerTodas() {
        return ResponseEntity.ok(butacaService.obtenerTodas());
    }

    // ================= OBTENER POR ID =================
    @GetMapping("/{id}")
    public ResponseEntity<Butaca> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(butacaService.obtenerPorId(id));
    }

    // ================= OBTENER POR SALA =================
    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<Butaca>> obtenerPorSala(@PathVariable Long idSala) {
        return ResponseEntity.ok(
                butacaService.obtenerPorSala(idSala)
        );
    }

    // ================= ELIMINAR BUTACA =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarButaca(@PathVariable Long id) {
        butacaService.eliminarButaca(id);
        return ResponseEntity.noContent().build();
    }
}
