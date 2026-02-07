package com.example.cine.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProyeccionDTO {
    private Long idProyeccion;
    private String sala;
    private LocalDate fecha;
    private String horario;
    private String peliculaTitulo;
    private String salaNombre;

    public Long getIdProyeccion() {
        return idProyeccion;
    }

    public void setIdProyeccion(Long idProyeccion) {
        this.idProyeccion = idProyeccion;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPeliculaTitulo() {
        return peliculaTitulo;
    }

    public void setPeliculaTitulo(String peliculaTitulo) {
        this.peliculaTitulo = peliculaTitulo;
    }

    public String getSalaNombre() {
        return salaNombre;
    }

    public void setSalaNombre(String salaNombre) {
        this.salaNombre = salaNombre;
    }
}
