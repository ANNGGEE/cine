package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Proyeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proyeccion;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    private List<Sala> sala;
}
