package br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato;

import org.springframework.stereotype.Service;

@Service
public class CampeonatoService {

    private  final CampeonatoRepository campeonatoRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository) {
        this.campeonatoRepository = campeonatoRepository;
    }

    public CampeonatoModel criarCampeonato(CampeonatoModel campeonatoModel) {
        return campeonatoRepository.save(campeonatoModel);
    }

    public CampeonatoModel buscarCompeticaoPorId(Long idCompeticao) {
        return campeonatoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada!"));
    }

    public CampeonatoModel atualizarCampeonato(Long idCompeticao, CampeonatoRequestDTO dto) {
        CampeonatoModel competicaoExistente = campeonatoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Competição não encontrada"));

        // Você altera APENAS o que o DTO permite.
        // A lista 'regulamentos' que está no 'competicaoExistente' nem é tocada.
        competicaoExistente.setNome(dto.nome());
        competicaoExistente.setDataInicio(dto.dataInicio());
        competicaoExistente.setDataFim(dto.dataFim());

        return campeonatoRepository.save(competicaoExistente);
    }

    public void deletarCampeonatoPorId(Long id) {
        CampeonatoModel CampeonatoModel =  campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrada para exclusão."));

        campeonatoRepository.delete(CampeonatoModel);
    }
}
