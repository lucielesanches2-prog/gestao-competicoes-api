package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegulamentoCampeonatoRepository extends JpaRepository<RegulamentoCampeonatoModel, Long> {

}
