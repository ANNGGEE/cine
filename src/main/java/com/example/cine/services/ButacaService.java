package com.example.cine.services;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ButacaService {
    private final ButacaRepository butacaRepository;
    private final SalaRepository salaRepository;

    public ButacaService(ButacaRepository butacaRepository, SalaRepository salaRepository) {
        this.butacaRepository = butacaRepository;
        this.salaRepository = salaRepository;
    }

    // ================= CREAR BUTACA ======================
    public Butaca crearButaca(Butaca butaca, Long idSala){
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        butaca.setSala(sala);
        return butacaRepository.save(butaca);
    }

    // ======================= OBTENER BUTACAS LIBRES =======================
    public List<Butaca> obtenerButacasLibres(Proyeccion proyeccion){
        return butacaRepository.findButacasLibres(proyeccion.getSala().getIdSala(), proyeccion.getIdProyeccion());
    }

    // ================ OBTENEMOS TODAS LAS BUTACAS ====================
    public List<Butaca> obtenerTodas(){
        return butacaRepository.findAll();
    }

    // =================== OBTENEMOS LAS BUTACAS POR ID ===========
    public Butaca obtenerPorId(Long id){
        return butacaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe ninguna butaca con ID \" + idButaca"));
    }

    // ================ OBTENEMOS POR SALA ===================
    public List<Butaca> obtenerPorSala(Long idSala){
        Sala sala = salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));

        return butacaRepository.findBySala(sala);
    }

    // ================== ELIMINAMOS LAS BUTACAS ================
    public void eliminarButaca(Long id){
        Butaca butaca = obtenerPorId(id);
        butacaRepository.delete(butaca);
    }
}
