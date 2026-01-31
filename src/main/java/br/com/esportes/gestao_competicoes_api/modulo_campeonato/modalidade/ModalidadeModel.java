package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento.RegulamentoModalidadeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modalidade")
public class ModalidadeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name="nome")
    @Schema(description = "Nome da modalidade", example =  "Futsal Masculino", type = "string")
    private String nome;

    @OneToMany(mappedBy = "modalidade", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de regras específicas desta modalidade",accessMode = Schema.AccessMode.READ_ONLY)
    private List<RegulamentoModalidadeModel> regulamentoModalidadeModel = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private CampeonatoModel campeonato;

}