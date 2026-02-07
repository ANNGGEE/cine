package com.example.cine.mappers;

import com.example.cine.dto.SalaDTO;
import com.example.cine.entity.Sala;

public class SalaMapper {
    public static SalaDTO toDTO(Sala s) {
        SalaDTO dto = new SalaDTO();
        dto.setIdSala(s.getIdSala());
        dto.setNumero(s.getNumero());
        dto.setDescripcion(s.getDescripcion());
        dto.setCapacidad(s.getCapacidad());
        return dto;
    }
}
