package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta.AtletaModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EquipeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nome oficial da equipe", example = "Tabajara Futebol Clube")
    private String nome;

    @Column(name = "nome_responsavel")
    @Schema(description = "Nome do responsável/técnico", example = "Seu Creysson")
    private String nomeResponsavel;

    @Email(message = "O formato do email é inválido")
    @Column(name = "email_responsavel")
    @Schema(description = "E-mail oficial de contato", example = "diretoria@tabajarafutebol.com.br")
    private String email;

    @Column(name = "telefone_responsavel")
    @Schema(description = "Celular ou WhatsApp", example = "(11) 99876-5432")
    private String telefone;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "doc_conteudo", length = 10000000)
    @JsonIgnore
    private byte[] documentacao;

    @Column(name = "doc_tipo")
    @JsonIgnore
    private String documentacaoTipo;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<InscricaoModel> historicoParticipacoes = new ArrayList<>();

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<AtletaModel> atletas = new ArrayList<>();
}