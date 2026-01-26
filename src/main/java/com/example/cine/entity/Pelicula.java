package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pelicula;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Proyeccion> proyeccion;
}
