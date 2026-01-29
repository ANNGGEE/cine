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

    // ================= CREAR SALA =================
    public Sala crearSala(Sala sala) {
        if (sala.getNumero() <= 0) {
            throw new RuntimeException("El número de la sala debe ser mayor que 0");
        }
        if (sala.getNumButaca() <= 0) {
            throw new RuntimeException("La sala debe tener al menos 1 butaca");
        }
        return salaRepository.save(sala);
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

    // ================= ELIMINAR =================
    public void eliminar(Long id) {
        Sala sala = obtenerPorId(id);
        salaRepository.delete(sala);
    }
}
