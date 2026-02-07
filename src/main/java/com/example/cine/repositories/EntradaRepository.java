package com.example.cine.repositories;

import com.example.cine.entity.Asistente;
import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.entity.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    boolean existsByProyeccionAndButacaAndCanceladaFalse(
            Proyeccion proyeccion,
            Butaca butaca
    );

    long countByProyeccionAndCanceladaFalse(Proyeccion proyeccion);

    long countByAsistenteAndCanceladaFalse(Asistente asistente);

    List<Entrada> findByProyeccion(Proyeccion proyeccion);

    List<Entrada> findByAsistente(Asistente asistente);
}
