package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "regulamento_campeonato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegulamentoCampeonatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Artigo 1 - Das Inscrições")
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Schema(example = "Todas as equipes devem ter no mínimo 5 atletas cadastrados até o dia 10.")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private CampeonatoModel campeonato;
}