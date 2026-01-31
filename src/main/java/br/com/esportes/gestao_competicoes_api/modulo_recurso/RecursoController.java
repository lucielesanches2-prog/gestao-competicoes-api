package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recursos")
public class RecursoController {


    private RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    @Operation(summary = "Abrir Recurso", description = "Time envia uma solicitação de recurso informando os IDs.")
    public ResponseEntity<RecursoModel> abrirRecurso(@RequestBody @Valid RecursoRequestDTO recursoRequestDTO) {

        RecursoModel novoRecurso = recursoService.abrirRecurso(
                recursoRequestDTO.idEquipe(),
                recursoRequestDTO.idCampeonato(),
                recursoRequestDTO.descricao()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(novoRecurso);
    }


    @PutMapping("/{idRecurso}/analise")
    @Operation(summary = "Julgar Recurso", description = "Comissão insere o parecer e define se aceita ou nega.")
    public ResponseEntity<RecursoModel> analisar(
            @PathVariable Long idRecurso,
            @RequestBody AnaliseRecursoDTO dadosAnalise) {

        RecursoModel recursoAtualizado = recursoService.analisarRecurso(
                idRecurso,
                dadosAnalise.parecer(),
                dadosAnalise.deferido()
        );

        return ResponseEntity.ok(recursoAtualizado);
    }

}
