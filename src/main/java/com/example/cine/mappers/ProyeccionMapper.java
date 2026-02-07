package com.example.cine.mappers;

import com.example.cine.dto.ProyeccionDTO;
import com.example.cine.entity.Proyeccion;

public class ProyeccionMapper {
    public static ProyeccionDTO toDTO(Proyeccion p) {
        ProyeccionDTO dto = new ProyeccionDTO();
        dto.setIdProyeccion(p.getIdProyeccion());
        dto.setFecha(p.getFecha());
        dto.setHorario(p.getHorario());
        dto.setPelicula(PeliculaMapper.toDTO(p.getPelicula()));
        dto.setSala(SalaMapper.toDTO(p.getSala()));
        return dto;
    }
}
