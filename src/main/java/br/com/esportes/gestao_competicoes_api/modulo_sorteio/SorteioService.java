package br.com.esportes.gestao_competicoes_api.modulo_sorteio;

import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeModel;
import br.com.esportes.gestao_competicoes_api.modulo_campeonato.modalidade.ModalidadeRepository;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SorteioService {

    private InscricaoRepository inscricaoRepository;


    private GrupoRepository grupoRepository;


    private ModalidadeRepository modalidadeRepository;

    public SorteioService(InscricaoRepository inscricaoRepository,
                         GrupoRepository grupoRepository,
                         ModalidadeRepository modalidadeRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.grupoRepository = grupoRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    @Transactional
    public List<GrupoModel> realizarSorteio(Long modalidadeId, Integer quantidadeGrupos) {


        ModalidadeModel modalidade = modalidadeRepository.findById(modalidadeId)
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada"));


        List<InscricaoModel> inscritos = inscricaoRepository.findByModalidadeId(modalidadeId);

        if (inscritos.isEmpty()) {
            throw new RuntimeException("Não há equipes inscritas para realizar o sorteio.");
        }

        List<GrupoModel> gruposCriados = new ArrayList<>();
        char letraGrupo = 'A';

        for (int i = 0; i < quantidadeGrupos; i++) {
            GrupoModel grupo = new GrupoModel();
            grupo.setNome("Grupo " + letraGrupo);
            grupo.setModalidade(modalidade);
            grupo.setLogAuditoriaSorteio("Sorteio realizado em: " + LocalDateTime.now());

            grupoRepository.save(grupo);
            gruposCriados.add(grupo);
            letraGrupo++;
        }


        List<InscricaoModel> cabecas = new ArrayList<>(inscritos.stream().filter(InscricaoModel::isCabecaDeChave).toList());
        List<InscricaoModel> normais = new ArrayList<>(inscritos.stream().filter(i -> !i.isCabecaDeChave()).toList());

        Collections.shuffle(normais);
        Collections.shuffle(cabecas);

        int indexGrupo = 0;
        for (InscricaoModel cabeca : cabecas) {
            GrupoModel grupoDestino = gruposCriados.get(indexGrupo % quantidadeGrupos);
            cabeca.setGrupo(grupoDestino);

            auditar(grupoDestino, "Cabeça de chave definido: " + cabeca.getEquipe().getNome());

            indexGrupo++;
        }


        for (InscricaoModel timeNormal : normais) {
            GrupoModel grupoDestino = gruposCriados.get(indexGrupo % quantidadeGrupos);
            timeNormal.setGrupo(grupoDestino);

            auditar(grupoDestino, "Sorteado: " + timeNormal.getEquipe().getNome());

            indexGrupo++;
        }


        inscricaoRepository.saveAll(inscritos);
        grupoRepository.saveAll(gruposCriados);

        return gruposCriados;
    }

    private void auditar(GrupoModel grupo, String mensagem) {
        String logAtual = grupo.getLogAuditoriaSorteio() == null ? "" : grupo.getLogAuditoriaSorteio();
        grupo.setLogAuditoriaSorteio(logAtual + "\n" + mensagem);
    }
}