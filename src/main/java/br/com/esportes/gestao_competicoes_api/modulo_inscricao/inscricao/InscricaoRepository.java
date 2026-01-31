package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;

import br.com.esportes.gestao_competicoes_api.modulo_relatorio.RelatorioInscritoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<InscricaoModel,Long> {
  List<InscricaoModel> findByModalidadeId(Long modalidadeId);

  @Query("""
        SELECT new br.com.esportes.gestao_competicoes_api.modulo_relatorio.RelatorioInscritoDTO(
            i.id,
            e.nome,
            e.nomeResponsavel,
            i.dataInscricao,
            i.status,
            g.nome
        )
        FROM InscricaoModel i
        JOIN i.equipe e
        LEFT JOIN i.grupo g
        WHERE i.modalidade.id = :idModalidade
    """)
  List<RelatorioInscritoDTO> buscarInscritosParaRelatorio(@Param("idModalidade") Long idModalidade);
}
