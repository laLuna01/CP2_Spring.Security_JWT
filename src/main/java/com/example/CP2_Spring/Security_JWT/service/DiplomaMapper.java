package com.example.CP2_Spring.Security_JWT.service;

import com.example.CP2_Spring.Security_JWT.dto.DiplomaRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.DiplomaResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Diploma;
import org.springframework.hateoas.Link;

public class DiplomaMapper {
    public Diploma requestRecordToDiploma(DiplomaRequestDTO diplomaRequestDTO) {
        Diploma diploma = new Diploma();
        diploma.setDiplomado(diplomaRequestDTO.diplomado());
        diploma.setCurso(diplomaRequestDTO.curso());
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
