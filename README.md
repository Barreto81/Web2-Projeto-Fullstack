# Programação Web II — Projeto Completo (Spring Boot + React)

Projeto completo da disciplina **Programação Web II**, construído a partir das aulas
**02 (APIs REST)**, **03 (Spring Data JPA)**, **04 (React)**, **05 (CRUD Completo)** e
**06 (Arquitetura em Camadas)**.

Inclui **backend em Spring Boot** (Java 17) e **frontend em React + TypeScript**, com
CRUD completo de três entidades, banco H2, validação de dados, tratamento global de
erros, regras de negócio e **todas as classes comentadas em português**.

---

## 📁 Estrutura do projeto

```
Web2-Projeto/
│
├── backend/                        ← API Spring Boot (Java)
│   ├── pom.xml                     ← dependências e configuração do Maven
│   ├── mvnw / mvnw.cmd             ← Maven Wrapper (roda sem instalar Maven)
│   ├── .mvn/wrapper/               ← configuração do wrapper
│   └── src/
│       ├── main/java/br/ueg/trindade/braully_web2_fullstack/
│       │   ├── BraullyWeb2FullstackApplication.java   ← classe principal (@SpringBootApplication)
│       │   ├── DataSeeder.java                        ← carga inicial de dados de exemplo
│       │   ├── config/CorsConfig.java                 ← CORS global (Aula 04)
│       │   ├── controller/                            ← camada HTTP (Aula 06)
│       │   │   ├── ApiController.java                 ← banner da API em GET /api
│       │   │   ├── UsuarioController.java             ← CRUD /api/usuarios
│       │   │   ├── PermissaoController.java           ← CRUD /api/permissoes
│       │   │   └── ProdutoController.java             ← CRUD /api/produtos
│       │   ├── service/                               ← regras de negócio (Aula 06)
│       │   │   ├── UsuarioService.java
│       │   │   ├── PermissaoService.java
│       │   │   └── ProdutoService.java
│       │   ├── repository/                            ← acesso ao banco (Aula 03)
│       │   │   ├── UsuarioRepository.java
│       │   │   ├── PermissaoRepository.java
│       │   │   └── ProdutoRepository.java
│       │   ├── model/                                 ← entidades JPA (Aula 03)
│       │   │   ├── Usuario.java
│       │   │   ├── Permissao.java
│       │   │   └── Produto.java
│       │   ├── dto/                                   ← objetos de transferência (Aula 02)
│       │   │   ├── UsuarioDTO.java / UsuarioRequest.java / UsuarioAtualizacaoRequest.java
│       │   │   ├── PermissaoDTO.java / PermissaoRequest.java
│       │   │   ├── ProdutoDTO.java / ProdutoRequest.java
│       │   │   ├── ErroResposta.java                  ← formato padrão de erro
│       │   │   └── CampoInvalido.java                 ← detalhe de campo inválido
│       │   └── exception/                             ← exceções + tratamento global
│       │       ├── RecursoNaoEncontradoException.java
│       │       ├── RegraDeNegocioException.java
│       │       └── GlobalExceptionHandler.java
│       ├── main/resources/application.properties      ← porta, banco H2, console, JPA
│       └── test/java/...                              ← testes automatizados (15 testes)
│
├── frontend/                       ← SPA React + TypeScript (Vite)
│   ├── package.json                ← dependências (axios, react, react-router-dom)
│   ├── package-lock.json           ← versões travadas (npm ci = instalação reproduzível)
│   └── src/
│       ├── main.tsx                ← entrada + BrowserRouter
│       ├── App.tsx                 ← rotas do sistema
│       ├── index.css               ← estilos completos
│       ├── components/             ← peças reutilizáveis (Aula 06)
│       │   ├── Menu.tsx            ← menu de navegação
│       │   ├── UsuarioForm.tsx / UsuarioItem.tsx
│       │   ├── PermissaoForm.tsx / PermissaoItem.tsx
│       │   ├── ProdutoForm.tsx / ProdutoItem.tsx
│       │   └── MensagemErro.tsx    ← estados de carregamento e erro (Aula 04)
│       ├── pages/                  ← telas do sistema (Aula 06)
│       │   ├── UsuariosPage.tsx
│       │   ├── PermissoesPage.tsx
│       │   └── ProdutosPage.tsx
│       ├── services/api.ts         ← instância única do Axios
│       └── types/                  ← interfaces TypeScript
│
└── README.md                       ← este arquivo
```

