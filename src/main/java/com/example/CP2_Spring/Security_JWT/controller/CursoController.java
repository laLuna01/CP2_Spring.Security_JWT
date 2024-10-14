package com.example.CP2_Spring.Security_JWT.controller;

import com.example.CP2_Spring.Security_JWT.dto.CursoRequestDTO;
import com.example.CP2_Spring.Security_JWT.dto.CursoResponseDTO;
import com.example.CP2_Spring.Security_JWT.model.Curso;
import com.example.CP2_Spring.Security_JWT.repository.CursoRepository;
import com.example.CP2_Spring.Security_JWT.service.CursoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cursos", produces = {"application/json"})
@Tag(name = "api-diplomas")
public class CursoController {
    private CursoRepository cursoRepository;
    @Autowired
    private CursoMapper cursoMapper;

    Pageable paginacao = PageRequest.of(0, 2, Sort.by("nome").descending());

    public CursoController() {
    }

    @Operation(summary = "Cria um curso e grava no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Atributos informados são inválidos",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<CursoResponseDTO> createCurso(@Valid @RequestBody CursoRequestDTO cursoRequestDTO) {
        Curso cursoConvertido = cursoMapper.requestRecordToCurso(cursoRequestDTO);
        Curso cursoCriado = cursoRepository.save(cursoConvertido);
        CursoResponseDTO cursoResponse = cursoMapper.cursoToResponseDTO(cursoCriado, null);
        return new ResponseEntity<>(cursoResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retorna todos os cursos persistidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nenhum curso encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<Curso>> readCursos(Pageable paginacao) {
        List<Curso> cursos = cursoRepository.findAll(paginacao).getContent();
        return ResponseEntity.ok(cursos);
    }

    @Operation(summary = "Retorna um curso dado o seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> readCurso(@PathVariable Long id) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Curso curso = cursoSalvo.get();
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CursoController.class)
                        .readCurso(curso.getId())
        ).withSelfRel();
        CursoResponseDTO cursoResponseDTO = cursoMapper.cursoToResponseDTO(curso, link);
        return new ResponseEntity<>(cursoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Atualiza um curso já existente no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Curso não encontrado ou atributos informados são inválidos",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponseDTO> updateCurso(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO cursoRequestDTO) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Curso curso = cursoSalvo.get();
        curso.setNome(cursoRequestDTO.nome());
        curso.setTipo(cursoRequestDTO.tipo());
        cursoRepository.save(curso);
        Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CursoController.class)
                        .readCurso(curso.getId())
        ).withSelfRel();
        CursoResponseDTO cursoResponseDTO = cursoMapper.cursoToResponseDTO(curso, link);
        return new ResponseEntity<>(cursoResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exclui um curso do banco de dados dado um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Curso não encontrado",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "200", description = "Exclusão realizada com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        Optional<Curso> cursoSalvo = cursoRepository.findById(id);
        if (cursoSalvo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        cursoRepository.delete(cursoSalvo.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
