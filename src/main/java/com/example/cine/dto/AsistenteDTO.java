package com.example.cine.dto;

import lombok.Data;

public class AsistenteDTO {
    private Long idAsistente;
    private String nombre;
    private Integer totalEntradas;

    public Long getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(Long idAsistente) {
        this.idAsistente = idAsistente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Integer totalEntradas) {
        this.totalEntradas = totalEntradas;
    }
}
