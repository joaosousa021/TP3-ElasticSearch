package com.example.demo.controller;

import com.example.demo.entity.PainelTaticoMissao;
import com.example.demo.service.MissaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService missaoService;

    public MissaoController(MissaoService missaoService) {
        this.missaoService = missaoService;
    }

    @GetMapping("/top15dias")
    public List<PainelTaticoMissao> getTopMissoesUltimos15Dias() {
        return missaoService.obterTopMissoesUltimos15Dias();
    }
}