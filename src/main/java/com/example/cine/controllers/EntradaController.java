package com.example.cine.controllers;

import com.example.cine.dto.EntradaDTO;
import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.entity.Proyeccion;
import com.example.cine.mappers.EntradaMapper;
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
    public ResponseEntity<EntradaDTO> comprarEntrada(
            @RequestParam Long id_asistente,
            @RequestParam Long id_proyeccion,
            @RequestParam Long id_butaca,
            @RequestParam Double precio
    ){
        return ResponseEntity.ok(
                EntradaMapper.toDTO(
                entradaService.comprarEntrada(
                        id_asistente,
                        id_proyeccion,
                        id_butaca,
                        precio
                )
                )
        );
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
        return ResponseEntity.ok(
                entradaService.cancelarEntrada(id_entrada)
        );
    }

    // ======================= ENTRADAS POR ASISTENTE ================================
    @GetMapping("/asistente/{id_asistente}")
    public ResponseEntity<List<EntradaDTO>> entradasPorAsistente(@PathVariable Long id_asistente){
        return ResponseEntity.ok(
                entradaService.entradasPorAsistente(id_asistente)
                        .stream()
                        .map(EntradaMapper::toDTO)
                        .toList()
        );
    }

    // ======================== OCUPACIÓN PROYECCIÓN =============================
    @GetMapping("/ocupacion/{id_proyeccion}")
    public ResponseEntity<Long> ocupacionProyeccion(
            @PathVariable Long id_proyeccion
    ){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(id_proyeccion)
        );
    }
}
