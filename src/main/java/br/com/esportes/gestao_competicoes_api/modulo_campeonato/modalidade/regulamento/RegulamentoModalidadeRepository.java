package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.regulamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulamentoModalidadeRepository extends JpaRepository<RegulamentoModalidadeModel, Long> {
}