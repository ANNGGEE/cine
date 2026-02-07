package com.example.cine.dto;

import lombok.Data;

@Data
public class ButacaDTO {
    private Long idButaca;
    private String fila;
    private Integer numero;
    private Long idSala;
    private String posicion;

    public Long getIdButaca() {
        return idButaca;
    }

    public void setIdButaca(Long idButaca) {
        this.idButaca = idButaca;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
}
