package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.TipoCurso;

public record CursoResponseDTO(
        Long id,
        String nome,
        TipoCurso tipo
) {
}
