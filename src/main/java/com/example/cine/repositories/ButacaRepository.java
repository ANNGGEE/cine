package com.example.cine.repositories;

import com.example.cine.entity.Butaca;
import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import com.example.cine.services.SalaService;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long>{
    List<Butaca> findBySala(Sala sala);
    List<Butaca> findBySalaId(Long idSala);
}
