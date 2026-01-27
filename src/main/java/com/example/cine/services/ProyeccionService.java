package com.example.cine.services;

import com.example.cine.entity.Proyeccion;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.ProyeccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProyeccionService{

    private ProyeccionRepository proyeccionRepository;
    private EntradaRepository entradaRepository;

    ProyeccionService(ProyeccionRepository proyeccionRepository,
                      EntradaRepository entradaRepository) {
        this.proyeccionRepository = proyeccionRepository;
        this.entradaRepository = entradaRepository;
    }

    public List<Proyeccion> obtenerPorSala(Long id_sala){
        return proyeccionRepository.findBySalaId_sala(id_sala);
    }

    public List<Proyeccion> obtenerPorFecha(LocalDate fecha) {
        return proyeccionRepository.findByFecha(fecha);
    }

    public long ocupacionProyeccion(Long id_proyeccion){
        Proyeccion proyeccion = proyeccionRepository.findById(id_proyeccion)
                .orElseThrow(() -> new RuntimeException("Proyeccion no encontrada"));

        return entradaRepository.findByProyeccion(proyeccion)
                .stream()
                .filter(e -> !e.getCancelada())
                .count();
    }
}
