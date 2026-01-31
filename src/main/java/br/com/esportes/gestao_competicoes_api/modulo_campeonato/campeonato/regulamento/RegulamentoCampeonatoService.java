package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.regulamento;


import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulamentoCampeonatoService {

    private final RegulamentoCampeonatoRepository regulamentoRepository;
    private final CampeonatoRepository campeonatoRepository;

    public RegulamentoCampeonatoService(RegulamentoCampeonatoRepository regulamentoRepository,
                                        CampeonatoRepository campeonatoRepository) {
        this.regulamentoRepository = regulamentoRepository;
        this.campeonatoRepository = campeonatoRepository;
    }

    public RegulamentoCampeonatoModel adicionarRegulamentoCampeonato(Long idCampeonato, RegulamentoCampeonatoModel regra) {
        CampeonatoModel campeonato = campeonatoRepository.findById(idCampeonato)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado com ID: " + idCampeonato));

        regra.setCampeonato(campeonato);
        return regulamentoRepository.save(regra);
    }

    public RegulamentoCampeonatoModel buscarRegulamentoCampeonato(Long idRegulamentoCampeonato) {
        return regulamentoRepository.findById(idRegulamentoCampeonato)
                .orElseThrow(() -> new RuntimeException("Regulamento não encontrada!"));
    }

    public RegulamentoCampeonatoModel atualizarRegulamentoCampeonato(Long idRegra, RegulamentoCampeonatoModel novosDados) {

        RegulamentoCampeonatoModel regraExistente = regulamentoRepository.findById(idRegra)
                .orElseThrow(() -> new RuntimeException("Regra não encontrada com ID: " + idRegra));


        regraExistente.setTitulo(novosDados.getTitulo());
        regraExistente.setDescricao(novosDados.getDescricao());


        return regulamentoRepository.save(regraExistente);
    }

    public void deletarRegra(Long idRegra) {
        if (!regulamentoRepository.existsById(idRegra)) {
            throw new RuntimeException("Regra não encontrada para exclusão.");
        }
        regulamentoRepository.deleteById(idRegra);
    }
}