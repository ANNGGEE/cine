package com.example.cine.services;

import com.example.cine.entity.Sala;
import com.example.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    // ================== OBTENER TODAS LAS SALAS ========================
    public List<Sala> obtenerTodas(){
        return salaRepository.findAll();
    }

    // ============================== OBTENER SALAS POR ID =========================
    public Sala obtenerPorId(Long id_sala){
        return salaRepository.findById(id_sala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

    // ================= CREAR SALA ====================
    public Sala crearSala(Sala sala){
        return salaRepository.save(sala);
    }
}
