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

    @OneToMany(mappedBy = "asistente", fetch = FetchType.LAZY) // LAZY, las entradas no se cargan automáticamente al traer un asistente
    @ToString.Exclude  // Evitamos LazyInitializationException al imprimir
    @JsonIgnoreProperties("asistente") // Evitamos ciclos en JSON, el JSON no incluirá otra vez el asistente dentro de cada entrada, evitando bucles infinitos
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
// EL ToString.Exclude: Evita que Lombok incluya el campo en toString().
// Beneficio	Previene recursión infinita en relaciones bidireccionales.
// Uso típico	Campos @OneToMany o @ManyToMany que referencian la misma entidad.
