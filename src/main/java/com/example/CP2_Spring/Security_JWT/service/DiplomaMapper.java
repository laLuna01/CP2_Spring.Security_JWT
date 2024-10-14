package com.example.CP2_Spring.Security_JWT.service;

import com.example.CP2_Spring.Security_JWT.dto.DiplomaRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.DiplomaResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Curso;
import com.example.CP2_Spring.Security_JWT.model.Diploma;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import com.example.CP2_Spring.Security_JWT.repository.CursoRepository;
import com.example.CP2_Spring.Security_JWT.repository.DiplomadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DiplomaMapper {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private DiplomadoRepository diplomadoRepository;

    public Diploma requestRecordToDiploma(DiplomaRequestDTO diplomaRequestDTO) {
        Diploma diploma = new Diploma();
        Curso curso = cursoRepository.findById(diplomaRequestDTO.idCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Diplomado diplomado = diplomadoRepository.findById(diplomaRequestDTO.idDiplomado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diplomado não encontrado"));
        diploma.setDiplomado(diplomado);
        diploma.setCurso(curso);
        diploma.setDataDeConclusao(diplomaRequestDTO.dataDeConclusao());
        diploma.setSexoReitor(diplomaRequestDTO.sexoReitor());
        diploma.setNomeReitor(diplomaRequestDTO.nomeReitor());
        return diploma;
    }

    public DiplomaResponseDTO diplomaToResponseDTO(Diploma diploma, Link link) {
        return new DiplomaResponseDTO(
                diploma.getId(),
                diploma.getDiplomado(),
                diploma.getCurso(),
                diploma.getDataDeConclusao(),
                diploma.getSexoReitor(),
                diploma.getNomeReitor(),
                link
        );
    }
}
