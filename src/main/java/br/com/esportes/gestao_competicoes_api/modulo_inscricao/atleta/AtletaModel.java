package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "atleta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtletaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "nome")
    @Schema(example = "Neymar Jr")
    private String nome;

    @Column(name = "cpf")
    @Schema(description = "CPF do atleta", example = "123.456.789-00")
    private String cpfDocumento;

    @Column(name = "rg")
    @Schema(description = "Registro Geral", example = "12.345.678-9")
    private String rg;

    @Column(name = "orgao_emissor")
    @Schema(description = "Órgão Emissor do RG", example = "SSP/SP")
    private String orgaoEmissor;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "foto_dados", length = 10000000)
    @JsonIgnore
    private byte[] foto;

    @Column(name = "foto_tipo")
    @JsonIgnore
    private String fotoTipo;

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    @JsonIgnoreProperties({"equipe","atletas", "historicoParticipacoes", "documentacao", "contato"})
    private EquipeModel equipe;
}