package com.example.cine.services;

import com.example.cine.entity.Asistente;
import com.example.cine.repositories.AsistenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenteService {

    private final AsistenteRepository asistenteRepository;

    public AsistenteService(AsistenteRepository asistenteRepository) {
        this.asistenteRepository = asistenteRepository;
    }

    // =================== CREAR ASISTENTE =====================
    public Asistente crearAsistente(Asistente asistente){
        if (asistente.getNombre() == null || asistente.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del asistente es obligatorio");
        }

        return asistenteRepository.save(asistente);
    }

    // ================ OBTENER TODOS LOS ASISTENTES ====================
    public List<Asistente> obtenerTodos(){
        return asistenteRepository.findAll();
    }

    // ======================== OBTENER POR ID =================
    public Asistente obtenerPorId(Long id) {
        return asistenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));
    }

    // ====================== ACTUALIZAR ASISTENTE =================
    public Asistente actualizarAsistente(Long id, Asistente asistenteActualizado) {
        Asistente asistente = obtenerPorId(id);
        asistente.setNombre(asistenteActualizado.getNombre());
        return asistenteRepository.save(asistente);
    }

    // ====================== ELIMINAR ASISTENTE =================
    public void eliminarAsistente(Long id) {
        Asistente asistente = obtenerPorId(id);
        asistenteRepository.delete(asistente);
    }
}
