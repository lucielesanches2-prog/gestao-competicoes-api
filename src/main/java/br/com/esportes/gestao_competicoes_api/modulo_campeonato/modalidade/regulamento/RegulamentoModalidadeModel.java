package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "regulamento_modalidade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegulamentoModalidadeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Título da regra", example = "Artigo 1 - Tempo de Jogo")
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Schema(description = "Descrição detalhada", example = "A partida terá dois tempos de 20 minutos cronometrados.")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "modalidade_id", nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private ModalidadeModel modalidade;
}