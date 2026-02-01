package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"idProyeccion", "idButaca"}
                )
        }
)
public class Entrada{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrada;

    private LocalDateTime fecha_compra;

    private Double precio;

    private Boolean cancelada = false;

    @ManyToOne
    @JoinColumn(name = "idProyeccion", nullable = false)
    private Proyeccion proyeccion;

    // @ManyToOne
    // @JoinColumn(name = "idButaca", nullable = false)
    // private Butaca butaca;

    @ManyToOne
    @JoinColumn(name = "idAsistente", nullable = false)
    private Asistente asistente;

    @ManyToOne
    @JoinColumn(name = "idButaca", nullable = false)
    @JsonIgnoreProperties("entrada")
    private Butaca butaca;
}