---

## ✅ O que o projeto cobre

| Aula | Conteúdo | Onde está no projeto |
|------|----------|----------------------|
| **02** | APIs REST, Jackson, `@JsonIgnore`, DTOs, exercícios (Permissao + entidade de interesse) | `model/`, `dto/`, `controller/` |
| **03** | JPA (`@Entity`, `@Id`, `@GeneratedValue`), Repository, banco H2, console H2 | `model/`, `repository/`, `application.properties` |
| **04** | React (componentes, props, state, hooks), Axios, CORS, loading/error | `frontend/`, `config/CorsConfig.java` |
| **05** | CRUD completo (`POST`, `GET`, `PUT`, `DELETE`), formulários controlados | controllers + services + `*Form.tsx` |
| **06** | Arquitetura em camadas (Controller → Service → Repository), regra de negócio | organização em `controller/`, `service/`, `repository/` |

### Entidades implementadas

1. **Usuario** — nome, username, senha (protegida), email, dataCadastro
   *(entidade principal das aulas)*
2. **Permissao** — nome, descricao
   *(exercício obrigatório das Aulas 02, 03, 05 e 06)*
3. **Produto** — nome, preco (BigDecimal), quantidade, categoria, descricao
   *(entidade de interesse sugerida nos exercícios)*

### Regras de negócio (Aula 06)

- E-mail de usuário **não pode se repetir** (criação e edição)
- Username de usuário **não pode se repetir** (criação e edição)
- Nome de permissão **não pode se repetir**
- Nome de produto **não pode se repetir**
- Preço de produto deve ser **positivo**; quantidade **não negativa**
- Senha **nunca** aparece em nenhuma resposta da API

---

## 🚀 Como rodar o projeto

### Requisitos

| Item | Versão | Observação |
|------|--------|------------|
| **Java** | 17 ou superior (17, 21 e 25 funcionam) | Único requisito **obrigatório** para o backend |
| **Node.js** | 18 ou superior | Necessário apenas para o frontend |
| Maven | **NÃO necessário** | O projeto traz o **Maven Wrapper** (`mvnw` / `mvnw.cmd`) e já inclui o `.jar` executável |

> **Windows, Linux e macOS** — o projeto foi preparado para os três sistemas.
> O wrapper `mvnw.cmd` (Windows) e `mvnw` (Linux/Mac) já vêm dentro do `.zip`,
> com os fins de linha corretos (CRLF para `.cmd`, LF para `.sh`).

---

### 🪟 Windows — passo a passo completo

#### 1. Descompacte o `.zip`

Extraia `Web2-Projeto-SpringBoot-React.zip` para uma pasta simples, por exemplo:

```
C:\Web2-Projeto\
```

