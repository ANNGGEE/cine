package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_sala;

    private String descripcion;
    private int numButaca;

    @ManyToOne
    @JoinColumn(name = "proyeccion_id")
    private Proyeccion proyeccion;
}
