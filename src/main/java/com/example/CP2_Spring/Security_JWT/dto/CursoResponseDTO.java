package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.TipoCurso;
import org.springframework.hateoas.Link;

public record CursoResponseDTO(
        Long id,
        String nome,
        TipoCurso tipo,
        Link link
) {
}
