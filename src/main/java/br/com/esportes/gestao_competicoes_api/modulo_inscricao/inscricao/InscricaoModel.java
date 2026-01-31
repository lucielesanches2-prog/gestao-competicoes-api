package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import br.com.esportes.gestao_competicoes_api.modulo_sorteio.GrupoModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inscricao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(name = "data_inscricao")
    private LocalDate dataInscricao = LocalDate.now();

    private String status = "PENDENTE";

 
    @Column(name = "cabeca_de_chave")
    private boolean cabecaDeChave = false;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    @JsonIgnoreProperties({"equipe","historicoParticipacoes", "atletas", "documentacao"})
    private EquipeModel equipe;

    @ManyToOne
    @JoinColumn(name = "modalidade_id", nullable = false)
   @JsonIgnoreProperties("inscricoes")
    private ModalidadeModel modalidade;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    @JsonIgnoreProperties("equipasDoGrupo")
    private GrupoModel grupo;
}