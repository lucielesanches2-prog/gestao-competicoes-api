package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AnaliseRecursoDTO(

        @Schema(description = "Justificativa da decisão da comissão", example = "Recurso procedente. Erro de direito confirmado.")
        @NotNull
        String parecer,

        @Schema(description = "Veredito: true para aceitar o recurso, false para negar", example = "true")
        @NotNull
        Boolean deferido
) {}
