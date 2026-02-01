package com.example.cine.mappers;

import com.example.cine.dto.AsistenteDTO;
import com.example.cine.entity.Asistente;

public class AsistenteMapper {

    public static AsistenteDTO toDTO(Asistente asistente) {
        AsistenteDTO dto = new AsistenteDTO();
        dto.setIdAsistente(asistente.getIdAsistente());
        dto.setNombre(asistente.getNombre());
        dto.setTotalEntradas(
                asistente.getEntradas() == null ? 0 : asistente.getEntradas().size()
        );
        return dto;
    }
}
