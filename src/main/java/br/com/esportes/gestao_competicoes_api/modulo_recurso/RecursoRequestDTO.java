package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecursoRequestDTO(
        @Schema(description = "ID da equipe que está solicitando o recurso", example = "15")
        @NotNull(message = "O ID da equipe é obrigatório")
        Long idEquipe,

        @Schema(description = "ID do campeonato onde ocorreu o fato", example = "3")
        @NotNull(message = "O ID do campeonato é obrigatório")
        Long idCampeonato,

        @Schema(description = "Texto detalhado explicando o motivo da contestação", example = "Solicito revisão da súmula pois o atleta camisa 10 do time adversário não estava inscrito.")
        @NotBlank(message = "A descrição do recurso não pode estar vazia")
        String descricao
) {
}