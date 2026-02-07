package com.example.cine.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProyeccionDTO {
    private Long idProyeccion;
    private LocalDate fecha;
    private LocalTime horario;
    private PeliculaDTO pelicula;
    private SalaDTO sala;

    public Long getIdProyeccion() {
        return idProyeccion;
    }

    public void setIdProyeccion(Long idProyeccion) {
        this.idProyeccion = idProyeccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public PeliculaDTO getPelicula() {
        return pelicula;
    }

    public void setPelicula(PeliculaDTO pelicula) {
        this.pelicula = pelicula;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
}
