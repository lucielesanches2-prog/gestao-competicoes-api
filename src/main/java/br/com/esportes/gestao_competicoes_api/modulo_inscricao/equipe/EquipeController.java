package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService = equipeService;
    }

    @PostMapping("/criar-equipe")
    @Operation(summary = "Criar Equipe", description = "Cria a equipe e retorna o ID gerado. Use este ID para enviar a documentação depois.")
    public ResponseEntity<EquipeModel> criarEquipe(@RequestBody EquipeModel equipe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipeService.salvarEquipe(equipe));
    }


    @GetMapping("buscar-equipe/{idEquipe}")
    @Operation(summary = "buscar equipe", description = "Perfil: Organizador (Comissão Técnica)/ Perfil: Representante da Equipe/ Buscar equipe.")
    public ResponseEntity<EquipeModel> buscarPorId(@PathVariable Long idEquipe) {
        return ResponseEntity.ok(equipeService.buscarEquipe(idEquipe));
    }

    @GetMapping("/buscar-todas-equipes")
    @Operation(summary = "Buscar todas as equipes", description = "Perfil: Organizador (Comissão Técnica)/ Buscar todas as equipes.")
    public ResponseEntity<List<EquipeModel>> listarTodasEquipes() {
        return ResponseEntity.ok(equipeService.listarTodasEquipes());
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar equipe", description = "Perfil: Representante da Equipe / Atualizar equipe.")
    public ResponseEntity<EquipeModel> atualizarDados(@PathVariable Long id, @RequestBody EquipeModel equipe) {
        return ResponseEntity.ok(equipeService.atualizarDadosEquipe(id, equipe));
    }

    @DeleteMapping("/{idEquipe}")
    @Operation(summary = "Deletar equipe", description = "Perfil: Representante da Equipe / Deletar equipe.")
    public ResponseEntity<Void> deletar(@PathVariable Long idEquipe) {
        equipeService.deletarEquipe(idEquipe);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{idEquipe}/documentacao", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload do Documento", description = "Perfil: Representante da Equipe/ Envia a foto do documento da equipe.")
    public ResponseEntity<String> uploadDocumentacao(
            @PathVariable Long idEquipe,
            @RequestParam("arquivo") MultipartFile arquivo) {

        try {
            equipeService.atualizarDocumentacao(idEquipe, arquivo);
            return ResponseEntity.ok("Documentação enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar foto: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/visualizar-documentacao")
    @Operation(summary = "Buscar foto do documento da equipe", description = "Perfil: Organizador (Comissão Técnica)/ Perfil: Representante da Equipe/ Buscar foto do documento da equipe.")
    public ResponseEntity<byte[]> visualizarDocumento(@PathVariable Long id) {
        EquipeModel equipe = equipeService.buscarEquipe(id);

        if (equipe.getDocumentacao() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(equipe.getDocumentacaoTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"doc_equipe_" + id + "\"")
                .body(equipe.getDocumentacao());
    }
}