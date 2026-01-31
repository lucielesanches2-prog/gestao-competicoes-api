package br.com.esportes.gestao_competicoes_api.modulo_relatorio;

import br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta.AtletaModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.atleta.AtletaRepository;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoModel;
import br.com.esportes.gestao_competicoes_api.modulo_inscricao.inscricao.InscricaoRepository;
import br.com.esportes.gestao_competicoes_api.modulo_recurso.RecursoModel;
import br.com.esportes.gestao_competicoes_api.modulo_recurso.RecursoRepository;
import br.com.esportes.gestao_competicoes_api.modulo_sorteio.GrupoModel;
import br.com.esportes.gestao_competicoes_api.modulo_sorteio.GrupoRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RelatorioService {

    private final InscricaoRepository inscricaoRepository;
    private final AtletaRepository atletaRepository;
    private final GrupoRepository grupoRepository;
    private final RecursoRepository recursoRepository;

    public RelatorioService(InscricaoRepository inscricaoRepository,
                            AtletaRepository atletaRepository,
                            GrupoRepository grupoRepository,
                            RecursoRepository recursoRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.atletaRepository = atletaRepository;
        this.grupoRepository = grupoRepository;
        this.recursoRepository = recursoRepository;
    }

    public List<RelatorioInscritoDTO> gerarRelatorioInscritos(Long idModalidade) {
        return inscricaoRepository.buscarInscritosParaRelatorio(idModalidade);
    }

    public List<AtletaModel> gerarRelatorioAtletas(Long idEquipe) {
        return atletaRepository.findAll().stream()
                .filter(a -> a.getEquipe().getId().equals(idEquipe))
                .toList();
    }

    public List<GrupoModel> gerarRelatorioSorteio(Long idModalidade) {
        return grupoRepository.findAll().stream()
                .filter(g -> g.getModalidade().getId().equals(idModalidade))
                .toList();
    }

    public List<RecursoModel> gerarHistoricoRecursos(Long idCampeonato) {
        return recursoRepository.findByCampeonatoId(idCampeonato);
    }

    public byte[] gerarExcelInscritos(Long idModalidade) throws IOException {

        // Busca o DTO enxuto
        List<RelatorioInscritoDTO> dados = inscricaoRepository.buscarInscritosParaRelatorio(idModalidade);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Equipes Inscritas");

            // --- Cabeçalho Atualizado (Sem Email/Telefone) ---
            String[] colunas = {"ID", "Equipe", "Responsável", "Data", "Status", "Grupo"};

            Row headerRow = sheet.createRow(0);
            Font font = workbook.createFont();
            font.setBold(true);
            CellStyle style = workbook.createCellStyle();
            style.setFont(font);

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(style);
            }

            // --- Dados ---
            int rowIdx = 1;
            for (RelatorioInscritoDTO item : dados) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.idInscricao());
                row.createCell(1).setCellValue(item.nomeEquipe());
                row.createCell(2).setCellValue(item.responsavel());
                row.createCell(3).setCellValue(item.dataInscricao().toString());
                row.createCell(4).setCellValue(item.status());
                row.createCell(5).setCellValue(item.nomeGrupo() != null ? item.nomeGrupo() : "Aguardando Sorteio");
            }

            // Ajustar largura das colunas
            for(int i=0; i<colunas.length; i++) sheet.autoSizeColumn(i);

            workbook.write(out);
            return out.toByteArray();
        }
    }
}