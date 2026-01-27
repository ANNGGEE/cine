package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Asistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_asistente;

    @OneToMany(mappedBy = "asistente")
    private List<Entrada> entradas;
}
