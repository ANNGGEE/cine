package com.example.cine.entity;

import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
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
    @JsonBackReference
    private Pelicula pelicula;

    @OneToMany(mappedBy = "proyeccion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Entrada> entradas;

    @ManyToMany(mappedBy = "proyecciones")
    @JsonIgnoreProperties({"entradas", "proyecciones"})
    private List<Asistente> asistentes;
}
