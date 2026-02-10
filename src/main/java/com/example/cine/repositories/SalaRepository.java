package com.example.cine.repositories;

import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    // Por defecto es LAZY
    //Con JOIN FETCH se le dice a JPA que cuando traiga sala se traiga también sus proyecciones en la misma query
    @Query("SELECT s FROM Sala s JOIN FETCH s.proyecciones WHERE s.idSala = :id")
    Sala findByIdWithProyecciones(@Param("id") Long id);
}
