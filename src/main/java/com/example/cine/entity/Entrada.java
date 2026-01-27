package com.example.cine.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

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

    private Date fecha_compra;

    private Double precio;

    private Boolean cancelada = false;

    @ManyToOne
    @JoinColumn(name = "id_proyeccion")
    private Proyeccion proyeccion;

    @ManyToOne
    @JoinColumn(name = "id_butaca")
    private Butaca butaca;

    @ManyToOne
    @JoinColumn(name = "id_asistente")
    private Asistente asistente;
}
