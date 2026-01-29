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
}
