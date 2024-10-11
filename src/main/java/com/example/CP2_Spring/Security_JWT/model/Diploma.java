package com.example.CP2_Spring.Security_JWT.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Diplomas")
public class Diploma {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "diplomado_id")
    private Diplomado diplomado;
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @Column(name = "dataDeConclusao")
    private String dataDeConclusao;
    @Enumerated(EnumType.STRING)
    @Column(name = "sexoReitor")
    private Sexo sexoReitor;
    @Column(name = "nomeReitor")
    private String nomeReitor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
