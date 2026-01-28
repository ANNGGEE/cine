package com.example.cine.repositories;

import com.example.cine.entity.Asistente;
import com.example.cine.entity.Butaca;
import com.example.cine.entity.Entrada;
import com.example.cine.entity.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.WeakHashMap;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long>{
    List<Entrada> findByProyeccion(Proyeccion proyeccion);
    List<Entrada> findByProyeccionAndButaca(Proyeccion proyeccion, Butaca butaca);
    List<Entrada> findByAsistente(Asistente asistente);
    List<Entrada> findByAsistenteAndCanceladaFalse(Asistente asistente);

    boolean existByProyeccionAndButacaAndCanceladaFalse(
            Proyeccion proyeccion,
            Butaca butaca
    );

    long countByProyeccionAndCanceladaFalse(Proyeccion proyeccion);
}
