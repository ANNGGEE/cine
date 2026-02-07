//package com.example.cine.controllers;
//
//import com.example.cine.dto.AsistenteDTO;
//import com.example.cine.dto.ButacaDTO;
//import com.example.cine.dto.EntradaDTO;
//import com.example.cine.dto.ProyeccionDTO;
//import com.example.cine.entity.*;
//import com.example.cine.services.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/")
//public class IndexController {
//
//    private final AsistenteService asistenteService;
//    private final ButacaService butacaService;
//    private final EntradaService entradaService;
//    private final PeliculaService peliculaService;
//    private final ProyeccionService proyeccionService;
//    private final SalaService salaService;
//
//    public IndexController(AsistenteService asistenteService, ButacaService butacaService,
//                           EntradaService entradaService, PeliculaService peliculaService,
//                           ProyeccionService proyeccionService, SalaService salaService) {
//        this.asistenteService = asistenteService;
//        this.butacaService = butacaService;
//        this.entradaService = entradaService;
//        this.peliculaService = peliculaService;
//        this.proyeccionService = proyeccionService;
//        this.salaService = salaService;
//    }
//
//    // ==================== INDEX / MENÚ =====================
//    @GetMapping
//    public ResponseEntity<Map<String, String>> index() {
//        return ResponseEntity.ok(Map.of(
//                "mensaje", "API Cine funcionando 🚀",
//                "asistentes", "/asistentes",
//                "butacas", "/butacas",
//                "peliculas", "/peliculas",
//                "salas", "/salas",
//                "proyecciones", "/proyecciones",
//                "entradas", "/entradas"
//        ));
//    }
//
//    // ==================== ASISTENTES =====================
//    @PostMapping("/asistentes")
//    public ResponseEntity<AsistenteDTO> crearAsistente(@RequestParam String nombre) {
//        if (nombre == null || nombre.isBlank()) throw new RuntimeException("Nombre obligatorio");
//        Asistente a = new Asistente();
//        a.setNombre(nombre);
//        Asistente creado = asistenteService.crearAsistente(a);
//        return ResponseEntity.ok(asistenteService.obtenerPorIdDTO(creado.getIdAsistente()));
//    }
//
//    @GetMapping("/asistentes")
//    public ResponseEntity<List<AsistenteDTO>> listarAsistentes() {
//        return ResponseEntity.ok(asistenteService.obtenerTodosDTO());
//    }
//
//    // ==================== PELICULAS =====================
//    @PostMapping("/peliculas")
//    public ResponseEntity<Pelicula> crearPelicula(@RequestParam String titulo,
//                                                  @RequestParam int duracion,
//                                                  @RequestParam String genero) {
//        if (titulo == null || titulo.isBlank()) throw new RuntimeException("Título obligatorio");
//        Pelicula p = new Pelicula();
//        p.setTitulo(titulo);
//        p.setDuracion(duracion);
//        p.setGenero(genero);
//        return ResponseEntity.ok(peliculaService.crearPelicula(p));
//    }
//
//    @GetMapping("/peliculas")
//    public ResponseEntity<List<Pelicula>> listarPeliculas() {
//        return ResponseEntity.ok(peliculaService.obtenerTodas());
//    }
//
//    // ==================== SALAS =====================
//    @PostMapping("/salas")
//    public ResponseEntity<Sala> crearSala(@RequestParam String descripcion,
//                                          @RequestParam int capacidad) {
//        Sala sala = new Sala();
//        sala.setDescripcion(descripcion);
//        sala.setCapacidad(capacidad);
//        return ResponseEntity.ok(salaService.crearSala(sala));
//    }
//
//    @GetMapping("/salas")
//    public ResponseEntity<List<Sala>> listarSalas() {
//        return ResponseEntity.ok(salaService.obtenerTodas());
//    }
//
//    // ==================== PROYECCIONES =====================
//    @PostMapping("/proyecciones")
//    public ResponseEntity<ProyeccionDTO> crearProyeccion(@RequestParam Long idPelicula,
//                                                         @RequestParam Long idSala,
//                                                         @RequestParam String fecha,
//                                                         @RequestParam String hora) {
//        Pelicula p = peliculaService.obtenerPorId(idPelicula);
//        Sala s = salaService.obtenerPorId(idSala);
//
//        Proyeccion proyeccion = new Proyeccion();
//        proyeccion.setFecha(LocalDate.parse(fecha));
//        proyeccion.setHorario(LocalTime.parse(hora));
//        Proyeccion creado = proyeccionService.crearProyeccion(proyeccion, idPelicula, idSala);
//
//        ProyeccionDTO dto = new ProyeccionDTO();
//        dto.setIdProyeccion(creado.getIdProyeccion());
//        dto.setFecha(creado.getFecha());
//        dto.setHorario(creado.getHorario().toString());
//        dto.setPeliculaTitulo(creado.getPelicula().getTitulo());
//        dto.setSala("Sala " + creado.getSala().getNumero() + " - " + creado.getSala().getDescripcion());
//
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/proyecciones")
//    public ResponseEntity<List<Proyeccion>> listarProyecciones() {
//        return ResponseEntity.ok(proyeccionService.obtenerTodas());
//    }
//
//    // ==================== BUTACAS =====================
//    @GetMapping("/butacas/{idSala}")
//    public ResponseEntity<List<ButacaDTO>> listarButacasPorSala(@PathVariable Long idSala) {
//        List<Butaca> butacas = butacaService.obtenerPorSala(idSala);
//        List<ButacaDTO> dto = butacas.stream().map(b -> {
//            ButacaDTO d = new ButacaDTO();
//            d.setIdButaca(b.getIdButaca());
//            d.setFila(b.getFila());
//            d.setNumero(b.getNumero());
//            return d;
//        }).toList();
//        return ResponseEntity.ok(dto);
//    }
//
//    // ==================== ENTRADAS =====================
//    @PostMapping("/entradas")
//    public ResponseEntity<EntradaDTO> comprarEntrada(@RequestParam Long idAsistente,
//                                                     @RequestParam Long idProyeccion,
//                                                     @RequestParam Long idButaca,
//                                                     @RequestParam Double precio) {
//        if (precio <= 0) throw new RuntimeException("Precio inválido");
//        Entrada e = entradaService.comprarEntrada(idAsistente, idProyeccion, idButaca, precio);
//
//        EntradaDTO dto = new EntradaDTO();
//        dto.setIdEntrada(e.getIdEntrada());
//        dto.setPrecio(e.getPrecio());
//        dto.setNombreAsistente(e.getAsistente().getNombre());
//        dto.setCancelada(e.getCancelada());
//        dto.setFila(e.getButaca().getFila());           // fila como String
//        dto.setNumeroButaca(e.getButaca().getNumero()); // número como Integer
//        dto.setTituloPelicula(e.getProyeccion().getPelicula().getTitulo());
//        dto.setSala("Sala " + e.getProyeccion().getSala().getNumero() + " - " + e.getProyeccion().getSala().getDescripcion());
//        dto.setFecha(e.getProyeccion().getFecha());
//        dto.setHorario(e.getProyeccion().getHorario());
//
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/asientosLibres/{idProyeccion}")
//    public ResponseEntity<List<Butaca>> listarAsientosLibres(@PathVariable Long idProyeccion) {
//        return ResponseEntity.ok(entradaService.asientosLibres(idProyeccion));
//    }
//
//    @PostMapping("/entradas/cancelar/{idEntrada}")
//    public ResponseEntity<EntradaDTO> cancelarEntrada(@PathVariable Long idEntrada) {
//        Entrada e = entradaService.cancelarEntrada(idEntrada);
//
//        EntradaDTO dto = new EntradaDTO();
//        dto.setIdEntrada(e.getIdEntrada());
//        dto.setPrecio(e.getPrecio());
//        dto.setNombreAsistente(e.getAsistente().getNombre());
//        dto.setCancelada(e.getCancelada());
//        dto.setFila(e.getButaca().getFila());           // fila
//        dto.setNumeroButaca(e.getButaca().getNumero()); // número
//        dto.setTituloPelicula(e.getProyeccion().getPelicula().getTitulo());
//        dto.setSala("Sala " + e.getProyeccion().getSala().getNumero() + " - " + e.getProyeccion().getSala().getDescripcion());
//        dto.setFecha(e.getProyeccion().getFecha());
//        dto.setHorario(e.getProyeccion().getHorario());
//
//        return ResponseEntity.ok(dto);
//    }
//}
