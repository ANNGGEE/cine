package com.example.cine.services;

import com.example.cine.entity.*;
import com.example.cine.repositories.AsistenteRepository;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // Compra manual específica
    @Transactional
    public Entrada comprarEntrada(Long idAsistente, Long idProyeccion, Long idButaca, Double precio) {
        if (precio <= 0) throw new RuntimeException("Precio inválido");

        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Butaca butaca = butacaRepository.findById(idButaca)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

        LocalDateTime fechaHoraProyeccion = LocalDateTime.of(proyeccion.getFecha(), proyeccion.getHorario());
        if (fechaHoraProyeccion.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede comprar entradas para proyecciones pasadas");
        }

        // ==================== REGLA 2: Máximo 5 entradas ====================
        long totalEntradas = entradaRepository.countByAsistenteAndCanceladaFalse(asistente);
        if (totalEntradas >= 5) {
            throw new RuntimeException("El asistente ya tiene 5 entradas compradas, no puede comprar más");
        }

        boolean ocupada = entradaRepository.existsByProyeccionAndButacaAndCanceladaFalse(proyeccion, butaca);
        if (ocupada) throw new RuntimeException("La butaca " + butaca.getPosicion() + " ya está ocupada");

        if (!butaca.getSala().getIdSala().equals(proyeccion.getSala().getIdSala())) {
            throw new RuntimeException("La butaca no pertenece a esta sala");
        }

        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butaca);
        entrada.setPrecio(precio);
        entrada.setFechacompra(LocalDateTime.now());
        entrada.setCancelada(false);

        Entrada entradaGuardada = entradaRepository.save(entrada);
        System.out.println("ID de la entrada: " + entradaGuardada.getIdEntrada());

        return entradaRepository.save(entrada);
    }

    // Ver entradas por asistente
    public List<Entrada> verEntradasPorAsistente(Long idAsistente) {
        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));
        return entradaRepository.findByAsistente(asistente);
    }

        // Devuelve las butacas libres para una proyección
    public List<Butaca> obtenerButacasLibres(Proyeccion proyeccion) {
        List<Butaca> todas = proyeccion.getSala().getButacas();
        List<Entrada> ocupadas = entradaRepository.findByProyeccion(proyeccion);

        // Filtramos las que ya están compradas
        return todas.stream()
                .filter(b -> ocupadas.stream().noneMatch(e -> e.getButaca().equals(b)))
                .collect(Collectors.toList());
    }

    // Comprar entradas (transaccional), compra una sola entrada especificando exactamente el asiento
    // Compra múltiple de varios asientos libres
    @Transactional // Garantiza que todo el proceso de validación y guardado sea atómico, si falla cualquier regla (ej: asiento ocupado), no se guarda nada
    public void comprarEntradas(Asistente asistente, Proyeccion proyeccion, int cantidad) {
        List<Butaca> libres = obtenerButacasLibres(proyeccion);

        if (libres.size() < cantidad) {
            throw new RuntimeException("No hay suficientes butacas libres. Quedan: " + libres.size());
        }

        for (int i = 0; i < cantidad; i++) {
            Butaca b = libres.get(i);

            Entrada e = new Entrada();
            e.setAsistente(asistente);
            e.setProyeccion(proyeccion);
            e.setButaca(b);
            e.setFechacompra(LocalDateTime.now());
            e.setCancelada(false);
            e.setPrecio(8.0);

            Entrada entradaGuardada = entradaRepository.save(e);
            System.out.println("ID de la entrada: " + entradaGuardada.getIdEntrada());

            entradaRepository.save(e);
        }
    }

    // =================== COMPRA AUTOMÁTICA (PRIMER ASIENTO LIBRE) =========
    // Compra una entrada automática, asignando la primera butaca libre disponible , compra rápida de un asiento
    @Transactional
    public Entrada comprarEntradaAutomatica(Long idAsistente, Long idProyeccion, Double precio) {
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        // Todas las butacas de la sala usando el idSala correcto
        List<Butaca> todasButacas = butacaRepository.findBySala_IdSala(proyeccion.getSala().getIdSala());

        // Entradas ya vendidas
        List<Entrada> entradasVendidas = entradaRepository.findByProyeccion(proyeccion);

        // Buscar primera butaca libre
        Optional<Butaca> butacaLibre = todasButacas.stream()
                .filter(b -> entradasVendidas.stream()
                        .noneMatch(e -> e.getButaca().getIdButaca().equals(b.getIdButaca()) && !e.getCancelada()))
                .findFirst();

        if (butacaLibre.isEmpty())
            throw new RuntimeException("No hay butacas disponibles");

        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butacaLibre.get());
        entrada.setPrecio(precio);
        entrada.setFechacompra(LocalDateTime.now());
        entrada.setCancelada(false);

        Entrada entradaGuardada = entradaRepository.save(entrada);
        System.out.println("ID de la entrada: " + entradaGuardada.getIdEntrada());

        return entradaRepository.save(entrada);
    }

    // ================================ ASIENTOS LIBRES ==============================
    // Obtenemos los asientos libres de una sala para una proyección
    @Transactional(readOnly = true)
    public List<Butaca> asientosLibres(Long idProyeccion) {
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        List<Butaca> todasButacas = butacaRepository.findBySala_IdSala(proyeccion.getSala().getIdSala());
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

    public List<Entrada> obtenerEntradasCancelable() {
        LocalDateTime ahoraMas2Horas = LocalDateTime.now().plusHours(2);
        return entradaRepository.findAll().stream()
                .filter(entrada ->
                        entrada.getProyeccion().getFecha().atTime(entrada.getProyeccion().getHorario())
                                .isAfter(ahoraMas2Horas) && !entrada.getCancelada()
                )
                .toList();
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
