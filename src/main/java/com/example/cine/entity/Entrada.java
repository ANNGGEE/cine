package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"id_proyeccion", "id_butaca"}
                )
        }
)
public class Entrada{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_entrada;

    private LocalDateTime fecha_compra;

    private Double precio;

    private Boolean cancelada = false;

    @ManyToOne
    @JoinColumn(name = "id_proyeccion", nullable = false)
    private Proyeccion proyeccion;

    @ManyToOne
    @JoinColumn(name = "id_butaca", nullable = false)
    private Butaca butaca;

    @ManyToOne
    @JoinColumn(name = "id_asistente", nullable = false)
    private Asistente asistente;
}
