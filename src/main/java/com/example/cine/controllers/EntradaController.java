package com.example.cine.controllers;

import com.example.cine.dto.EntradaDTO;
import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.mappers.EntradaMapper;
import com.example.cine.services.EntradaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entradas")
public class EntradaController {

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @PostMapping("/entradas")
    public ResponseEntity<EntradaDTO> comprarEntrada(@RequestParam Long idAsistente,
                                                     @RequestParam Long idProyeccion,
                                                     @RequestParam Long idButaca,
                                                     @RequestParam Double precio) {
        try {
            Entrada e = entradaService.comprarEntrada(idAsistente, idProyeccion, idButaca, precio);

            EntradaDTO dto = new EntradaDTO();
            dto.setIdEntrada(e.getIdEntrada());
            dto.setPrecio(e.getPrecio());
            dto.setNombreAsistente(e.getAsistente().getNombre());
            dto.setCancelada(e.getCancelada());
            dto.setFila(e.getButaca().getFila());
            dto.setNumeroButaca(e.getButaca().getNumero());

            return ResponseEntity.ok(dto);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null); // o lanzar un DTO con mensaje de error
        }
    }

    // ================== ASIENTOS LIBRES ============================
    @GetMapping("/libres")
    public ResponseEntity<List<Butaca>> asientosLibres(
            @RequestParam Long idProyeccion
    ){
        return ResponseEntity.ok(
                entradaService.asientosLibres(idProyeccion)
        );
    }

    // ================== CANCELAR ENTRADA ============================
    @PutMapping("/cancelar/{idEntrada}")
    public ResponseEntity<Map<String, Object>> cancelarEntrada(@PathVariable Long idEntrada){
        Entrada e = entradaService.cancelarEntrada(idEntrada);
        return ResponseEntity.ok(Map.of(
                "idEntrada", e.getIdEntrada(),
                "cancelada", e.getCancelada()
        ));
    }

    @GetMapping("/cancelables")
    public ResponseEntity<List<Entrada>> listarEntradasCancelable() {
        List<Entrada> cancelables = entradaService.obtenerEntradasCancelable();
        return ResponseEntity.ok(cancelables);
    }

    // ======================= ENTRADAS POR ASISTENTE ================================
    @GetMapping("/asistente/{idAsistente}")
    public ResponseEntity<List<EntradaDTO>> entradasPorAsistente(
            @PathVariable Long idAsistente){
        return ResponseEntity.ok(
                entradaService.entradasPorAsistente(idAsistente)
                        .stream()
                        .map(EntradaMapper::toDTO)
                        .toList()
        );
    }

    // ======================== OCUPACIÓN PROYECCIÓN =============================
    @GetMapping("/ocupacion/{idProyeccion}")
    public ResponseEntity<Long> ocupacionProyeccion(
            @PathVariable Long idProyeccion
    ){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(idProyeccion)
        );
    }
}
