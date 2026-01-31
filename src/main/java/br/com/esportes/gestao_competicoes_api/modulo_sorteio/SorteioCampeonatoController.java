package br.com.esportes.gestao_competicoes_api.modulo_sorteio;


import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Realizar Sorteio de Grupos",
            description = "Executa o algoritmo de sorteio para a modalidade informada. " +
                    "O sistema cria a quantidade de grupos solicitada, distribui primeiramente os cabeças de chave (se houver) " +
                    "e, em seguida, distribui aleatoriamente as demais equipes, gerando o log de auditoria."
    )
    public ResponseEntity<List<GrupoModel>> realizarSorteio(
            @RequestParam Long modalidadeId,
            @RequestParam Integer numeroDeGrupos) {

        List<GrupoModel> grupos = sorteioService.realizarSorteio(modalidadeId, numeroDeGrupos);
        return ResponseEntity.ok(grupos);
    }
}
