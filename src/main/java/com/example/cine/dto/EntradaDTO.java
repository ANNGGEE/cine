package com.example.cine.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EntradaDTO {

    private Long idEntrada;

    private String nombreAsistente;

    private String tituloPelicula;

    private String sala;

    private String fila;
    private Integer numeroButaca;

    private LocalDate fecha;
    private LocalTime horario;

    private Double precio;

    private Boolean cancelada;
}
