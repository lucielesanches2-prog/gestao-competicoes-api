package br.com.esportes.gestao_competicoes_api.modulo_recurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<RecursoModel, Long> {
    List<RecursoModel> findByCampeonatoId(Long id);
}