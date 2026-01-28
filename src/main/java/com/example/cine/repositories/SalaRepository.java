package com.example.cine.repositories;

import com.example.cine.entity.Proyeccion;
import com.example.cine.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

}
