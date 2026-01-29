package com.example.cine.dto;

import lombok.Data;

import java.nio.DoubleBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EntradaDTO {
    private Long idEntrada;
    private LocalDateTime fechaCompra;
    private Double precio;
    private Boolean cancelada;
    private Long idProyeccion;
    private Long idButaca;
    private Long idAsistente;
}
