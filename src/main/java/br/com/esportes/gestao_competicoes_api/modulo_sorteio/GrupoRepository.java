package br.com.esportes.gestao_competicoes_api.modulo_sorteio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository  extends JpaRepository<GrupoModel, Long> {
}
