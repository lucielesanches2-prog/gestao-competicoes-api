package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModalidadeRepository extends CrudRepository<ModalidadeModel, Long> {
}
