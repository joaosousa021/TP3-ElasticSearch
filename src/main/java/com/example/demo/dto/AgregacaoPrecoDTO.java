package com.example.demo.dto;

public class AgregacaoPrecoDTO {
    private Double precoMedio;

    public AgregacaoPrecoDTO(Double precoMedio) {
        this.precoMedio = precoMedio;
    }

    public Double getPrecoMedio() { return precoMedio; }
    public void setPrecoMedio(Double precoMedio) { this.precoMedio = precoMedio; }
}