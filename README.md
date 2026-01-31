# 🏆 Sistema de Gestão de Competições Esportivas

API REST desenvolvida para gerenciamento de campeonatos, inscrições de equipes, sorteios automatizados de grupos e interposição de recursos administrativos.

## 🚀 Sobre o Projeto

Este projeto backend foi desenvolvido para atender às demandas de organizações esportivas, substituindo processos manuais por um sistema auditável e centralizado.

### Principais Módulos:
* **Gestão de Competições:** Cadastro de campeonatos, datas e regulamentos.
* **Inscrições:** Registro de equipes e atletas (com upload de fotos e documentos).
* **Sorteio Automatizado:** Algoritmo para definição de grupos com suporte a "cabeças de chave" e log de auditoria.
* **Recursos:** Fluxo de abertura e análise de recursos (Deferido/Indeferido).

---

## 📚 Documentação da API (Swagger UI)

A documentação interativa dos endpoints está disponível através do Swagger.
Após iniciar a aplicação, acesse:

👉 **[http://localhost:8080/api-gestao-competicoes/swagger-ui/index.html](http://localhost:8080/api-gestao-competicoes/swagger-ui/index.html)**

> **Nota:** Certifique-se de que a aplicação está rodando para acessar o link.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21+**
* **Spring Boot 3.x**
* **Spring Data JPA** (Persistência)
* **Postgresql** 
* **OpenAPI / Swagger** (Documentação)
* **Maven** (Gerenciador de dependências)

---

## ⚙️ Configuração e Instalação

### 1. Pré-requisitos
Certifique-se de ter o Java (JDK 21 ou superior) e o Maven instalados.

### 2. Configuração do Application Properties
Para que a URL do Swagger funcione conforme o link acima e o upload de arquivos opere corretamente, verifique seu arquivo `src/main/resources/application.properties`:


---
# 🐳 Ambiente Docker - Gestão de Competições

Este documento descreve como subir o ambiente completo (Aplicação + Banco de Dados PostgreSQL) utilizando Docker Compose.

## 📋 Pré-requisitos

- **Docker** e **Docker Compose** instalados.
- Portas **8080** (API) e **5432** (Banco) livres na máquina host.

---

## 🚀 Como Executar

### 1. Preparação dos Arquivos

Na raiz do projeto, crie um arquivo chamado `docker-compose.yml` e cole o conteúdo unificado abaixo. Ele junta a aplicação e o banco na mesma rede.

YAML

`version: '3.8'

services:
# Serviço da Aplicação (API)
app:
build:
context: .
dockerfile: Dockerfile
container_name: container-api-gestao-competicoes
ports:
- "8080:8080"
depends_on:
- db
deploy:
resources:
limits:
memory: 512M
cpus: '2.0'
reservations:
memory: 256M
cpus: '1.0'
environment:
# Conexão com o serviço 'db' definido abaixo
SPRING_DATASOURCE_URL: jdbc:postgresql://db-gestao-competicoes:5432/gestao_competicoes_db
SPRING_DATASOURCE_USERNAME: admin_esportes
SPRING_DATASOURCE_PASSWORD: senha_segura_123
SPRING_JPA_HIBERNATE_DDL_AUTO: update
volumes:
# Logs e Arquivos no Host
- ./infra/logs:/app/logs
- ./infra/data:/app/data
networks:
- rede_esportes_dev

# Serviço do Banco de Dados (PostgreSQL)
db:
image: postgres:17.0
container_name: db-gestao-competicoes
restart: always
deploy:
resources:
limits:
memory: 512M
cpus: '0.5'
reservations:
memory: 256M
cpus: '0.25'
environment:
POSTGRES_DB: gestao_competicoes_db
POSTGRES_USER: admin_esportes
POSTGRES_PASSWORD: senha_segura_123
ports:
- "5432:5432"
volumes:
- postgres_data:/var/lib/postgresql/data
networks:
- rede_esportes_dev

volumes:
postgres_data:

networks:
rede_esportes_dev:
driver: bridge`

### 2. Comandos de Execução

No terminal, dentro da pasta onde está o arquivo `docker-compose.yml`, execute:

**Para subir o ambiente:**

Bash

`docker-compose up -d --build`

> O parâmetro --build garante que a imagem da aplicação seja recriada caso você tenha alterado o código Java.
>

**Para verificar os logs:**

Bash

`docker-compose logs -f app`

**Para parar e remover os containers:**

Bash

`docker-compose down`

---

## 📂 Estrutura de Volumes e Persistência

Para garantir que dados e logs não sejam perdidos, configuramos os seguintes volumes:

| **Serviço** | **Caminho no Container** | **Onde fica no seu PC (Host)** | **Descrição** |
| --- | --- | --- | --- |
| **App** | `/app/logs` | `./infra/logs` | Logs de execução do Spring Boot. |
| **App** | `/app/data` | `./infra/data` | Arquivos de upload (fotos, documentos). |
| **DB** | `/var/lib/postgresql/data` | Volume Docker Interno | Dados das tabelas do banco. |

> Nota: Ajustei os caminhos do app para ./infra/... (caminho relativo). Se você usar o caminho absoluto /data/gestao-competicoes/... (como no seu original), precisará criar essas pastas manualmente na raiz do seu sistema operacional (Linux/Mac) ou ajustar para C:/... no Windows.
>

---

## ⚙️ Variáveis de Ambiente e Recursos

### Limites de Hardware (Deploy Resources)

Para evitar que os containers consumam toda a memória do servidor, definimos limites rígidos:

- **API (App):** Mínimo 256MB / Máximo 512MB RAM.
- **Banco (DB):** Mínimo 256MB / Máximo 512MB RAM.

### Credenciais

As credenciais estão configuradas diretamente no `docker-compose.yml` para ambiente de desenvolvimento (`_dev`).

- **User:** `admin_esportes`
- **Pass:** `senha_segura_123`
- **Database:** `gestao_competicoes_db`

---

## ⚠️ Troubleshooting (Problemas Comuns)

**1. Erro de Conexão com o Banco**

Se a aplicação subir antes do banco estar pronto, ela pode falhar.

- *Solução:* O Docker Compose tentará reiniciar, mas você pode usar `docker-compose restart app` após alguns segundos.

**2. Porta em uso**

Se vir erro `Bind for 0.0.0.0:8080 failed: port is already allocated`.

- *Solução:* Pare o processo que está usando a porta 8080 ou mude a porta no docker-compose (ex: `"8081:8080"`).

**3. Permissão de Escrita (Linux/Mac)**

Se a aplicação não conseguir escrever logs ou uploads.

- *Solução:* Dê permissão na pasta infra: `chmod -R 777 ./infra`.

---

### O que eu ajustei no seu código para este README:

1. **Unificação:** Juntei os dois blocos em um só arquivo. No seu original, um bloco definia a rede como `external: true` e o outro criava a rede. No unificado, a rede é criada automaticamente (`driver: bridge`), o que é mais fácil para começar.
2. **Caminhos Relativos:** Mudei os volumes da aplicação de `/data/gestao-competicoes/...` (que exige criar pastas na raiz do sistema operacional) para `./infra/...` (que cria pastas dentro do projeto). Isso evita erros de "pasta não encontrada" no Windows ou Mac.
3. **Link App-Banco:** Adicionei a variável `SPRING_DATASOURCE_URL` apontando para `jdbc:postgresql://db-gestao-competicoes...`. Isso é crucial, senão o Java não acha o Postgres.
---
Estrutura de Pastas Sugerida
````
com.projeto.esportivo
│
├── core (Configurações gerais, Segurança, Utils)
│
├── modulo_competicao       <-- MÓDULO 1: GESTÃO DE COMPETIÇÕES
│   ├── Competicao.java (Entity)
│   ├── Modalidade.java (Entity)
│   ├── CompeticaoRepository.java
│   ├── CompeticaoService.java
│   └── CompeticaoController.java
│
├── modulo_inscricao        <-- MÓDULO 2 e 3: REGISTRO DE EQUIPES E ATLETAS
│   ├── Equipe.java (Entity)
│   ├── Atleta.java (Entity)
│   ├── Inscricao.java (Entity - vínculo com modalidade)
│   ├── EquipeRepository.java
│   ├── EquipeService.java
│   └── EquipeController.java
│
├── modulo_sorteio          <-- MÓDULO 4: SORTEIO DE GRUPOS
│   ├── Grupo.java (Entity)
│   ├── SorteioService.java (A Lógica do Algoritmo fica aqui)
│   └── SorteioController.java
│
├── modulo_recurso          <-- MÓDULO 5: GESTÃO DE RECURSOS
│   ├── Recurso.java (Entity)
│   ├── RecursoRepository.java
│   ├── RecursoService.java
│   └── RecursoController.java
│
└── modulo_relatorio        <-- MÓDULO 6: RELATÓRIOS
    └── RelatorioService.java (Geração de PDFs/Excel)
````

### 1. Módulo: Gestão de Competições

*Responsabilidade: Criar campeonatos, definir datas e regras.*

- **Repository (`CompeticaoRepository`):** Interface que estende `JpaRepository` para salvar e buscar competições no banco.
- **Service (`CompeticaoService`):**
    - Método `criarCompeticao(dados)`: Valida se a data de fim é maior que a de início.
    - Método `adicionarModalidade()`: Garante que não se adicione uma modalidade duplicada.
- **Controller (`CompeticaoController`):**
    - `POST /competicoes`: Recebe o JSON e chama o Service.
    - `GET /competicoes`: Lista os campeonatos ativos.

### 2. Módulo: Registro de Equipes e Atletas

*Responsabilidade: Cadastrar times, jogadores e validar documentos.*

- **Repository:** `EquipeRepository`, `AtletaRepository`.
- **Service (`EquipeService`):**
    - Método `registrarEquipe()`: Salva os dados do responsável.
    - Método `inscreverAtleta()`: Verifica se o atleta já não está em outra equipe (regra de negócio).
    - Método `realizarInscricaoEmModalidade()`: Liga a Equipe à Modalidade (tabela `Inscricao`).
- **Controller (`EquipeController`):**
    - `POST /equipes`: Cria a equipe.
    - `POST /equipes/{id}/atletas`: Adiciona atleta ao time.

### 3. Módulo: Sorteio de Grupos (O Coração do Sistema)

*Responsabilidade: Algoritmo automatizado e auditoria.*

- **Repository:** `GrupoRepository` (para salvar o resultado).
- **Service (`SorteioService`):**
    - **Lógica Pesada:** Aqui vai o algoritmo. Ele busca todas as `Inscricoes` de uma modalidade.
    - Separa quem é `cabecaDeChave`.
    - Distribui o restante aleatoriamente usando `Collections.shuffle()` ou `Random`.
    - Grava o passo a passo numa String ou JSON para o campo `logAuditoriaSorteio`.
- **Controller (`SorteioController`):**
    - `POST /sorteio/executar?modalidadeId=1`: Dispara o sorteio.

### 4. Módulo: Gestão de Recursos

*Responsabilidade: Interposição e análise de recursos.*

- **Repository:** `RecursoRepository`.
- **Service (`RecursoService`):**
    - Método `abrirRecurso()`: Registra a reclamação com status `AGUARDANDO_ANALISE`.
    - Método `avaliarRecurso()`: A comissão admin envia o parecer e muda o status para `DEFERIDO/INDEFERIDO`.
- **Controller (`RecursoController`):**
    - `POST /recursos`: Equipe abre recurso.
    - `PATCH /recursos/{id}/analise`: Comissão responde.

### 5. Módulo: Relatórios

*Responsabilidade: Gerar histórico e listas.*

- **Service (`RelatorioService`):**
    - Não precisa necessariamente de uma Entidade própria. Ele consulta os outros Repositories.
    - Método `gerarFichaInscricao()`: Busca dados da Equipe + Atletas e monta um PDF.
    - Método `gerarResultadoSorteio()`: Busca os Grupos formados e exporta.
- **Controller (`RelatorioController`):**
    - `GET /relatorios/equipes-inscritas`: Baixa o arquivo.