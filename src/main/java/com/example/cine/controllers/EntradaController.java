package com.example.cine.controllers;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.entity.Proyeccion;
import com.example.cine.services.EntradaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entradas")
public class EntradaController{
    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    // ================= COMPRAR ENTRADA =======================================
    @PostMapping("/comprar")
    public ResponseEntity<Entrada> comprarEntrada(
            @RequestParam Long id_asistente,
            @RequestParam Long id_proyeccion,
            @RequestParam Long id_butaca,
            @RequestParam Double precio
    ){
        Entrada entrada = entradaService.comprarEntrada(id_asistente, id_proyeccion, id_butaca, precio);
            return ResponseEntity.ok(entrada);
    }

    // ================== ASIENTOS LIBRES ============================
    @GetMapping("/libres")
    public ResponseEntity<List<Butaca>> asientosLibres(
            @RequestParam Long id_proyeccion
    ){
        return ResponseEntity.ok(
                entradaService.asientosLibres(id_proyeccion)
        );
    }

    // ================== CANCELAR ENTRADA ============================
    @PutMapping("/cancelar/{idEntrada}")
    public ResponseEntity<Entrada> cancelarEntrada(@PathVariable Long id_entrada){
        Entrada entrada = entradaService.cancelarEntrada(id_entrada);
        return ResponseEntity.ok(entrada);
    }

    @GetMapping("/asistente/{id_asistente}")
    public ResponseEntity<List<Entrada>> entradasPorAsistente(@PathVariable Long id_asistente){
        return ResponseEntity.ok(
                entradaService.entradasPorAsistente(id_asistente)
        );
    }
}
