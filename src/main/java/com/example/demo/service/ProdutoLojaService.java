package com.example.demo.service;

import com.example.demo.entity.ProdutoLoja;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoLojaService {

    private final ElasticsearchOperations elasticsearchOperations;

    public ProdutoLojaService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private List<ProdutoLoja> executarQuery(Criteria criteria) {
        return elasticsearchOperations.search(new CriteriaQuery(criteria), ProdutoLoja.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public ProdutoLoja cadastrarProduto(ProdutoLoja produto) {
        return elasticsearchOperations.save(produto);
    }

    public List<ProdutoLoja> buscarPorNome(String termo) {
        return executarQuery(new Criteria("nome").matches(termo));
    }

    public List<ProdutoLoja> buscarPorDescricao(String termo) {
        return executarQuery(new Criteria("descricao").matches(termo));
    }

    public List<ProdutoLoja> buscarPorFraseExata(String termo) {
        String queryJson = "{ \"match_phrase\": { \"descricao\": \"" + termo + "\" } }";
        return elasticsearchOperations.search(new StringQuery(queryJson), ProdutoLoja.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<ProdutoLoja> buscarFuzzy(String termo) {
        return executarQuery(new Criteria("nome").fuzzy(termo));
    }

    public List<ProdutoLoja> buscarEmMultiplosCampos(String termo) {
        Criteria criteria = new Criteria("nome").matches(termo)
                .or("descricao").matches(termo);
        return executarQuery(criteria);
    }

    public List<ProdutoLoja> buscarTextualComFiltro(String termo, String categoria) {
        Criteria criteria = new Criteria("descricao").matches(termo)
                .and("categoria").is(categoria);
        return executarQuery(criteria);
    }

    public List<ProdutoLoja> buscarPorFaixaPreco(Double min, Double max) {
        Criteria criteria = new Criteria("preco").greaterThanEqual(min).lessThanEqual(max);
        return executarQuery(criteria);
    }

    public List<ProdutoLoja> buscaAvancada(String categoria, String raridade, Double min, Double max) {
        Criteria criteria = new Criteria("categoria").is(categoria)
                .and("raridade").is(raridade)
                .and("preco").greaterThanEqual(min).lessThanEqual(max);
        return executarQuery(criteria);
    }

    private List<ProdutoLoja> buscarTodos() {
        return elasticsearchOperations.search(org.springframework.data.elasticsearch.core.query.Query.findAll(), ProdutoLoja.class)
                .getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorCategoria() {
        return buscarTodos().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCategoria() != null ? p.getCategoria() : "Sem Categoria",
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new com.example.demo.dto.AgregacaoContagemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorRaridade() {
        return buscarTodos().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getRaridade() != null ? p.getRaridade() : "Sem Raridade",
                        Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new com.example.demo.dto.AgregacaoContagemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public com.example.demo.dto.AgregacaoPrecoDTO obterPrecoMedio() {
        Double media = buscarTodos().stream()
                .filter(p -> p.getPreco() != null) // Filtro mágico contra o erro nulo
                .mapToDouble(ProdutoLoja::getPreco)
                .average()
                .orElse(0.0);
        return new com.example.demo.dto.AgregacaoPrecoDTO(media);
    }


    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorFaixaPreco() {
        List<ProdutoLoja> todosComPreco = buscarTodos().stream()
                .filter(p -> p.getPreco() != null) // Removemos os nulos da conta
                .collect(Collectors.toList());

        long abaixo100 = todosComPreco.stream().filter(p -> p.getPreco() < 100).count();
        long de100a300 = todosComPreco.stream().filter(p -> p.getPreco() >= 100 && p.getPreco() <= 300).count();
        long de300a700 = todosComPreco.stream().filter(p -> p.getPreco() > 300 && p.getPreco() <= 700).count();
        long acima700 = todosComPreco.stream().filter(p -> p.getPreco() > 700).count();

        return java.util.Arrays.asList(
                new com.example.demo.dto.AgregacaoContagemDTO("Abaixo de 100", abaixo100),
                new com.example.demo.dto.AgregacaoContagemDTO("De 100 a 300", de100a300),
                new com.example.demo.dto.AgregacaoContagemDTO("De 300 a 700", de300a700),
                new com.example.demo.dto.AgregacaoContagemDTO("Acima de 700", acima700)
        );
    }
}