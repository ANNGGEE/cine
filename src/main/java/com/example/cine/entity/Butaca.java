package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String posicion;
    private String fila;
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "idSala", nullable = false)
    @JsonIgnoreProperties("butacas")
    private Sala sala;

    @OneToMany(mappedBy = "butaca")
    @ToString.Exclude
    @JsonIgnoreProperties("entradas")
    private List<Entrada> entradas;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Butaca)) return false;
        Butaca b = (Butaca) o;
        return idButaca != null && idButaca.equals(b.getIdButaca());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
