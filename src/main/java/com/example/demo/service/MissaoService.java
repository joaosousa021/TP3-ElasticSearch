package com.example.demo.service;

import com.example.demo.entity.PainelTaticoMissao;
import com.example.demo.repository.MissaoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissaoService {

    private final MissaoRepository repository;

    public MissaoService(MissaoRepository repository) {

        this.repository = repository;
    }

    @Cacheable("topMissoesCache")
    public List<PainelTaticoMissao> obterTopMissoesUltimos15Dias() {
        LocalDateTime dataCorte = LocalDateTime.now().minusDays(15);
        return repository.buscarRanking(dataCorte);

    }
    /*

    A consulta do materialized demorava muito para retoranr os valores devido aos calculos matématicos
     mesmo sendo uma view demora, então pra contornar isso usei o sistema de cache nativo do Spring Boot isso deixou
     praticamente instantaneo a busca porque ele deixa guardado no cache o resultado daquela busca

    */
}