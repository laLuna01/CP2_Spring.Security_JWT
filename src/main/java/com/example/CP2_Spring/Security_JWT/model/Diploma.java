package com.example.CP2_Spring.Security_JWT.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Diplomas")
public class Diploma {
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "diplomado_id")
    private Diplomado diplomado;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @NotBlank
    @Column(name = "dataDeConclusao")
    private String dataDeConclusao;
    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "sexoReitor")
    private Sexo sexoReitor;
    @NotBlank
    @Column(name = "nomeReitor")
    private String nomeReitor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Diplomado getDiplomado() {
        return diplomado;
    }

    public void setDiplomado(Diplomado diplomado) {
        this.diplomado = diplomado;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getDataDeConclusao() {
        return dataDeConclusao;
    }

    public void setDataDeConclusao(String dataDeConclusao) {
        this.dataDeConclusao = dataDeConclusao;
    }

    public Sexo getSexoReitor() {
        return sexoReitor;
    }

    public void setSexoReitor(Sexo sexoReitor) {
        this.sexoReitor = sexoReitor;
    }

    public String getNomeReitor() {
        return nomeReitor;
    }

    public void setNomeReitor(String nomeReitor) {
        this.nomeReitor = nomeReitor;
    }
}
