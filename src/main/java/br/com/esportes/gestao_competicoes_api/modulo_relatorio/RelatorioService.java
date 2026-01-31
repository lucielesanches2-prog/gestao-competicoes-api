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

    public List<InscricaoModel> gerarRelatorioInscritos(Long idModalidade) {
        return inscricaoRepository.findByModalidadeId(idModalidade);
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
        // 1. Buscar os dados do banco
        List<InscricaoModel> inscricoes = inscricaoRepository.findByModalidadeId(idModalidade);

        // 2. Criar o arquivo Excel em memória
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Equipes Inscritas");

            // --- ESTILO DO CABEÇALHO (Negrito) ---
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // --- CRIAR LINHA DE CABEÇALHO ---
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"ID Inscrição", "Equipe", "Responsável", "Email", "Telefone", "Data", "Status"};

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // --- PREENCHER OS DADOS (LINHA POR LINHA) ---
            int rowIdx = 1;
            for (InscricaoModel inscricao : inscricoes) {
                Row row = sheet.createRow(rowIdx++);

                // Célula 0: ID
                row.createCell(0).setCellValue(inscricao.getId());

                // Célula 1: Nome da Equipe
                row.createCell(1).setCellValue(inscricao.getEquipe().getNome());

                // Célula 2: Responsável
                row.createCell(2).setCellValue(inscricao.getEquipe().getNomeResponsavel());

                // Célula 3: Email (Acesso Direto agora)
                String email = inscricao.getEquipe().getEmail();
                row.createCell(3).setCellValue(email != null ? email : "N/A");

                // Célula 4: Telefone (Acesso Direto agora)
                String tel = inscricao.getEquipe().getTelefone();
                row.createCell(4).setCellValue(tel != null ? tel : "N/A");

                // Célula 5: Data
                row.createCell(5).setCellValue(inscricao.getDataInscricao().toString());

                // Célula 6: Status
                row.createCell(6).setCellValue(inscricao.getStatus());
            }

            // --- AJUSTE AUTOMÁTICO DAS COLUNAS ---
            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 3. Escrever o workbook para um array de bytes
            workbook.write(out);
            return out.toByteArray();
        }
    }
}