package com.example.cine.repositories;

import com.example.cine.entity.Pelicula;
import com.example.cine.entity.Proyeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long>{
    List<Proyeccion> findBySalaId_sala(Long id_sala);

    List<Proyeccion> findByFecha(LocalDate fecha);
    List<Proyeccion> findByPelicula(Pelicula pelicula);
}
