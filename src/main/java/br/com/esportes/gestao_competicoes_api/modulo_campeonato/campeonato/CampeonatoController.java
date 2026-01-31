package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campeonato")
public class CampeonatoController {

    private CampeonatoService campeonatoService;

    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @PostMapping("/criar-campeonato")
    @Operation(summary = "Criar novo campeonato", description = "Perfil: Organizador (Comissão Técnica)/Cadastra um novo campeonato com suas datas.")
    public ResponseEntity<CampeonatoModel> criarCampeonato(@Valid @RequestBody CampeonatoModel campeonatoModel){
        CampeonatoModel competicaoSalva = campeonatoService.criarCampeonato(campeonatoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(competicaoSalva);

    }

    @GetMapping("/buscar-campeonato-por-id/{idCampeonato}")
    @Operation(summary = "Buscar campeonato", description = "Perfil: Organizador (Comissão Técnica)/Buscar informações do campeonato pelo id do campeonato.")
    public ResponseEntity<CampeonatoModel> buscarCampeonatoPorId(@PathVariable Long idCampeonato) {
        CampeonatoModel campeonatoModel = campeonatoService.buscarCompeticaoPorId(idCampeonato);
        return ResponseEntity.ok(campeonatoModel);
    }

    @PutMapping("/atualizar-campeonato/{idCampeonato}")
    @Operation(summary = "Atualizar campeonato", description = "Perfil: Organizador (Comissão Técnica)/Atualiza os dados de uma campeonato existente pelo id do campeonato.")
    public ResponseEntity<CampeonatoModel> atualizarCampeonato(
            @PathVariable Long idCampeonato,
            @RequestBody CampeonatoRequestDTO campeonatoRequestDTO) {

        CampeonatoModel competicaoAtualizada = campeonatoService.atualizarCampeonato(idCampeonato, campeonatoRequestDTO);
        return ResponseEntity.ok(competicaoAtualizada);
    }

    @DeleteMapping("/deletar-campeonato/{idCampeonato}")
    @Operation(summary = "Deletar campeonato", description = "Perfil: Organizador (Comissão Técnica)/Deletar campeonato existente pelo id do campeonato (Só deleta se não tiver relacionamento com outras tabelas como modalidade).")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Long idCampeonato) {
        campeonatoService.deletarCampeonatoPorId(idCampeonato);
        return ResponseEntity.noContent().build();
    }
}
