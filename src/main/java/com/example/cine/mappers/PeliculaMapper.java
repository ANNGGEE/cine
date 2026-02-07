package com.example.cine.mappers;

import com.example.cine.dto.PeliculaDTO;
import com.example.cine.entity.Pelicula;

public class PeliculaMapper {
    public static PeliculaDTO toDTO(Pelicula p) {
        PeliculaDTO dto = new PeliculaDTO();
        dto.setIdPelicula(p.getIdPelicula());
        dto.setTitulo(p.getTitulo());
        dto.setDuracion(p.getDuracion());
        dto.setGenero(p.getGenero());
        return dto;
    }
}
