package com.example.cine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Entrada{
    private Date fecha_compra;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_proyeccion", referencedColumnName = "id")
    private Proyeccion proyeccion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_butaca", referencedColumnName = "id")
    private Butaca butaca;
}
