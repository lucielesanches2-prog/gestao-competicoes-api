package br.com.esportes.gestao_competicoes_api.modulo_relatorio;


import br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta.AtletaModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import br.com.esportes.gestao_competicoes_api.modulo_recurso.RecursoModel;
import br.com.esportes.gestao_competicoes_api.modulo_sorteio.GrupoModel;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/inscritos/modalidade/{idModalidade}")
    @Operation(summary = "Relatório de Equipes Inscritas", description = "Lista todas as equipes confirmadas em uma modalidade.")
    public ResponseEntity<List<RelatorioInscritoDTO>> getRelatorioInscritos(@PathVariable Long idModalidade) {
        return ResponseEntity.ok(relatorioService.gerarRelatorioInscritos(idModalidade));
    }

    @GetMapping("/atletas/equipe/{idEquipe}")
    @Operation(summary = "Relatório de Atletas", description = "Lista todos os atletas vinculados a uma equipe.")
    public ResponseEntity<List<AtletaModel>> getRelatorioAtletas(@PathVariable Long idEquipe) {
        return ResponseEntity.ok(relatorioService.gerarRelatorioAtletas(idEquipe));
    }

    @GetMapping("/sorteios/modalidade/{idModalidade}")
    @Operation(summary = "Resultado dos Sorteios", description = "Exibe os grupos formados e os times sorteados dentro deles.")
    public ResponseEntity<List<GrupoModel>> getRelatorioSorteio(@PathVariable Long idModalidade) {
        return ResponseEntity.ok(relatorioService.gerarRelatorioSorteio(idModalidade));
    }

    @GetMapping("/recursos/campeonato/{idCampeonato}")
    @Operation(summary = "Histórico de Recursos", description = "Mostra todas as disputas administrativas e os pareceres da comissão.")
    public ResponseEntity<List<RecursoModel>> getHistoricoRecursos(@PathVariable Long idCampeonato) {
        return ResponseEntity.ok(relatorioService.gerarHistoricoRecursos(idCampeonato));
    }

    @GetMapping("/inscritos/modalidade/{idModalidade}/download")
    @Operation(
            summary = "Download Relatório de Inscritos",
            description = "Gera e baixa uma planilha Excel (.xlsx) com a lista de todas as equipes confirmadas na modalidade."
    )
    public ResponseEntity<byte[]> baixarExcelInscritos(@PathVariable Long idModalidade) {
        try {
            byte[] arquivoExcel = relatorioService.gerarExcelInscritos(idModalidade);

            return ResponseEntity.ok()

                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio_inscritos_" + idModalidade + ".xlsx")

                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(arquivoExcel);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}