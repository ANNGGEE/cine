package com.example.cine.entity;

import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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
    @ToString.Exclude
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "idPelicula", nullable = false)
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Entrada> entradas;

    @ManyToMany(mappedBy = "proyecciones")
    @ToString.Exclude
    private List<Asistente> asistentes;
}
