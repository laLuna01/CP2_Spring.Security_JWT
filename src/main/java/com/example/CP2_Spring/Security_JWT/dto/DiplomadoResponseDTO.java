package com.example.CP2_Spring.Security_JWT.dto;

public record DiplomadoResponseDTO(
        Long id,
        String nome,
        String nacionalidade,
        String naturalidade,
        String rg
) {
}
