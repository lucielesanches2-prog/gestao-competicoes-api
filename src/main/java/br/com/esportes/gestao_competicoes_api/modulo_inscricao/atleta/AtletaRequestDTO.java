package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

import io.swagger.v3.oas.annotations.media.Schema;

public record AtletaRequestDTO(

        @Schema(example = "Neymar Jr")
        String nome,

        @Schema(description = "CPF do atleta", example = "123.456.789-00")
        String cpfDocumento,

        @Schema(description = "Registro Geral", example = "12.345.678-9")
        String rg,

        @Schema(description = "Órgão Emissor do RG", example = "SSP/SP")
        String orgaoEmissor
) {
}

