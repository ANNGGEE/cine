package com.example.cine.services;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.repositories.ButacaRepository;
import com.example.cine.repositories.EntradaRepository;
import com.example.cine.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final EntradaRepository entradaRepository;

    public SalaService(SalaRepository salaRepository, EntradaRepository entradaRepository) {
        this.salaRepository = salaRepository;
        this.entradaRepository = entradaRepository;
    }

    public Sala crearSala(Sala sala) {
        // Creamos lista de butacas
        List<Butaca> listaButacas = new ArrayList<>();
        int totalButacas = sala.getCapacidad();
        int columnas = 10;
        int filas = (int) Math.ceil((double) totalButacas / columnas);
        char filaLetra = 'A';
        int creadas = 0;

        for (int f = 0; f < filas && creadas < totalButacas; f++) {
            for (int n = 1; n <= columnas && creadas < totalButacas; n++) {
                Butaca b = new Butaca();
                b.setFila(String.valueOf(filaLetra));
                b.setNumero(n);
                b.setPosicion(filaLetra + String.valueOf(n));
                b.setSala(sala);
                listaButacas.add(b);
                creadas++;
            }
            filaLetra++;
        }

        sala.setButacas(listaButacas); // Asignamos todas antes de guardar
        return salaRepository.save(sala); // Guarda Sala + Butacas
    }

    public void mostrarButacasDeProyeccion(Proyeccion proyeccion) {

        Sala sala = proyeccion.getSala();
        List<Butaca> butacas = sala.getButacas();

        System.out.println("🎬 Proyección en sala: " + sala.getDescripcion());
        System.out.println("📅 Fecha: " + proyeccion.getFecha() + " " + proyeccion.getHorario());
        System.out.println("------------------------------------------------");

        for (Butaca b : butacas) {

            boolean ocupada = entradaRepository
                    .existsByProyeccionAndButacaAndCanceladaFalse(proyeccion, b);

            String estado = ocupada ? "❌ OCUPADA" : "✅ LIBRE";

            System.out.println(
                    "Butaca " + b.getPosicion() + " (" + b.getFila() + b.getNumero() + ") → " + estado
            );
        }

        System.out.println("------------------------------------------------");
    }

    public List<Sala> obtenerTodas() {
        return salaRepository.findAll();
    }

    public Sala obtenerPorId(Long idSala) {
        return salaRepository.findById(idSala)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

        // ================= ELIMINAR =================
        public void eliminar(Long id) {
            Sala sala = obtenerPorId(id);
            salaRepository.delete(sala);
        }
    }
