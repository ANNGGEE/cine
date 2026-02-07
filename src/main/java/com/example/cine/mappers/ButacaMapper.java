package com.example.cine.mappers;

import com.example.cine.dto.ButacaDTO;
import com.example.cine.entity.Butaca;

public class ButacaMapper {
    public static ButacaDTO toDTO(Butaca b) {
        ButacaDTO dto = new ButacaDTO();
        dto.setIdButaca(b.getIdButaca());
        dto.setFila(b.getFila());
        dto.setNumero(b.getNumero());
        return dto;
    }
}
