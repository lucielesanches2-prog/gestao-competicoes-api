package br.com.esportes.gestao_competicoes_api.modulo_inscricao.equipe;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;


    public EquipeService(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    public EquipeModel salvarEquipe(EquipeModel equipe) {
        return equipeRepository.save(equipe);
    }

    public List<EquipeModel> listarTodasEquipes() {
        return equipeRepository.findAll();
    }

    public EquipeModel buscarEquipe(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipe não encontrada com ID: " + id));
    }

    public EquipeModel atualizarDadosEquipe(Long id, EquipeModel novosDados) {
        EquipeModel equipeExistente = buscarEquipe(id);

        equipeExistente.setNome(novosDados.getNome());
        equipeExistente.setNomeResponsavel(novosDados.getNomeResponsavel());
        equipeExistente.setEmail(novosDados.getEmail());
        equipeExistente.setTelefone(novosDados.getTelefone());

        return equipeRepository.save(equipeExistente);
    }

    public void deletarEquipe(Long id) {
        if (!equipeRepository.existsById(id)) {
            throw new RuntimeException("Equipe não encontrada para exclusão");
        }
        equipeRepository.deleteById(id);
    }

    public void atualizarDocumentacao(Long idEquipe, MultipartFile arquivo) throws IOException {
        EquipeModel equipe = buscarEquipe(idEquipe);

        String tipoArquivo = arquivo.getContentType();

        if (tipoArquivo == null || !tipoArquivo.startsWith("image/")) {
            throw new RuntimeException("Formato inválido! Por favor, envie apenas fotos (JPG, PNG).");
        }
        equipe.setDocumentacao(arquivo.getBytes());
        equipe.setDocumentacaoTipo(tipoArquivo);

        equipeRepository.save(equipe);
    }
}