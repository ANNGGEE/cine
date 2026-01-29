package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

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
    private Sala sala;

    @OneToMany(mappedBy = "butaca")
    private List<Entrada> entradas;
}