> Evite pastas com espaços ou acentos (ex.: `C:\Users\Meu Nome\Meu Projeto\`).

#### 2. Backend — Spring Boot

**Opção A — usar o jar já pronto (mais fácil, só precisa de Java)**

Abra o **Prompt de Comando** (cmd) ou **PowerShell** dentro da pasta `backend`:

```
cd C:\Web2-Projeto\backend
java -jar target\braully-web2-fullstack-1.0.0.jar
```

A API sobe em: **http://localhost:8080/api**

> Para abrir a pasta no cmd: clique com o botão direito na pasta `backend` →
> **"Abrir no Terminal"** (Windows 11) ou digite `cmd` na barra de endereço do
> Explorer com o botão direito pressionado → **"Abrir janela do PowerShell aqui"**.

**Opção B — compilar do zero usando o Maven Wrapper do Windows**

```
cd C:\Web2-Projeto\backend
mvnw.cmd spring-boot:run
```

> Na primeira execução, o wrapper baixa o Maven automaticamente (é preciso
> internet). Aguarde a mensagem `BUILD SUCCESS`.
>
> Se aparecer erro de execução de script, rode o PowerShell **como Administrador**
> e execute uma única vez:
> ```
> Set-ExecutionPolicy -Scope CurrentUser RemoteSigned
> ```
> (ou simplesmente use a **Opção A**, que não usa PowerShell.)

**Opção C — gerar um novo jar no Windows**

```
cd C:\Web2-Projeto\backend
mvnw.cmd package -DskipTests
```

O jar será gerado em `target\braully-web2-fullstack-1.0.0.jar`.

**Rodar os testes no Windows:**

```
cd C:\Web2-Projeto\backend
mvnw.cmd test
```

#### 3. Frontend — React

Abra o **Prompt de Comando** (cmd) ou **PowerShell** dentro da pasta `frontend`:

```
cd C:\Web2-Projeto\frontend
npm install
npm run dev
```

O Vite sobre em: **http://localhost:5173**

> **Importante:** o backend precisa estar rodando (Opção A ou B acima) para o
> front-end conseguir buscar os dados.

#### 4. Verifique que tudo está no ar

| URL | O que mostra |
|-----|--------------|
| http://localhost:8080/api | Banner da API com todos os endpoints |
| http://localhost:8080/actuator/health | Saúde da aplicação (`{"status":"UP"}`) |
| http://localhost:8080/h2-console | Console do banco H2 (JDBC URL: `jdbc:h2:file:./database.db`, usuário: `sa`, senha vazia) |
| http://localhost:5173 | Interface React (menu: Usuários, Permissões, Produtos) |

---

### 🐧 Linux / macOS

```bash
# Backend — opção 1: usar o jar já pronto (só precisa de Java)
cd backend
java -jar target/braully-web2-fullstack-1.0.0.jar

# Backend — opção 2: compilar com o Maven Wrapper
cd backend
./mvnw spring-boot:run          # Linux/macOS
# Windows: mvnw.cmd spring-boot:run

# Backend — gerar novo jar
./mvnw package -DskipTests

# Frontend
cd ../frontend
npm install
npm run dev
```

> Se o `mvnw` der erro de permissão, execute:
> ```bash
> chmod +x mvnw
> ```

### 📄 Instruções rápidas por plataforma (resumo)

| Ação | Windows (cmd/PowerShell) | Linux / macOS |
|------|--------------------------|---------------|
| Rodar backend (jar) | `java -jar target\braully-web2-fullstack-1.0.0.jar` | `java -jar target/braully-web2-fullstack-1.0.0.jar` |
| Rodar backend (dev) | `mvnw.cmd spring-boot:run` | `./mvnw spring-boot:run` |
| Compilar jar | `mvnw.cmd package -DskipTests` | `./mvnw package -DskipTests` |
| Rodar testes | `mvnw.cmd test` | `./mvnw test` |
| Frontend | `npm install` e `npm run dev` | `npm install` e `npm run dev` |

---

### ❓ Problemas comuns no Windows

| Problema | Solução |
|----------|---------|
| `'mvnw' não é reconhecido como um comando interno` | Use `mvnw.cmd` (com `.cmd`) no Windows |
| `Acesso negado` ao executar script PowerShell | Execute o PowerShell como Administrador e rode `Set-ExecutionPolicy -Scope CurrentUser RemoteSigned` — **ou** use a Opção A (jar), que não usa PowerShell |
| `Porta 8080 em uso` | Feche a aplicação anterior (feche a janela do cmd) ou mude a porta em `backend\src\main\resources\application.properties` (`server.port=8081`) |
| `Java não é reconhecido` | Instale o JDK 17+ e adicione o `bin` do Java às variáveis de ambiente `Path` |
| `npm não é reconhecido` | Instale o Node.js (versão LTS) e reinstale |
| Front-end sem dados | Confirme se o backend está no ar acessando `http://localhost:8080/api` no navegador |
| Caminho com espaços/acentos | Mova o projeto para uma pasta simples como `C:\Web2-Projeto\` |
| Git converteu os fins de linha | O projeto já inclui `.gitattributes` que protege `mvnw.cmd` (CRLF) e `mvnw` (LF) |

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição | Sucesso |
|--------|----------|-----------|---------|
| `GET` | `/api` | Banner da API | 200 |
| `GET` | `/api/usuarios` | Listar usuários | 200 |
| `GET` | `/api/usuarios/{id}` | Buscar usuário por id | 200 |
| `POST` | `/api/usuarios` | Criar usuário | 201 |
| `PUT` | `/api/usuarios/{id}` | Atualizar usuário | 200 |
| `DELETE` | `/api/usuarios/{id}` | Excluir usuário | 204 |
| `GET` | `/api/permissoes` | Listar permissões | 200 |
| `GET` | `/api/permissoes/{id}` | Buscar permissão por id | 200 |
| `POST` | `/api/permissoes` | Criar permissão | 201 |
| `PUT` | `/api/permissoes/{id}` | Atualizar permissão | 200 |
| `DELETE` | `/api/permissoes/{id}` | Excluir permissão | 204 |
| `GET` | `/api/produtos` | Listar produtos | 200 |
| `GET` | `/api/produtos/{id}` | Buscar produto por id | 200 |
| `POST` | `/api/produtos` | Criar produto | 201 |
| `PUT` | `/api/produtos/{id}` | Atualizar produto | 200 |
| `DELETE` | `/api/produtos/{id}` | Excluir produto | 204 |
| `GET` | `/actuator/health` | Saúde da aplicação | 200 |

### Exemplos com `curl`

```bash
# Listar usuários
curl http://localhost:8080/api/usuarios

# Criar usuário
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Souza","username":"ana.souza","senha":"senha123","email":"ana@web2.com"}'

# Criar produto
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"Cadeira Gamer","preco":899.90,"quantidade":15,"categoria":"Eletronicos"}'

