package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de transferência para criação e atualização de modalidade")
public record ModalidadeRequestDTO(
        @Schema(description = "Nome da modalidade", example =  "Futsal Masculino", type = "string")
        String nome
) {

}
