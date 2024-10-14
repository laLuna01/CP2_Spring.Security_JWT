package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.Curso;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import com.example.CP2_Spring.Security_JWT.model.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiplomaRequestDTO(
        @NotNull(message = "O diplomado é obrigatório")
        Diplomado diplomado,
        @NotNull(message = "O curso é obrigatório")
        Curso curso,
        @NotBlank(message = "A data de conclusão é obrigatória")
        String dataDeConclusao,
        @NotNull(message = "O sexo do reitor é obrigatório")
        Sexo sexoReitor,
        @NotBlank(message = "O nome do reitor é obrigatório")
        String nomeReitor
) {
}
