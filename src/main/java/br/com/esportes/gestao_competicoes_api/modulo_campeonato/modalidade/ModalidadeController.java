package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modalidade")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @PostMapping("/{idCampeonato}/criar-modalidade")
    @Operation(summary = "Criar Modalidade", description = "Perfil: Organizador (Comissão Técnica)/Criar modalidade.")
    public ResponseEntity<ModalidadeModel> criarModalidade(
            @PathVariable Long idCampeonato,
            @RequestBody @Valid ModalidadeModel modalidade) {

        ModalidadeModel novaModalidade = modalidadeService.criarModalidade(idCampeonato, modalidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaModalidade);
    }

    @GetMapping("/buscar-modalidade-por-id/{idModalidade}")
    @Operation(summary = "Busca Modalidade", description = "Perfil: Organizador (Comissão Técnica)/Busca modalidade existente pelo id da modalidade.")
    public ResponseEntity<ModalidadeModel> buscarModalidadePorId(@PathVariable Long idModalidade) {
        ModalidadeModel modalidadeModel=modalidadeService.buscarModalidadePorId(idModalidade);
        return ResponseEntity.status(HttpStatus.OK).body(modalidadeModel);
    }

    @PutMapping("/atualizar-modalidade/{idModalidade}")
    @Operation(summary = "Atualiza Modalidade", description = "Perfil: Organizador (Comissão Técnica)/Atualizar modalidade existente pelo id da modalidade.")
    public ResponseEntity<ModalidadeModel> atualizarModalidade(
            @PathVariable Long idModalidade,
            @RequestBody ModalidadeRequestDTO modalidadeRequestDTO) {

        ModalidadeModel modalidadeAtualizada = modalidadeService.atualizarModalidade(idModalidade, modalidadeRequestDTO);
        return ResponseEntity.ok(modalidadeAtualizada);
    }

    @DeleteMapping("/deletar-modalidade/{idModalidedade}")
    @Operation(summary = "Deletar Modalidade", description = "Perfil: Organizador (Comissão Técnica)/Deletar modalidade existente pelo id da modalidade.")
    public ResponseEntity<Void> deletarModalidade(@PathVariable Long idModalidedade) {
        modalidadeService.deletarModalidadePorId(idModalidedade);
        return ResponseEntity.noContent().build();
    }
}
