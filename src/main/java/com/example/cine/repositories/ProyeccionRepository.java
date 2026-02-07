package com.example.cine.repositories;

import com.example.cine.entity.Pelicula;
import com.example.cine.entity.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.cine.entity.Proyeccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyeccionRepository extends JpaRepository<Proyeccion, Long>{
    @Query("""
        SELECT p FROM Proyeccion p
        LEFT JOIN FETCH p.sala
        LEFT JOIN FETCH p.pelicula
    """)
    List<Proyeccion> findAllWithSalaAndPelicula();

    @Query("""
        SELECT p FROM Proyeccion p
        LEFT JOIN FETCH p.sala
        LEFT JOIN FETCH p.pelicula
        WHERE p.idProyeccion = :id
    """)
    Optional<Proyeccion> findByIdWithSalaAndPelicula(@Param("id") Long id);

    List<Proyeccion> findBySala(Sala sala);
    Page<Proyeccion> findByFecha(LocalDate fecha, Pageable pageable);
    List<Proyeccion> findByPelicula(Pelicula pelicula);

    List<Proyeccion> findByPeliculaIdPelicula(Long idPelicula);

    @Query("SELECT p FROM Proyeccion p WHERE p.sala.idSala = :idSala")
    List<Proyeccion> findBySalaId(@Param("idSala") Long idSala);
}
