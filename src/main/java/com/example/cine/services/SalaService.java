package com.example.cine.services;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final ButacaRepository butacaRepository;

    public SalaService(SalaRepository salaRepository, ButacaRepository butacaRepository) {
        this.salaRepository = salaRepository;
        this.butacaRepository = butacaRepository;
    }

    // ================= CREAR SALA =================
    public Sala crearSala(Sala sala) {
        if (sala.getNumero() <= 0) {
            throw new RuntimeException("El número de la sala debe ser mayor que 0");
        }
        if (sala.getCapacidad() <= 0) {
            throw new RuntimeException("La sala debe tener al menos 1 butaca");
        }

        // Guardamos la sala
        Sala salaGuardada = salaRepository.save(sala);

        // Creamos las butacas según la capacidad
        int total = sala.getCapacidad();
        int columnas = 10;
        int filas = (int) Math.ceil((double) total / columnas);

        char filaLetra = 'A';
        int creadas = 0;

        for (int f = 0; f < filas && creadas < total; f++) {
            for (int n = 1; n <= columnas && creadas < total; n++) {

                Butaca b = new Butaca();
                b.setSala(salaGuardada);
                b.setFila(String.valueOf(filaLetra));
                b.setNumero(n);
                b.setPosicion(filaLetra + String.valueOf(n));

                butacaRepository.save(b);
                creadas++;
            }
            filaLetra++;
        }
        return salaGuardada;
    }

    // ================== OBTENER TODAS LAS SALAS ========================
    public List<Sala> obtenerTodas(){
        return salaRepository.findAll();
    }

    // ============================== OBTENER SALAS POR ID =========================
    public Sala obtenerPorId(Long idSala){
        return salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

    // ================= ELIMINAR =================
    public void eliminar(Long id) {
        Sala sala = obtenerPorId(id);
        salaRepository.delete(sala);
    }
}
