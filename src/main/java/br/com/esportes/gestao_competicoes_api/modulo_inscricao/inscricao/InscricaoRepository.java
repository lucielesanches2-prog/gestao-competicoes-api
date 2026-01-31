package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<InscricaoModel,Long> {
  List<InscricaoModel> findByModalidadeId(Long modalidadeId);

    // MÉTODO OTIMIZADO PARA RELATÓRIOS
    // O "JOIN FETCH" obriga o banco a trazer os dados da Equipe junto com a Inscrição
  //  @Query("SELECT i FROM InscricaoModel i JOIN FETCH i.equipe WHERE i.modalidade.id = :idModalidade")
 //   List<InscricaoModel> findByModalidadeIdComEquipe(@Param("idModalidade") Long idModalidade);
}
