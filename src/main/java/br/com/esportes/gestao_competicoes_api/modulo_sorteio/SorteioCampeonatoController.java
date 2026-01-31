package br.com.esportes.gestao_competicoes_api.modulo_sorteio;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sorteio")
public class SorteioCampeonatoController {

    private final SorteioService sorteioService;

    public SorteioCampeonatoController(SorteioService sorteioService) {
        this.sorteioService = sorteioService;
    }

    @PostMapping("/realizar-sorteio")
    public ResponseEntity<List<GrupoModel>> realizarSorteio(
            @RequestParam Long modalidadeId,
            @RequestParam Integer numeroDeGrupos) {

        List<GrupoModel> grupos = sorteioService.realizarSorteio(modalidadeId, numeroDeGrupos);
        return ResponseEntity.ok(grupos);
    }
}
