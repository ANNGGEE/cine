package com.example.cine.services;

import com.example.cine.entity.*;
import com.example.cine.repositories.AsistenteRepository;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntradaService {
    private final EntradaRepository entradaRepository;
    private final ProyeccionRepository proyeccionRepository;
    private final ButacaRepository butacaRepository;
    private final AsistenteRepository asistenteRepository;

    public EntradaService(EntradaRepository entradaRepository, ProyeccionRepository proyeccionRepository, ButacaRepository butacaRepository, AsistenteRepository asistenteRepository) {
        this.entradaRepository = entradaRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.butacaRepository = butacaRepository;
        this.asistenteRepository = asistenteRepository;
    }

    // =================================== COMPRA DE ENTRADA =============================================
    public Entrada comprarEntrada(Long idAsistente, Long idProyeccion, Long idButaca, Double precio) {
        if(precio <= 0) throw new RuntimeException("Precio inválido");

        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Butaca butaca = butacaRepository.findById(idButaca)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

        LocalDateTime fechaHora = LocalDateTime.of(proyeccion.getFecha(), proyeccion.getHorario());
        if(fechaHora.isBefore(LocalDateTime.now())){
            throw new RuntimeException("No se pueden comprar entradas para la proyección");
        }

        if(!butaca.getSala().getIdSala().equals(proyeccion.getSala().getIdSala())){
            throw new RuntimeException("La butaca no pertenece a la sala de esta proyección");
        }

        long entradasAsistente = entradaRepository.countByAsistenteAndCanceladaFalse(asistente);
        if(entradasAsistente >= 5){
            throw new RuntimeException("El asistente ya tiene 5 entradas");
        }

        if(entradaRepository.existsByProyeccionAndButacaAndCanceladaFalse(proyeccion, butaca)){
            throw new RuntimeException("La butaca ya está ocupada en esta proyección");
        }

        if(entradaRepository.countByProyeccionAndCanceladaFalse(proyeccion) >= proyeccion.getSala().getCapacidad()){
            throw new RuntimeException("Aforo completo, no se pueden vender más entradas");
        }

        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butaca);
        entrada.setPrecio(precio);

        return entradaRepository.save(entrada);
    }

    // Ver entradas por asistente
    public List<Entrada> verEntradasPorAsistente(Long idAsistente) {
        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));
        return entradaRepository.findByAsistente(asistente);
    }

    // =================== COMPRA AUTOMÁTICA (PRIMER ASIENTO LIBRE) =========
    @Transactional
    public Entrada comprarEntradaAutomatica(Long idAsistente, Long idProyeccion, Double precio) {
        // Obtener proyección y asistente
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        // Todas las butacas de la sala
        List<Butaca> todasButacas = butacaRepository.findBySalaId(proyeccion.getSala().getIdSala());

        // Entradas ya vendidas
        List<Entrada> entradasVendidas = entradaRepository.findByProyeccion(proyeccion);

        // Buscar primera butaca libre
        Optional<Butaca> butacaLibre = todasButacas.stream()
                .filter(b -> entradasVendidas.stream()
                        .noneMatch(e -> e.getButaca().getIdButaca().equals(b.getIdButaca()) && !e.getCancelada()))
                .findFirst();

        if (butacaLibre.isEmpty())
            throw new RuntimeException("No hay butacas disponibles");

        // Crear y guardar la entrada
        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butacaLibre.get());
        entrada.setPrecio(precio);
        entrada.setFechacompra(LocalDateTime.now());
        entrada.setCancelada(false);

        return entradaRepository.save(entrada);
    }

    // ================================ ASIENTOS LIBRES ==============================
// Obtenemos los asientos libres de una sala para una proyección
    @Transactional(readOnly = true)
    public List<Butaca> asientosLibres(Long idProyeccion) {
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        List<Butaca> todasButacas = butacaRepository.findBySalaId(proyeccion.getSala().getIdSala());
        List<Entrada> entradasVendidas = entradaRepository.findByProyeccion(proyeccion);

        return todasButacas.stream()
                .filter(b -> entradasVendidas.stream()
                        .noneMatch(e -> e.getButaca().getIdButaca().equals(b.getIdButaca()) && !e.getCancelada()))
                .toList();
    }

    // =========================== CANCELAR ENTRADA =================================
// Cancelamos la entrada
    public Entrada cancelarEntrada(Long idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("Entrada no encontrada"));

        if (entrada.getCancelada()) {
            throw new RuntimeException("Entrada ya cancelada");
        }

        LocalDateTime fechaHoraProyeccion =
                LocalDateTime.of(entrada.getProyeccion().getFecha(), entrada.getProyeccion().getHorario());

        if (fechaHoraProyeccion.minusHours(2).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede cancelar la entrada con menos de dos horas");
        }

        entrada.setCancelada(true);
        return entradaRepository.save(entrada);
    }

// =================== ENTRADAS POR CLIENTE ================================

    public List<Entrada> entradasPorAsistente(Long idAsistente) {
        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        return entradaRepository.findByAsistente(asistente);
    }

    // ===================== OCUPACIÓN PROYECCIÓN ======================================
    public Long ocupacionProyeccion(Long idProyeccion) {
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada, sorry"));

        // Contamos solo las entradas no canceladas
        return entradaRepository.countByProyeccionAndCanceladaFalse(proyeccion);
    }
}
