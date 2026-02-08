package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Asistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistente;

    private String nombre;

    @OneToMany(mappedBy = "asistente", fetch = FetchType.EAGER)
    @ToString.Exclude  // Evitamos LazyInitializationException al imprimir
    @JsonIgnoreProperties("asistente") // Evitamos ciclos en JSON
    private List<Entrada> entradas;

    @ManyToMany
    @JoinTable(
            name = "asistente_proyeccion",
            joinColumns = @JoinColumn(name = "idAsistente"),
            inverseJoinColumns = @JoinColumn(name = "idProyeccion")
    )
    @ToString.Exclude
    @JsonIgnoreProperties("asistentes")
    private List<Proyeccion> proyecciones;
}