# Atualizar produto (PUT)
curl -X PUT http://localhost:8080/api/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{"nome":"Notebook Gamer Pro","preco":5299.90,"quantidade":8,"categoria":"Eletronicos"}'

# Excluir produto (DELETE → 204)
curl -X DELETE http://localhost:8080/api/produtos/2
```

### Formato padrão de erro

O back-end devolve **sempre** o mesmo formato quando algo falha:

```json
{
  "status": 400,
  "mensagem": "Ja existe um usuario cadastrado com o e-mail joao@web2.com",
  "dataHora": "2026-10-02T19:18:23.809755145"
}
```

Validação de campo:

```json
{
  "status": 400,
  "mensagem": "Erro de validacao nos campos enviados",
  "dataHora": "2026-10-02T19:18:23.856726598",
  "campos": [
    { "campo": "nome", "mensagem": "O nome é obrigatório" }
  ]
}
```

---

## 🧩 Arquitetura em camadas (Aula 06)

```
Requisição HTTP (React · Axios)
        │
        ▼
┌──────────────────────┐
│  Controller           │  @RestController — recebe HTTP, devolve JSON
│  (UsuarioController)  │  Não fala com o banco diretamente!
└──────────┬───────────┘
           │ injeta
           ▼
┌──────────────────────┐
│  Service              │  @Service — regras de negócio e validações
│  (UsuarioService)     │  Quem coordena o fluxo
└──────────┬───────────┘
           │ injeta
           ▼
