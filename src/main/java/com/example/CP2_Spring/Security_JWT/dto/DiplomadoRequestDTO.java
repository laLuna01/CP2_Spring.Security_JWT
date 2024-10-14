package com.example.CP2_Spring.Security_JWT.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DiplomadoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "A nacionalidade é obrigatória")
        String nacionalidade,
        @NotBlank(message = "A naturalidade é obrigatória")
        String naturalidade,
        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$", message = "O RG deve seguir o formato XX.XXX.XXX-X")
        String rg
) {
}
