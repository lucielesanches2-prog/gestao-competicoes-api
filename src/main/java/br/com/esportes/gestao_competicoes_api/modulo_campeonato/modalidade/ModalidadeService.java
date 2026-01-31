package br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.campeonato.CampeonatoRepository;
import org.springframework.stereotype.Service;

@Service
public class ModalidadeService {

    private ModalidadeRepository modalidadeRepository;

    private CampeonatoRepository competicaoRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository, CampeonatoRepository competicaoRepository) {
        this.modalidadeRepository = modalidadeRepository;
        this.competicaoRepository = competicaoRepository;
    }

    public ModalidadeModel criarModalidade(Long idCompeticao, ModalidadeModel modalidade) {
        CampeonatoModel campeonato = competicaoRepository.findById(idCompeticao)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado!"));

        modalidade.setCampeonato(campeonato);

        return modalidadeRepository.save(modalidade);
    }

    public ModalidadeModel buscarModalidadePorId(Long id) {
        return modalidadeRepository.findById(id).orElseThrow();
    }

    public ModalidadeModel atualizarModalidade(Long idModalidade, ModalidadeRequestDTO modalidadeRequestDTO) {
        ModalidadeModel modalidadeModel = modalidadeRepository.findById(idModalidade)
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada com ID: " + idModalidade));

        modalidadeModel.setNome(modalidadeRequestDTO.nome());

        return modalidadeRepository.save(modalidadeModel);
    }

    public void deletarModalidadePorId(Long id) {
        ModalidadeModel modalidade = modalidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada para exclusão."));
        modalidadeRepository.delete(modalidade);
    }
}