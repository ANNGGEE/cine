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
    public ResponseEntity<?> comprarEntrada(@RequestParam Long idAsistente,
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
            // Mandar el mensaje al cliente en Postman
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    // ================== ASIENTOS LIBRES ============================
    @GetMapping("/libres")
    public ResponseEntity<?> asientosLibres(
            @RequestParam Long idProyeccion
    ){
        try {
            List<Butaca> libres = entradaService.asientosLibres(idProyeccion);
            return ResponseEntity.ok(libres);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("mensaje", ex.getMessage()));
        }
    }

    // ================== CANCELAR ENTRADA ============================
    @PutMapping("/cancelar/{idEntrada}")
    public ResponseEntity<Map<String, Object>> cancelarEntrada(@PathVariable Long idEntrada){
        try{
        Entrada e = entradaService.cancelarEntrada(idEntrada);
        return ResponseEntity.ok(Map.of(
                "idEntrada", e.getIdEntrada(),
                "cancelada", e.getCancelada(),
                "mensaje", "Entrada cancelada correctamente"
        ));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("mensaje", ex.getMessage()));
        }
    }

    @GetMapping("/cancelables")
    public ResponseEntity<List<Entrada>> listarEntradasCancelable() {
        List<Entrada> cancelables = entradaService.obtenerEntradasCancelable();
        return ResponseEntity.ok(cancelables);
    }

    // ======================= ENTRADAS POR ASISTENTE ================================
    @GetMapping("/asistente/{idAsistente}")
    public ResponseEntity<?> entradasPorAsistente(
            @PathVariable Long idAsistente){
        try {
            List<EntradaDTO> dtoList = entradaService.entradasPorAsistente(idAsistente)
                    .stream()
                    .map(EntradaMapper::toDTO)
                    .toList();
            return ResponseEntity.ok(dtoList);
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("mensaje", ex.getMessage()));
        }
    }

    // ======================== OCUPACIÓN PROYECCIÓN =============================
    @GetMapping("/ocupacion/{idProyeccion}")
    public ResponseEntity<?> ocupacionProyeccion(
            @PathVariable Long idProyeccion
    ){
        try {
            Long ocupacion = entradaService.ocupacionProyeccion(idProyeccion);
            return ResponseEntity.ok(Map.of(
                    "idProyeccion", idProyeccion,
                    "ocupacion", ocupacion
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("mensaje", ex.getMessage()));
        }
    }
}
