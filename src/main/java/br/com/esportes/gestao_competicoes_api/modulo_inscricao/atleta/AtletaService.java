package br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta;


import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe.EquipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AtletaService {

    private final AtletaRepository atletaRepository;
    private final EquipeRepository equipeRepository;

    public AtletaService(AtletaRepository atletaRepository, EquipeRepository equipeRepository) {
        this.atletaRepository = atletaRepository;
        this.equipeRepository = equipeRepository;
    }

    public AtletaModel criarAtleta(Long idEquipe, AtletaModel atleta) {
        EquipeModel equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada com ID: " + idEquipe));

        atleta.setEquipe(equipe);

        return atletaRepository.save(atleta);
    }

    public AtletaModel atualizarAtleta(Long idAtleta, AtletaRequestDTO atletaRequestDTO) {
        AtletaModel atletaModel=buscarAtleta(idAtleta);
        atletaModel.setNome(atletaRequestDTO.nome());
        atletaModel.setCpfDocumento(atletaRequestDTO.cpfDocumento());
        atletaModel.setRg(atletaRequestDTO.rg());
        atletaModel.setOrgaoEmissor(atletaRequestDTO.orgaoEmissor());

        return atletaRepository.save(atletaModel);

    }

    public AtletaModel buscarAtleta(Long id) {
        return atletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atleta não encontrado"));
    }

    public void deletar(Long id) {
        if (!atletaRepository.existsById(id)) {
            throw new RuntimeException("Atleta não existe");
        }
        atletaRepository.deleteById(id);
    }

    public List<AtletaModel> listar() {
        return atletaRepository.findAll();
    }

    public void salvarFoto(Long id, MultipartFile arquivo) throws IOException {
        AtletaModel atleta = buscarAtleta(id);
        atleta.setFoto(arquivo.getBytes());
        atleta.setFotoTipo(arquivo.getContentType());
        atletaRepository.save(atleta);
    }

}