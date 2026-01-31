package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento.RegulamentoCampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "campeonato")
public class CampeonatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "nome")
    @Schema(description = "Nome oficial do evento", example = "Campeonato Municipal de Verão 2026")
    private String nome;

    @Column(name = "data_inicio")
    @Schema(description = "Data de início", example = "2026-02-15", type = "string")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    @Schema(description = "Data de término", example = "2026-03-30", type = "string")
    private LocalDate dataFim;

    @OneToMany(mappedBy = "campeonato", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de regras do campeonato",accessMode = Schema.AccessMode.READ_ONLY)
    private List<RegulamentoCampeonatoModel> regulamentos = new ArrayList<>();

    @OneToMany(mappedBy = "campeonato", fetch = FetchType.LAZY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<ModalidadeModel> modalidades;

}