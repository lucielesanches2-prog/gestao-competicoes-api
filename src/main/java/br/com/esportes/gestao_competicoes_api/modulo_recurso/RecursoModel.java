package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "recurso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Motivo detalhado do recurso", example = "Solicito revisão do resultado do jogo devido à escalação irregular do time adversário.")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusRecursoEnum status = StatusRecursoEnum.PENDENTE;

    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Column(name = "data_analise")
    private LocalDateTime dataAnalise;

    @Column(name = "parecer_comissao", columnDefinition = "TEXT")
    @Schema(description = "Resposta oficial da comissão (preenchido na análise)", example = "Recurso aceito. O time adversário perdeu os pontos.")
    private String parecerComissao;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    @JsonIgnoreProperties({"atletas", "historicoParticipacoes", "documentacao", "contato"})
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private EquipeModel equipeSolicitante;

    @ManyToOne
    @JoinColumn(name = "campeonato_id", nullable = false)
    @JsonIgnoreProperties({"modalidades", "regulamento", "tabelaJogos"})
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private CampeonatoModel campeonato;
}