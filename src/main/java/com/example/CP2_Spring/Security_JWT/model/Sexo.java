package com.example.CP2_Spring.Security_JWT.model;

public enum Sexo {
    M("masculino"),
    F("feminino");

    private String sexo;

    Sexo(String sexo) { this.sexo = sexo; }

    public String getSexo() { return sexo; }
}
