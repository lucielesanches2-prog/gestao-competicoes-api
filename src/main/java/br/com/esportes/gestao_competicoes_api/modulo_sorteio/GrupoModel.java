package br.com.esportes.gestao_competicoes_api.modulo_sorteio;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "modalidade_id", nullable = false)

    @JsonIgnoreProperties({"grupos", "inscricoes", "competicao"})
    private ModalidadeModel modalidade;


    @Column(name = "log_auditoria", columnDefinition = "TEXT")
    private String logAuditoriaSorteio;


    @OneToMany(mappedBy = "grupo", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"grupo", "modalidade", "historicoParticipacoes"})
    private List<InscricaoModel> equipesDoGrupo = new ArrayList<>();
}