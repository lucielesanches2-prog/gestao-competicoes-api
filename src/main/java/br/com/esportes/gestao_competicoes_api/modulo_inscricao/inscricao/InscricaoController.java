package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;
    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

   @PostMapping
    @Operation(summary = "Inscrever equipes", description = "Inscreve uma equipe na modalidade. Pode definir se é cabeça de chave imediatamente.")
    public ResponseEntity<InscricaoModel> inscreverTime(
            @RequestParam Long idEquipe,
            
            @RequestParam Long idModalidade,
            
            @Parameter(description = "Define se a equipe já entra como cabeça de chave (Padrão: false)")
            @RequestParam(required = false, defaultValue = "false") boolean cabecaDeChave) { 

        InscricaoModel inscricao = inscricaoService.realizarInscricao(idEquipe, idModalidade, cabecaDeChave);
        return ResponseEntity.ok(inscricao);
    }

    
}