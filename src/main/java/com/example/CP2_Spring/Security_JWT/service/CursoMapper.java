package com.example.CP2_Spring.Security_JWT.service;

import com.example.CP2_Spring.Security_JWT.dto.CursoRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.CursoResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Curso;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class CursoMapper {
    public Curso requestRecordToCurso(CursoRequestDTO cursoRequestDTO) {
        Curso curso = new Curso();
        curso.setNome(cursoRequestDTO.nome());
        curso.setTipo(cursoRequestDTO.tipo());
        return curso;
    }

    public CursoResponseDTO cursoToResponseDTO(Curso curso, Link link) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getTipo(),
                link
        );
    }
}
