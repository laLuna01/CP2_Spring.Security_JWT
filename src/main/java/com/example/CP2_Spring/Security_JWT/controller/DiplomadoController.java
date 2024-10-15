package com.example.CP2_Spring.Security_JWT.controller;

import com.example.CP2_Spring.Security_JWT.dto.DiplomadoRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.DiplomadoResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Diplomado;
import com.example.CP2_Spring.Security_JWT.repository.DiplomadoRepository;
import com.example.CP2_Spring.Security_JWT.service.DiplomadoMapper;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/diplomados", produces = {"application/json"})
@Tag(name = "api-diplomas")
public class DiplomadoController {
    @Autowired
    private DiplomadoRepository diplomadoRepository;
    @Autowired
    private DiplomadoMapper diplomadoMapper;

    Pageable paginacao = PageRequest.of(0, 2, Sort.by("nome").descending());

    public DiplomadoController() {
    }

    @Operation(summary = "Cria um diplomado e grava no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Diplomado cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<DiplomadoResponseDTO> createDiplomado(@Valid @RequestBody DiplomadoRequestDTO diplomadoRequestDTO) {
        Diplomado diplomadoConvertido = diplomadoMapper.requestRecordToDiplomado(diplomadoRequestDTO);
        Diplomado diplomadoCriado = diplomadoRepository.save(diplomadoConvertido);
        DiplomadoResponseDTO diplomadoResponse = diplomadoMapper.diplomadoToResponseDTO(diplomadoCriado, null);
        return new ResponseEntity<>(diplomadoResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os diplomados persistidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum diplomado encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Diplomado>> readDiplomados(Pageable paginacao) {
        List<Diplomado> diplomados = diplomadoRepository.findAll(paginacao).getContent();
        return ResponseEntity.ok(diplomados);
    }

    @Operation(summary = "Retorna um diplomado dado o seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Diplomado não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DiplomadoResponseDTO> readDiplomado(@PathVariable Long id) {
        Optional<Diplomado> diplomadoSalvo = diplomadoRepository.findById(id);
        if (diplomadoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Diplomado diplomado = diplomadoSalvo.get();
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DiplomadoController.class)
                        .readDiplomado(diplomado.getId())
        ).withSelfRel();
        DiplomadoResponseDTO diplomadoResponseDTO = diplomadoMapper.diplomadoToResponseDTO(diplomado, link);
        return new ResponseEntity<>(diplomadoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um diplomado já existente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Diplomado não encontrado ou atributos informados são inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DiplomadoResponseDTO> updateDiplomado(@PathVariable Long id, @Valid @RequestBody DiplomadoRequestDTO diplomadoRequestDTO) {
        Optional<Diplomado> diplomadoSalvo = diplomadoRepository.findById(id);
        if (diplomadoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Diplomado diplomado = diplomadoSalvo.get();
        diplomado.setNome(diplomadoRequestDTO.nome());
        diplomado.setNacionalidade(diplomadoRequestDTO.nacionalidade());
        diplomado.setNaturalidade(diplomadoRequestDTO.naturalidade());
        diplomado.setRg(diplomadoRequestDTO.rg());
        diplomadoRepository.save(diplomado);
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DiplomadoController.class)
                        .readDiplomado(diplomado.getId())
        ).withSelfRel();
        DiplomadoResponseDTO diplomadoResponseDTO = diplomadoMapper.diplomadoToResponseDTO(diplomado, link);
        return new ResponseEntity<>(diplomadoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exclui um diplomado do banco de dados dado um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Diplomado não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiplomado(@PathVariable Long id) {
        Optional<Diplomado> diplomadoSalvo = diplomadoRepository.findById(id);
        if (diplomadoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        diplomadoRepository.delete(diplomadoSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
