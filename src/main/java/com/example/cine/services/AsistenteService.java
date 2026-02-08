package com.example.cine.services;

import com.example.cine.entity.Asistente;
import com.example.cine.repositories.AsistenteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.cine.dto.AsistenteDTO;
import com.example.cine.entity.Asistente;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsistenteService {
    private final AsistenteRepository asistenteRepository;

    public AsistenteService(AsistenteRepository asistenteRepository) {
        this.asistenteRepository = asistenteRepository;
    }

    // ======================== CREAR ASISTENTE ====================================
    public Asistente crearAsistente(Asistente asistente){
        if (asistente.getNombre() == null || asistente.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del asistente es obligatorio");
        }
        return asistenteRepository.save(asistente);
    }

    @Transactional(readOnly = true)
    // ================== OBTENER TODOS LOS ASISTENTES ==========================
    public List<AsistenteDTO> obtenerTodosDTO() {
        return asistenteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ================== OBTENER POR ID =====================================
    public Asistente obtenerPorId(Long id) {
        return asistenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));
    }

    // =================== OBTENER DTO POR ID =============================
    public AsistenteDTO obtenerPorIdDTO(Long id) {
        Asistente a = obtenerPorId(id);
        return toDTO(a);
    }

    // =========================== ACTUALIZAR ASISTENTE ====================
    public Asistente actualizarAsistente(Long id, Asistente asistenteActualizado) {
        Asistente asistente = obtenerPorId(id);
        asistente.setNombre(asistenteActualizado.getNombre());
        return asistenteRepository.save(asistente);
    }

    // ========================== ELIMINAR ASISTENTE =============================
    public void eliminarAsistente(Long id) {
        Asistente asistente = obtenerPorId(id);
        asistenteRepository.delete(asistente);
    }

    // =========================== MÉTODO PRIVADO PARA CONVERTIR ENTIDAD A DTO =========================
    private AsistenteDTO toDTO(Asistente a) {
        AsistenteDTO dto = new AsistenteDTO();
        dto.setIdAsistente(a.getIdAsistente());
        dto.setNombre(a.getNombre());
        dto.setTotalEntradas(a.getEntradas() != null ? a.getEntradas().size() : 0);
        return dto;
    }
}
