package com.example.cine.services;

import com.example.cine.entity.Asistente;
import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.entity.Proyeccion;
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

    public Entrada comprarEntrada(Long id_asistente, Long id_proyeccion, Long id_butaca, Double precio) {
        Asistente asistente = asistenteRepository.findById(id_asistente)
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        Proyeccion proyeccion = proyeccionRepository.findById(id_proyeccion)
                .orElseThrow(() -> new RuntimeException("Proyección no encontrada"));

        Butaca butaca = butacaRepository.findById(id_butaca)
                .orElseThrow(() -> new RuntimeException("Butaca no encontrada"));

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

        // Creamos la entrada
        Entrada entrada = new Entrada();
        entrada.setAsistente(asistente);
        entrada.setProyeccion(proyeccion);
        entrada.setButaca(butaca);
        entrada.setPrecio(precio);

        return entradaRepository.save(entrada);
    }
}
