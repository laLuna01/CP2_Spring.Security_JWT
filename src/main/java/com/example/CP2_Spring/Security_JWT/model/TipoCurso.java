package com.example.CP2_Spring.Security_JWT.model;

public enum TipoCurso {
    GRADUACAO("graduacão"),
    POS_GRADUACAO("pós-graduacão");

    private String curso;

    TipoCurso(String curso) { this.curso = curso; }

    public String getCurso() { return curso; }
}
