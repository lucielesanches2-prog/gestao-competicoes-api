package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

    private final AtletaService atletaService;

    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @PostMapping("/{idEquipe}/criar-atleta")
    @Operation(summary = "Criar Atleta", description = "Perfil: Representante da Equipe/ Cria o atleta vinculado à equipe e retorna o ID.")
    public ResponseEntity<AtletaModel> criarAtleta(
            @PathVariable Long idEquipe,
            @RequestBody AtletaModel atleta) {

        AtletaModel novoAtleta = atletaService.criarAtleta(idEquipe, atleta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtleta);
    }

    @PostMapping(value = "/{idAtleta}/enviar-foto-atleta", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload da Foto do atleta", description = "Perfil: Representante da Equipe/ Envia a foto do Foto do atleta.")
    public ResponseEntity<String> uploadFoto(
            @PathVariable Long idAtleta,
            @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            atletaService.salvarFoto(idAtleta, arquivo);
            return ResponseEntity.ok("Foto salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/{idAtleta}")
    @Operation(summary = "Atualizar atleta", description = "Perfil: Representante da Equipe/Atualizar atleta com id do atleta.")
    public ResponseEntity<AtletaModel> atualizarAtleta(@PathVariable Long idAtleta, @RequestBody AtletaRequestDTO atletaRequestDTO) {

        return ResponseEntity.ok(atletaService.atualizarAtleta(idAtleta,atletaRequestDTO));
    }

    @GetMapping("/buscar-atleta/{idAtleta}")
    @Operation(summary = "Buscar atleta", description = "Perfil: Representante da Equipe/Buscar atleta com id do atleta.")
    public ResponseEntity<AtletaModel> buscarAtleta(@PathVariable Long idAtleta) {
        return ResponseEntity.ok(atletaService.buscarAtleta(idAtleta));
    }

    @GetMapping("/listar-todos-atletas")
    @Operation(summary = "Listar todos atletas", description = "Perfil: Representante da Equipe/Listar todos atletas.")
    public ResponseEntity<List<AtletaModel>> listarAtletas() {
        return ResponseEntity.ok(atletaService.listar());
    }

    @DeleteMapping("/deletar-atleta/{idAtleta}")
    @Operation(summary = "Deletar  atleta", description = "Perfil: Representante da Equipe/Deletar  atleta.")
    public ResponseEntity<Void> deletar(@PathVariable Long idAtleta) {
        atletaService.deletar(idAtleta);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idAtleta}/visualizar-foto-atleta")
    @Operation(summary = "Visualizar foto atleta", description = "Perfil: Representante da Equipe/Visualizar do atleta.")
    public ResponseEntity<byte[]> verFotoAtleta(@PathVariable Long idAtleta) {
        AtletaModel atletaModel = atletaService.buscarAtleta(idAtleta);
        if (atletaModel.getFoto() == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(atletaModel.getFotoTipo()))
                .body(atletaModel.getFoto());
    }
}