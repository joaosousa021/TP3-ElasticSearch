package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Data
@Document(indexName = "guilda_loja")
public class ProdutoLoja {
    @Id
    private String id;

    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private Double preco;
}
