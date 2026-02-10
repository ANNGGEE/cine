package com.example.cine.repositories;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.services.SalaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long>{
    // Devuelve las butacas libres de una sala para una proyección concreta
    @Query("SELECT b FROM Butaca b WHERE b.sala.idSala = :idSala " +
           "AND b.idButaca NOT IN (" +
           "SELECT e.butaca.idButaca FROM Entrada e WHERE e.proyeccion.idProyeccion = :idProyeccion AND e.cancelada = false)")
    List<Butaca> findButacasLibres(@Param("idSala") Long idSala, @Param("idProyeccion") Long idProyeccion);

    List<Butaca> findBySala(Sala sala);
    List<Butaca> findBySala_IdSala(Long idSala);
}
