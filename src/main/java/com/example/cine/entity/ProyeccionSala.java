package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ProyeccionSala{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_proyeccion")
    private Proyeccion proyeccion;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    private Sala sala;
}
