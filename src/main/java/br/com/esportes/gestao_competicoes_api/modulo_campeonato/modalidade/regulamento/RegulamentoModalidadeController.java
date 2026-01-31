package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento.RegulamentoCampeonatoModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regulamento-modalidade")
public class RegulamentoModalidadeController {

    private final RegulamentoModalidadeService regulamentoModalidadeService;

    public RegulamentoModalidadeController(RegulamentoModalidadeService regulamentoModalidadeService) {
        this.regulamentoModalidadeService = regulamentoModalidadeService;
    }

    @PostMapping("/{idModalidade}/adicionar-regra")
    @Operation(summary = "Adicionar Regra à Modalidade", description = "Adiciona uma regra específica (Ex: tempo de jogo) a uma modalidade existente.")
    public ResponseEntity<RegulamentoModalidadeModel> adicionarRegra(
            @PathVariable Long idModalidade,
            @RequestBody RegulamentoModalidadeModel regra) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(regulamentoModalidadeService.adicionarRegra(idModalidade, regra));
    }

    @GetMapping("/buscar-regulamento-por-id/{idRegulamentoModalidade}")
    @Operation(summary = "Buscar regulamento modalidade", description = "Perfil: Organizador (Comissão Técnica)/Buscar informações do regulamento pelo id do regulamento.")
    public ResponseEntity<RegulamentoModalidadeModel> buscarCampeonatoPorId(@PathVariable Long idRegulamentoModalidade) {
        RegulamentoModalidadeModel regulamentoModalidadeModel = regulamentoModalidadeService.buscarRegulamentoCampeonato(idRegulamentoModalidade);
        return ResponseEntity.ok(regulamentoModalidadeModel);
    }

    @PutMapping("/{idRegra}")
    @Operation(summary = "Atualizar Regra da Modalidade", description = "Corrige ou altera uma regra específica sem mudar a modalidade vinculada.")
    public ResponseEntity<RegulamentoModalidadeModel> atualizarRegra(
            @PathVariable Long idRegra,
            @RequestBody RegulamentoModalidadeModel regra) {

        return ResponseEntity.ok(regulamentoModalidadeService.atualizarRegulamentoModalidade(idRegra, regra));
    }

    @DeleteMapping("/{idRegra}")
    public ResponseEntity<Void> deletarRegra(@PathVariable Long idRegra) {
        regulamentoModalidadeService.deletarRegra(idRegra);
        return ResponseEntity.noContent().build();
    }
}