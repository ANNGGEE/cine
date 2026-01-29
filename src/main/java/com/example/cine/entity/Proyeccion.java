package com.example.cine.entity;

import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class Proyeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyeccion;

    private LocalTime horario;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "idSala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "idPelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    private List<Entrada> entradas;

    @ManyToMany(mappedBy = "proyecciones")
    private List<Asistente> asistentes;
}
