package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Proyeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proyeccion;

    private LocalDate horario;
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    private List<Entrada> entradas;
}
