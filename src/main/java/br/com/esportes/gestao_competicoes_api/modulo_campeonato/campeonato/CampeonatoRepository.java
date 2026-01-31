package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampeonatoRepository extends CrudRepository<CampeonatoModel, Long> {
}