┌──────────────────────┐
│  Repository           │  extends JpaRepository<Entidade, Long>
│  (UsuarioRepository)  │  Acesso ao banco — SQL gerado automaticamente
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│  Banco H2             │  jdbc:h2:file:./database.db (não precisa instalar)
└──────────────────────┘
```

**Por que separar?** (Aula 06)
- O Controller cuida só de HTTP; o Service concentra as regras;
- A mesma regra pode ser usada por outros controllers ou por testes;
- A manutenção fica mais fácil e o código mais legível.

---

## 🔐 Segurança e qualidade

- **Senha protegida**: `@JsonProperty(access = WRITE_ONLY)` na entidade + DTO de resposta
  **sem** o campo senha — a senha nunca trafega em JSON de resposta
- **Validação declarativa**: `@NotBlank`, `@Email`, `@Size`, `@Positive`, `@PositiveOrZero`
  nos DTOs de entrada — o Spring devolve 400 automaticamente
- **Tratamento global de erros**: `GlobalExceptionHandler` converte exceções em JSON
  padronizado (404, 400, 500) sem vazar pilha de exceção ao cliente
- **CORS global**: `CorsConfig` libera `http://localhost:5173` (Vite) e portas comuns
- **Transações**: `@Transactional` nos Services garante atomicidade
- **Testes automatizados**: 15 testes (JUnit 5 + MockMvc) cobrindo CRUD, validações e regras

---

## 🧪 Dados de exemplo

Na primeira execução, o `DataSeeder` cadastra automaticamente:

| Entidade | Exemplos |
|----------|----------|
| Permissões | ADMIN, OPERADOR, CONSULTA |
| Usuários | admin/admin123, braully/prof2026, maria.santos/maria123, joao.souza/joao123 |
| Produtos | Notebook Gamer, Mouse Gamer RGB, Teclado Mecânico, Livro Clean Code, Fone Bluetooth |

O banco é **em arquivo** (`database.db`, criado na pasta de execução) — os dados
sobrevivem ao reinício. Para recomeçar do zero, **apague o arquivo `database.db`**
(geralmente na pasta `backend/` ou na pasta de onde você rodou o comando).

---

## ❓ Perguntas frequentes

**Preciso instalar Maven?**
Não. Use `./mvnw` (Linux/macOS) ou `mvnw.cmd` (Windows) — ele baixa o Maven sozinho.
Ou use o jar já pronto: `java -jar target/braully-web2-fullstack-1.0.0.jar`.

**Preciso instalar um banco de dados?**
Não. O projeto usa **H2** (em arquivo), que roda 100% em Java, sem instalação.

**A API não responde no React?**
Verifique se o backend está no ar (`http://localhost:8080/api`). O CORS já está
configurado para `http://localhost:5173` via `CorsConfig`.

**Erro de porta em uso?**
Algo já está usando a porta 8080. Feche o processo ou mude a porta em
`backend/src/main/resources/application.properties` (`server.port`).

**Como trocar o banco H2 por PostgreSQL/MySQL?**
Mude o `spring.datasource.*` em `application.properties` e substitua a dependência
H2 pela do banco desejado no `pom.xml`. As entidades e repositories não mudam.

---

## 📚 Referências das aulas

- Spring Boot Documentation — https://spring.io/guides
- Spring Data JPA Documentation — https://docs.spring.io/spring-data/jpa/reference/
- React Documentation — https://react.dev/learn
- Axios Documentation — https://axios-http.com/docs/intro
- Projeto "Building a RESTful Web Service" — https://spring.io/guides/gs/rest-service/

---

## 👨‍🏫 Disciplina

- **Programação Web II** — 6º semestre · 60h
- **Professor:** Braully Rocha da Silva
- **Código Classroom:** `cxuqp2it`
- **Stack:** Spring Boot 3.5 · Java 17 · Spring Data JPA · H2 · React 19 · TypeScript · Vite · Axios · React Router
