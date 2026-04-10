package com.example.demo.dto;

public class AgregacaoContagemDTO {
    private String chave;
    private Long quantidade;
    public AgregacaoContagemDTO(String chave, Long quantidade) {
        this.chave = chave;
        this.quantidade = quantidade;
    }

    public String getChave() { return chave; }
    public void setChave(String chave) { this.chave = chave; }
    public Long getQuantidade() { return quantidade; }
    public void setQuantidade(Long quantidade) { this.quantidade = quantidade; }
}