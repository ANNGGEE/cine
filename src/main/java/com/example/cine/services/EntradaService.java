package com.example.cine.services;

import com.example.cine.entity.*;
import com.example.cine.repositories.AsistenteRepository;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import org.springframework.stereotype.Service;

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
    public Entrada comprarEntrada(Long id_asistente, Long id_proyeccion, Long id_butaca, Double precio) {
        Asistente asistente = asistenteRepository.findById(id_asistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        Proyeccion proyeccion = proyeccionRepository.findById(id_proyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Butaca butaca = butacaRepository.findById(id_butaca)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

        Sala sala = butaca.getSala();
        // Verificamos que el asistente no supere las 5 entradas
        List<Entrada> entradasAsistente = entradaRepository.findByAsistente(asistente);
        if(entradasAsistente.size() >= 5){
            throw new RuntimeException("El asistente ya tiene 5 entradas");
        }

        // Verificamos que la butaca esté libre para la proyección
        List<Entrada> entradasExistentes = entradaRepository.findByProyeccionAndButaca(proyeccion, butaca);
        if(!entradasExistentes.isEmpty()){
            throw new RuntimeException("Butaca ya ocupada en esta proyección");
        }

        // Control de aforo máximo de la sala
        long entradasVendidas = entradaRepository.findByProyeccion(proyeccion).size();
        if(entradasVendidas >= sala.getNumButaca()){
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
        Sala sala = proyeccion.getProyeccionSalas().get(0).getSala();

        List<Butaca> todasButacas = sala.getButacas();
        List<Entrada> entradasVendidas = entradaRepository.findByProyeccion(proyeccion);

        return todasButacas.stream()
                .filter(b -> entradasVendidas.stream()
                        .noneMatch(e -> e.getButaca().equals(b) && !e.getCancelada()))
                .toList();
    }

    // =========================== CANCELAR ENTRADA =================================
    // Cancelamos la entrada
    public Entrada cancelarEntrada(Long id_entrada){
        Entrada entrada = entradaRepository.findById(id_entrada)
                .orElseThrow(() ->  new RuntimeException("Entrada no encontrada"));

        if(entrada.getCancelada()){
            throw new RuntimeException("Entrada ya cancelada");
        }

        entrada.setCancelada(true);
        return entradaRepository.save(entrada);
    }
}
