package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.TipoCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O tipo é obrigatório")
        TipoCurso tipo
) {
}
