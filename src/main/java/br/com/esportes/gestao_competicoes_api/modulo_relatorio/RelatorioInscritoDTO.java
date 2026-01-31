package br.com.esportes.gestao_competicoes_api.modulo_relatorio;

import java.time.LocalDate;

public record RelatorioInscritoDTO(
        Long idInscricao,
        String nomeEquipe,
        String responsavel,
        LocalDate dataInscricao,
        String status,
        String nomeGrupo
) {}