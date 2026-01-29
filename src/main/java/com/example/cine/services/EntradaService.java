package com.example.cine.services;

import com.example.cine.entity.*;
import com.example.cine.repositories.AsistenteRepository;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntradaService{
    private final EntradaRepository entradaRepository;
    private final ProyeccionRepository proyeccionRepository;
    private final ButacaRepository butacaRepository;
    private final AsistenteRepository asistenteRepository;

    public EntradaService(EntradaRepository entradaRepository, ProyeccionRepository proyeccionRepository, ButacaRepository butacaRepository, AsistenteRepository asistenteRepository){
        this.entradaRepository = entradaRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.butacaRepository = butacaRepository;
        this.asistenteRepository = asistenteRepository;
    }

    // =================================== COMPRA DE ENTRADA =============================================
    public Entrada comprarEntrada(Long idAsistente, Long idProyeccion, Long idButaca, Double precio) {
        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Butaca butaca = butacaRepository.findById(idButaca)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

        // No se pueden comprar entradas de proyecciones pasadas
        LocalDateTime fechaHoraProyeccion =
                LocalDateTime.of(proyeccion.getFecha(), proyeccion.getHorario());

        if(fechaHoraProyeccion.isBefore(LocalDateTime.now())){
            throw new RuntimeException("No se pueden comprar entradas para la proyección");
        }

        // La butaca tiene que pertenecer a la sala de la proyección
        if (!butaca.getSala().getIdSala().equals(proyeccion.getSala().getIdSala())) {
            throw new RuntimeException("La butaca no pertenece a la sala de esta proyección");
        }

        // Verificamos que el asistente no supere las 5 entradas
        long entradasAsistente = entradaRepository
                .findByAsistenteAndCanceladaFalse(asistente)
                .size();

        if(entradasAsistente >= 5){
            throw new RuntimeException("El asistente ya tiene 5 entradas");
        }

        // ======================= BUTACA OCUPADA EN ESA PROYECCIÓN ==========================
        if(entradaRepository.existsByProyeccionAndButacaAndCanceladaFalse(proyeccion, butaca)){
            throw new RuntimeException("La butaca ya está ocupada en esta proyección");
        }

        // Control de aforo máximo de la sala
        Sala sala = proyeccion.getSala();
        long entradasVendidas = entradaRepository.findByProyeccion(proyeccion).size();
        if(entradasVendidas >= sala.getCapacidad()){
            throw new RuntimeException("Aforo completo, no se pueden vender más entradas");
        }

        // Creamos la entrada
        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butaca);
        entrada.setPrecio(precio);

        return entradaRepository.save(entrada);
    }

    // ================================ ASIENTOS LIBRES ==============================
    // Obtenemos los asientos libres de una sala para una proyección
    public List<Butaca> asientosLibres(Long idProyeccion) {
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        // Para simplificar, seleccionamos la primera sala de la proyección
        Sala sala = proyeccion.getSala();

        List<Butaca> todasButacas = sala.getButacas();
        List<Entrada> entradasVendidas = entradaRepository.findByProyeccion(proyeccion);

        return todasButacas.stream()
                .filter(b -> entradasVendidas.stream()
                        .noneMatch(e -> e.getButaca().equals(b) && !e.getCancelada()))
                .toList();
    }

    // =========================== CANCELAR ENTRADA =================================
    // Cancelamos la entrada
    public Entrada cancelarEntrada(Long idEntrada){
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() ->  new RuntimeException("Entrada no encontrada"));

        if(entrada.getCancelada()){
            throw new RuntimeException("Entrada ya cancelada");
        }

        LocalDateTime fechaHoraProyeccion =
                LocalDateTime.of(
                        entrada.getProyeccion().getFecha(),
                        entrada.getProyeccion().getHorario()
                );

        if(fechaHoraProyeccion.minusHours(2).isBefore(LocalDateTime.now())){
            throw new RuntimeException("No se puede cancelar la entrada con menos de dos horas");
        }

        entrada.setCancelada(true);
        return entradaRepository.save(entrada);
    }

    // =================== ENTRADAS POR CLIENTE ================================

    public List<Entrada> entradasPorAsistente(Long idAsistente){
        Asistente asistente = asistenteRepository.findById(idAsistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        return entradaRepository.findByAsistente(asistente);
    }

    // ===================== OCUPACIÓN PROYECCIÓN ======================================
    public Long ocupacionProyeccion(Long idProyeccion){
        Proyeccion proyeccion = proyeccionRepository.findById(idProyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada, sorry"));

        // Contamos solo las entradas no canceladas
        return entradaRepository.countByProyeccionAndCanceladaFalse(proyeccion);
    }
}
