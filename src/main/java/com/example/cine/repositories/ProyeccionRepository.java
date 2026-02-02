package com.example.cine.repositories;

import com.example.cine.entity.Pelicula;
import com.example.cine.entity.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import com.example.cine.entity.Proyeccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long>{
    @Query("SELECT p FROM Proyeccion p JOIN FETCH p.sala JOIN FETCH p.pelicula")
    List<Proyeccion> findAllWithSalaAndPelicula();

    List<Proyeccion> findBySala(Sala sala);
    Page<Proyeccion> findByFecha(LocalDate fecha, Pageable pageable);
    List<Proyeccion> findByPelicula(Pelicula pelicula);
}
