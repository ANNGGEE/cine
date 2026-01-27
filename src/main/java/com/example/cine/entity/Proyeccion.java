package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Proyeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_proyeccion;

    private Date horario;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "pelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    private List<Entrada> entradas;
}
