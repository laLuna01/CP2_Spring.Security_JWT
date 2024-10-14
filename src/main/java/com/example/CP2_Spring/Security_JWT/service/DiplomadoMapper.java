package com.example.CP2_Spring.Security_JWT.service;

import com.example.CP2_Spring.Security_JWT.dto.DiplomadoRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.DiplomadoResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import org.springframework.hateoas.Link;

public class DiplomadoMapper {
    public Diplomado requestRecordToDiplomado(DiplomadoRequestDTO diplomadoRequestDTO) {
        Diplomado diplomado = new Diplomado();
        diplomado.setNome(diplomadoRequestDTO.nome());
        diplomado.setNacionalidade(diplomadoRequestDTO.nacionalidade());
        diplomado.setNaturalidade(diplomadoRequestDTO.naturalidade());
        diplomado.setRg(diplomadoRequestDTO.rg());
        return diplomado;
    }

    public DiplomadoResponseDTO diplomadoToResponseDTO(Diplomado diplomado, Link link) {
        return new DiplomadoResponseDTO(
                diplomado.getId(),
                diplomado.getNome(),
                diplomado.getNacionalidade(),
                diplomado.getNaturalidade(),
                diplomado.getRg(),
                link
        );
    }
}
