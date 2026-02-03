package com.example.cine.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSala;

    private int numero;
    private String descripcion;
    private int capacidad;

    // Constructor vacío obligatorio para JPA/Hibernate
    public Sala() {}

    // Constructor con parámetros (para tu DataInitializer)
    public Sala(int numero, String descripcion, int capacidad) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.capacidad = capacidad;
    }

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Proyeccion> proyecciones;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("sala")
    private List<Butaca> butacas;

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Proyeccion> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(List<Proyeccion> proyecciones) {
        this.proyecciones = proyecciones;
    }

    public List<Butaca> getButacas() {
        return butacas;
    }

    public void setButacas(List<Butaca> butacas) {
        this.butacas = butacas;
    }
}
