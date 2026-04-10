package com.example.demo.repository;

import com.example.demo.entity.PainelTaticoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {


    @Query(value = "SELECT * FROM operacoes.vw_painel_tatico_missao WHERE ultima_atualizacao > :dataCorte ORDER BY indice_prontidao DESC LIMIT 10", nativeQuery = true)
    List<PainelTaticoMissao> buscarRanking(@Param("dataCorte") LocalDateTime dataCorte);

}