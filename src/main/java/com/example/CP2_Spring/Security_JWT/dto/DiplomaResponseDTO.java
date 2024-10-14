package com.example.CP2_Spring.Security_JWT.dto;

import com.example.CP2_Spring.Security_JWT.model.Curso;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import com.example.CP2_Spring.Security_JWT.model.Sexo;
import org.springframework.hateoas.Link;

public record DiplomaResponseDTO(
        String id,
        Diplomado diplomado,
        Curso curso,
        String dataDeConclusao,
        Sexo sexoReitor,
        String nomeReitor,
        Link link
) {
}
