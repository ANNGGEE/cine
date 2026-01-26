package com.example.cine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class Pelicula {
    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<Proyeccion> proyeccion;
}
