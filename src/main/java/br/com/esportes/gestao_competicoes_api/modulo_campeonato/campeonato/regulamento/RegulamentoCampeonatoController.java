package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regulamento-campeonato")
public class RegulamentoCampeonatoController {

    private final RegulamentoCampeonatoService regulamentoCampeonatoService;

    public RegulamentoCampeonatoController(RegulamentoCampeonatoService regulamentoCampeonatoService) {
        this.regulamentoCampeonatoService = regulamentoCampeonatoService;
    }

    @PostMapping("/{idCampeonato}/adicionar-regulamento-campeonato")
    @Operation(summary = "Adicionar regulamento campeonato ao Campeonato", description = "Perfil: Organizador (Comissão Técnica)/Adiciona um artigo ou cláusula ao regulamento de um campeonato existente.")
    public ResponseEntity<RegulamentoCampeonatoModel> adicionarRegulamentoCampeonato(
            @PathVariable Long idCampeonato,
            @RequestBody RegulamentoCampeonatoModel regra) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(regulamentoCampeonatoService.adicionarRegulamentoCampeonato(idCampeonato, regra));
    }

    @GetMapping("/buscar-regulamento-por-id/{idRegulamentoCampeonato}")
    @Operation(summary = "Buscar regulamento campeonato", description = "Perfil: Organizador (Comissão Técnica)/Buscar informações do regulamento pelo id do regulamento.")
    public ResponseEntity<RegulamentoCampeonatoModel> buscarCampeonatoPorId(@PathVariable Long idRegulamentoCampeonato) {
        RegulamentoCampeonatoModel regulamentoCampeonatoModel = regulamentoCampeonatoService.buscarRegulamentoCampeonato(idRegulamentoCampeonato);
        return ResponseEntity.ok(regulamentoCampeonatoModel);
    }

    @PutMapping("/{idRegulamento}")
    @Operation(summary = "Atualizar regulamento campeonato", description = "Perfil: Organizador (Comissão Técnica)/Altera regulamento.")
    public ResponseEntity<RegulamentoCampeonatoModel> atualizarRegulamentoCampeonato(
            @PathVariable Long idRegulamento,
            @RequestBody RegulamentoCampeonatoModel regulamentoCampeonatoModel) {

        return ResponseEntity.ok(regulamentoCampeonatoService.atualizarRegulamentoCampeonato(idRegulamento, regulamentoCampeonatoModel));
    }

    @DeleteMapping("/{idRegulamento}")
    @Operation(summary = "Deletar regulamento campeonato", description = "Perfil: Organizador (Comissão Técnica)/Deletar o regulamento pelo id do regulamento.")
    public ResponseEntity<Void> deletarRegra(@PathVariable Long idRegulamento) {
        regulamentoCampeonatoService.deletarRegra(idRegulamento);
        return ResponseEntity.noContent().build();
    }
}