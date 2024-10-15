package com.example.CP2_Spring.Security_JWT.controller;

import com.example.CP2_Spring.Security_JWT.dto.DiplomaRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.DiplomaResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Curso;
import com.example.CP2_Spring.Security_JWT.model.Diploma;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import com.example.CP2_Spring.Security_JWT.repository.CursoRepository;
import com.example.CP2_Spring.Security_JWT.repository.DiplomaRepository;
import com.example.CP2_Spring.Security_JWT.repository.DiplomadoRepository;
import com.example.CP2_Spring.Security_JWT.service.DiplomaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/diplomas", produces = {"application/json"})
@Tag(name = "api-diplomas")
public class DiplomaController {
    @Autowired
    private DiplomaRepository diplomaRepository;
    private DiplomaMapper diplomaMapper;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private DiplomadoRepository diplomadoRepository;

    Pageable paginacao = PageRequest.of(0, 2, Sort.by("curso").descending());

    public DiplomaController() {
    }

    @Operation(summary = "Cria um diploma e grava no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diploma cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<DiplomaResponseDTO> createDiploma(@Valid @RequestBody DiplomaRequestDTO diplomaRequestDTO) {
        Diploma diplomaConvertido = diplomaMapper.requestRecordToDiploma(diplomaRequestDTO);
        Diploma diplomaCriado = diplomaRepository.save(diplomaConvertido);
        DiplomaResponseDTO diplomaResponse = diplomaMapper.diplomaToResponseDTO(diplomaCriado, null);
        return new ResponseEntity<>(diplomaResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os diplomas persistidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum diploma encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Diploma>> readDiplomas(Pageable paginacao) {
        List<Diploma> diplomas = diplomaRepository.findAll(paginacao).getContent();
        return ResponseEntity.ok(diplomas);
    }

    @Operation(summary = "Retorna um diploma dado o seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Diploma não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<String> readDiploma(@PathVariable String id) {
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(Long.valueOf(id));
        if (diplomaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Diploma diploma = diplomaSalvo.get();
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DiplomaController.class)
                        .readDiploma(diploma.getId())
        ).withSelfRel();
        String textoFormatado = diplomaMapper.gerarTextoDiploma(diploma);
        return new ResponseEntity<>(textoFormatado, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um diploma já existente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Diploma não encontrado ou atributos informados são inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DiplomaResponseDTO> updateDiploma(@PathVariable String id, @Valid @RequestBody DiplomaRequestDTO diplomaRequestDTO) {
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(Long.valueOf(id));
        if (diplomaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Diploma diploma = diplomaSalvo.get();
        Curso curso = cursoRepository.findById(diplomaRequestDTO.idCurso())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
        Diplomado diplomado = diplomadoRepository.findById(diplomaRequestDTO.idDiplomado())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Diplomado não encontrado"));
        diploma.setDiplomado(diplomado);
        diploma.setCurso(curso);
        diploma.setDataDeConclusao(diplomaRequestDTO.dataDeConclusao());
        diploma.setSexoReitor(diplomaRequestDTO.sexoReitor());
        diploma.setNomeReitor(diplomaRequestDTO.nomeReitor());
        diplomaRepository.save(diploma);
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DiplomaController.class)
                        .readDiploma(diploma.getId())
        ).withSelfRel();
        DiplomaResponseDTO diplomaResponseDTO = diplomaMapper.diplomaToResponseDTO(diploma, link);
        return new ResponseEntity<>(diplomaResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exclui um diploma do banco de dados dado um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Diploma não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiploma(@PathVariable String id) {
        Optional<Diploma> diplomaSalvo = diplomaRepository.findById(Long.valueOf(id));
        if (diplomaSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        diplomaRepository.delete(diplomaSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
