package com.example.cine.controllers;


import com.example.cine.dto.ProyeccionDTO;
import com.example.cine.entity.Proyeccion;
import com.example.cine.mappers.PeliculaMapper;
import com.example.cine.mappers.ProyeccionMapper;
import com.example.cine.mappers.SalaMapper;
import com.example.cine.services.EntradaService;
import com.example.cine.services.ProyeccionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Proyeccion> crearProyeccion(@RequestBody Proyeccion proyeccion, @RequestParam Long idPelicula, @RequestParam Long idSala){
       return ResponseEntity.ok(proyeccionService.crearProyeccion(proyeccion, idPelicula, idSala));
//        // Creamos la proyección usando el service
//        Proyeccion p = proyeccionService.crearProyeccion(proyeccion, idPelicula, idSala);
//
//        // Convertimos la entidad a DTO
//        ProyeccionDTO dto = ProyeccionMapper.toDTO(p);
//        return ResponseEntity.ok(dto);
    }

    // =============== OBTENER PAGINADO =======================
    @GetMapping("/paginado")
    public Page<Proyeccion> listarPaginado(Pageable pageable){
        return proyeccionService.obtenerPaginado(pageable);
    }

    // ================= OBTENEMOS TODAS LAS PROYECCIONES ==================================
    @GetMapping
    public ResponseEntity<List<Proyeccion>> todasLasProyecciones(){
        return ResponseEntity.ok(proyeccionService.obtenerTodas());
    }

    // ================== OBTENEMOS POR ID ==============================
    @GetMapping("/{id}")
    public ResponseEntity<ProyeccionDTO> obtenerProyeccion(@PathVariable Long id) {
        Proyeccion p = proyeccionService.obtenerPorId(id);
        ProyeccionDTO dto = new ProyeccionDTO();

        dto.setIdProyeccion(p.getIdProyeccion());
        dto.setFecha(p.getFecha());
        dto.setHorario(p.getHorario());
        dto.setPelicula(PeliculaMapper.toDTO(p.getPelicula()));
        dto.setSala(SalaMapper.toDTO(p.getSala()));

        return ResponseEntity.ok(dto);
    }

    // ===================== ACTUALIZAR PROYECCIÓN =========================
    @PutMapping("/{id}")
    public  ResponseEntity<Proyeccion> actualizarProyeccion(@PathVariable Long id, @RequestBody Proyeccion proyeccion){
        return ResponseEntity.ok(proyeccionService.actualizarProyeccion(id, proyeccion));
    }

    // ==================== ELIMINAR PROYECCIÓN ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyeccion(@PathVariable Long id){
        proyeccionService.eliminarProyeccion(id);
        return ResponseEntity.noContent().build();
    }

    // =========================== OBTENER PROYECCIONES POR PELÍCULA ================
    @GetMapping("/pelicula/{idPelicula}")
    public ResponseEntity<List<Proyeccion>> listarPorPelicula(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(proyeccionService.obtenerPorPelicula(idPelicula));
    }

    // ==================== OBTENEMOS PROYECCIONES POR SALA =============================
    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<Proyeccion>> proyeccionesPorSala(@PathVariable Long idSala){
        return ResponseEntity.ok(
                proyeccionService.obtenerPorSala(idSala)
        );
    }

    // =========================== OBTENEMOS PROYECCIONES POR FECHA ===============================
    @GetMapping("/fecha/paginado")
    public Page<Proyeccion> proyeccionesPorFechaPaginado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Pageable pageable
    ) {
        return proyeccionService.obtenerPorFecha(fecha, pageable);
    }

    // ===================== OCUPACIÓN DE UNA PROYECCIÓN ===========================================
    @GetMapping("/ocupacion/{idProyeccion}")
    public ResponseEntity<Long> ocupacion(@PathVariable Long idProyeccion){
        return ResponseEntity.ok(
                entradaService.ocupacionProyeccion(idProyeccion)
        );
    }
}
