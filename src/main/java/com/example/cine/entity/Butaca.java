package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Butaca{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_butaca;

    private String posicion;

    private String fila;
    private Integer numero;

    @OneToOne(mappedBy = "butaca")
    private Entrada entrada;
}
