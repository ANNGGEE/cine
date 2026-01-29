package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Asistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistente;

    private String nombre;

    @OneToMany(mappedBy = "asistente")
    private List<Entrada> entradas;

    @ManyToMany
    @JoinTable(
            name = "asistente_proyeccion",
            joinColumns = @JoinColumn(name = "idAsistente"),
            inverseJoinColumns = @JoinColumn(name = "idProyeccion")
    )
    private List<Proyeccion> proyecciones;
}
