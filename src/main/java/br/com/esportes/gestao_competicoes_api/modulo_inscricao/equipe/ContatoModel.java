package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;



    @Column(name = "telefone_secundario")
    @Schema(description = "Telefone para recado ou fixo (Opcional)", example = "(11) 3322-1100")
    private String telefoneSecundario;

}