package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
// No puede existir más de una entrada con la misma proyección y la misma butaca.
// Una butaca solo puede venderse una vez por proyección
// Da igual el asistente (Aunque se pruebe ya en el service con el existsByProyeccionAndButacaAndCanceladaFalse(...))
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

    private LocalDateTime fechacompra;

    private Double precio;

    private Boolean cancelada = false;

    @ManyToOne
    @JoinColumn(name = "idProyeccion", nullable = false)
    @JsonBackReference(value = "proyeccion-entrada") // JsonBackReference, se usa para evitar bucles infinitos en JSON
    private Proyeccion proyeccion;

    // @ManyToOne
    // @JoinColumn(name = "idButaca", nullable = false)
    // private Butaca butaca;

    @ManyToOne
    @JsonIgnoreProperties({"entradas", "proyecciones"})
    @JoinColumn(name = "idAsistente", nullable = false)
    private Asistente asistente;

    @ManyToOne
    @JoinColumn(name = "idButaca", nullable = false)
    @JsonIgnoreProperties(value = "butaca-entrada")
    @JsonBackReference
    private Butaca butaca;
}
