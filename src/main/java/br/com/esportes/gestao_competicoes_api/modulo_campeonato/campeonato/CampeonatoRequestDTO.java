package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Objeto de transferência para criação e atualização de campeonatos")
public record CampeonatoRequestDTO(

        @Schema(description = "Nome oficial do evento", example = "Campeonato Municipal de Verão 2026")
        String nome,

        @Schema(description = "Data de início do campeonato", example = "2026-02-15")
        LocalDate dataInicio,

        @Schema(description = "Data de término do campeonato", example = "2026-03-30")
        LocalDate dataFim
) {}