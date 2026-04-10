package com.example.demo.controller;

import com.example.demo.entity.ProdutoLoja;
import com.example.demo.service.ProdutoLojaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoLojaController {

    private final ProdutoLojaService service;

    public ProdutoLojaController(ProdutoLojaService service) {
        this.service = service;
    }

    @PostMapping
    public ProdutoLoja cadastrar(@RequestBody ProdutoLoja produto){
        return service.cadastrarProduto(produto);
    }

    @GetMapping("/busca/nome")
    public List<ProdutoLoja> buscarPorNome(@RequestParam String termo) {
        return service.buscarPorNome(termo);
    }

    @GetMapping("/busca/descricao")
    public List<ProdutoLoja> buscarPorDescricao(@RequestParam String termo) {
        return service.buscarPorDescricao(termo);
    }

    @GetMapping("/busca/frase")
    public List<ProdutoLoja> buscarPorFraseExata(@RequestParam String termo) {
        return service.buscarPorFraseExata(termo);
    }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoLoja> buscarFuzzy(@RequestParam String termo) {
        return service.buscarFuzzy(termo);
    }

    @GetMapping("/busca/multicampos")
    public List<ProdutoLoja> buscarMulticampos(@RequestParam String termo) {
        return service.buscarEmMultiplosCampos(termo);
    }

    @GetMapping("/busca/com-filtro")
    public List<ProdutoLoja> buscarComFiltro(@RequestParam String termo, @RequestParam String categoria) {
        return service.buscarTextualComFiltro(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoLoja> buscarPorFaixaDePreco(@RequestParam Double min, @RequestParam Double max) {
        return service.buscarPorFaixaPreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoLoja> buscaAvancada(@RequestParam String categoria,
                                           @RequestParam String raridade,
                                           @RequestParam Double min,
                                           @RequestParam Double max) {
        return service.buscaAvancada(categoria, raridade, min, max);
    }

    @GetMapping("/agregacoes/por-categoria")
    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorCategoria() {
        return service.agruparPorCategoria();
    }

    @GetMapping("/agregacoes/por-raridade")
    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorRaridade() {
        return service.agruparPorRaridade();
    }

    @GetMapping("/agregacoes/preco-medio")
    public com.example.demo.dto.AgregacaoPrecoDTO obterPrecoMedio() {
        return service.obterPrecoMedio();
    }

    @GetMapping("/agregacoes/faixas-preco")
    public List<com.example.demo.dto.AgregacaoContagemDTO> agruparPorFaixasDePreco() {
        return service.agruparPorFaixaPreco();
    }

}