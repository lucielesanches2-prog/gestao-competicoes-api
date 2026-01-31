package br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao;


import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeRepository;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InscricaoService {

     private InscricaoRepository inscricaoRepository;
     private EquipeRepository equipeRepository;
    private ModalidadeRepository modalidadeRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository,
                           EquipeRepository equipeRepository,
                           ModalidadeRepository modalidadeRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.equipeRepository = equipeRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

// Método atualizado para receber o boolean cabecaDeChave
    public InscricaoModel realizarInscricao(Long idEquipe, Long idModalidade, boolean cabecaDeChave) {

        EquipeModel equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não cadastrada com ID: " + idEquipe));

        ModalidadeModel modalidade = modalidadeRepository.findById(idModalidade)
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada com ID: " + idModalidade));



        InscricaoModel novaInscricao = new InscricaoModel();
        novaInscricao.setEquipe(equipe);
        novaInscricao.setModalidade(modalidade);
        novaInscricao.setDataInscricao(LocalDate.now());
        novaInscricao.setStatus("PENDENTE");
        
        novaInscricao.setCabecaDeChave(cabecaDeChave); 

        return inscricaoRepository.save(novaInscricao);
    }
}