package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Butaca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idButaca;

    @Column(nullable = false)
    private String posicion;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "idSala", nullable = false)
    @JsonIgnoreProperties("butacas")
    private Sala sala;

    @OneToMany(mappedBy = "butaca")
    @ToString.Exclude // @ToString.Exclude le dice a Lombok que NO incluya ese atributo en el método toString() que genera automáticamente para la clase.
    @JsonManagedReference(value = "butaca-entrada")
    private List<Entrada> entradas;
}
