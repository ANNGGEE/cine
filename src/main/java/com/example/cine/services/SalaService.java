package com.example.cine.services;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        // Guardamos la sala primero
        Sala salaGuardada = salaRepository.save(sala);

        // Creamos las butacas según la capacidad
        int totalButacas = sala.getCapacidad();
        int columnas = 10; // máximo de columnas por fila
        int filas = (int) Math.ceil((double) totalButacas / columnas);

        List<Butaca> listaButacas = new ArrayList<>();
        char filaLetra = 'A';
        int creadas = 0;

        for (int f = 0; f < filas && creadas < totalButacas; f++) {
            for (int n = 1; n <= columnas && creadas < totalButacas; n++) {
                Butaca b = new Butaca();
                b.setSala(salaGuardada);
                b.setFila(String.valueOf(filaLetra));
                b.setNumero(n);
                b.setPosicion(filaLetra + String.valueOf(n)); // <-- CORREGIDO

                listaButacas.add(b);
                creadas++;
            }
            filaLetra++;
        }

        // Guardamos todas las butacas en un solo batch
        butacaRepository.saveAll(listaButacas);

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
