package com.example.cine.mappers;

import com.example.cine.dto.AsistenteDTO;
import com.example.cine.entity.Asistente;

// Desacoplamos la capa de persistencia de la capa de
// presentación, controlando qué datos se exponen
// y evitando problemas de serialización y dependencias

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
