package com.example.CP2_Spring.Security_JWT.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Cursos")
public class Curso {
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "nome")
    private String nome;
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoCurso tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoCurso getTipo() {
        return tipo;
    }

    public void setTipo(TipoCurso tipo) {
        this.tipo = tipo;
    }
}
