package com.example.cine.mappers;

import com.example.cine.dto.EntradaDTO;
import com.example.cine.entity.Entrada;

public class EntradaMapper {

    public static EntradaDTO toDTO(Entrada entrada) {
        EntradaDTO dto = new EntradaDTO();

        dto.setIdEntrada(entrada.getIdEntrada());
        dto.setNombreAsistente(entrada.getAsistente().getNombre());
        dto.setTituloPelicula(entrada.getProyeccion().getPelicula().getTitulo());
        dto.setSala(entrada.getProyeccion().getSala().getDescripcion());

        dto.setFila(entrada.getButaca().getFila()); // ahora fila es String
        dto.setNumeroButaca(entrada.getButaca().getNumero());

        dto.setFecha(entrada.getProyeccion().getFecha());
        dto.setHorario(entrada.getProyeccion().getHorario());

        dto.setPrecio(entrada.getPrecio());
        dto.setCancelada(entrada.getCancelada());

        return dto;
    }
}
