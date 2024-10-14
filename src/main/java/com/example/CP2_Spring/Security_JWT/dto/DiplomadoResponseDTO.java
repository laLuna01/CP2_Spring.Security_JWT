package com.example.CP2_Spring.Security_JWT.dto;

import org.springframework.hateoas.Link;

public record DiplomadoResponseDTO(
        Long id,
        String nome,
        String nacionalidade,
        String naturalidade,
        String rg,
        Link link
) {
}
