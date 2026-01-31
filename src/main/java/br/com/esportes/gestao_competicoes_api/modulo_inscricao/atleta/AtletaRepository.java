package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaRepository extends JpaRepository<AtletaModel,Long> {
}
