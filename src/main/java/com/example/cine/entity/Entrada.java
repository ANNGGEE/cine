package com.example.cine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Entrada{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proyeccion", referencedColumnName = "id")
    private Proyeccion proyeccion;
}
