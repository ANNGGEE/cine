package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Butaca{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idButaca;

    private String posicion;
    private String fila;
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "idSala", nullable = false)
    @JsonIgnoreProperties("butacas") // Evitamos ciclos
    private Sala sala;

    @OneToMany(mappedBy = "butaca")
    @ToString.Exclude
    @JsonIgnoreProperties("butaca")
    private List<Entrada> entradas;
}
