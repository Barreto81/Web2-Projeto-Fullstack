# Backend — Spring Boot (Programação Web II)

API REST construída com **Spring Boot 3.5** + **Java 17** + **Spring Data JPA** + **H2**,
organizada na **arquitetura em camadas** da Aula 06.

> Guia completo do projeto (frontend incluído): veja o [`README.md`](../README.md)

---

## ▶ Como rodar

> Funciona em **Windows**, **Linux** e **macOS**. O Maven Wrapper já vem
> incluso — não é preciso instalar Maven.

### Windows (cmd ou PowerShell)

```powershell
# Opção 1 — usar o jar já pronto (só precisa de Java 17+)
cd C:\caminho\do\projeto\Web2-Projeto\backend
java -jar target\braully-web2-fullstack-1.0.0.jar

# Opção 2 — rodar em modo desenvolvimento (usa o Maven Wrapper do Windows)
mvnw.cmd spring-boot:run

# Opção 3 — compilar e gerar novo jar
mvnw.cmd package -DskipTests

# Rodar os testes automatizados (15 testes)
mvnw.cmd test
```

> Na primeira execução de `mvnw.cmd`, o wrapper baixa o Maven automaticamente
> (é preciso internet). Se aparecer erro de execução de script no PowerShell:
> `Set-ExecutionPolicy -Scope CurrentUser RemoteSigned` — ou use a Opção 1,
> que não usa PowerShell.

### Linux / macOS

```bash
# Opção 1 — usar o jar já pronto (só precisa de Java 17+)
java -jar target/braully-web2-fullstack-1.0.0.jar

# Opção 2 — rodar em modo desenvolvimento
./mvnw spring-boot:run

# Opção 3 — compilar e gerar novo jar
./mvnw package -DskipTests

# Rodar os testes automatizados (15 testes)
./mvnw test

# Se necessário, libere a permissão de execução do wrapper
chmod +x mvnw
```

API: **http://localhost:8080/api** · Console H2: **http://localhost:8080/h2-console**

### Arquivos do Maven Wrapper

| Arquivo | Sistema | Fim de linha |
|---------|---------|--------------|
| `mvnw` | Linux / macOS | LF |
| `mvnw.cmd` | Windows | CRLF |
| `.mvn/wrapper/maven-wrapper.properties` | todos | configura a versão do Maven (3.9.16) |

O `.gitattributes` incluso no projeto garante que o Git **não** altere os fins
de linha desses arquivos ao fazer commit/push no GitHub.


---

## 🗂️ Guia das classes (comentadas em português no código)

### Camada de configuração — `config/`

| Classe | Anotação | Responsabilidade |
|--------|----------|------------------|
| `CorsConfig` | `@Configuration` + `WebMvcConfigurer` | Libera o front-end (porta 5173) a chamar a API. Configuração **global**, citada na Aula 04 como alternativa ao `@CrossOrigin` em cada controller. |

### Camada HTTP — `controller/`

| Classe | Anotação | Endpoints |
|--------|----------|-----------|
| `ApiController` | `@RestController @RequestMapping("/api")` | `GET /api` — banner com informações e lista de endpoints |
| `UsuarioController` | `@RestController @RequestMapping("/api/usuarios")` | `GET`, `GET/{id}`, `POST`, `PUT/{id}`, `DELETE/{id}` |
| `PermissaoController` | `@RestController @RequestMapping("/api/permissoes")` | CRUD completo (exercício das aulas) |
| `ProdutoController` | `@RestController @RequestMapping("/api/produtos")` | CRUD completo (entidade de interesse) |

> Os controllers **não** acessam o banco — apenas traduzem HTTP em chamadas ao Service.

### Camada de regra de negócio — `service/`

| Classe | Anotação | Responsabilidade |
|--------|----------|------------------|
| `UsuarioService` | `@Service @Transactional` | CRUD de usuários + regras: **e-mail único**, **login único** (Aula 06) |
| `PermissaoService` | `@Service @Transactional` | CRUD de permissões + regra: **nome único** |
| `ProdutoService` | `@Service @Transactional` | CRUD de produtos + regra: **nome único** |

### Camada de persistência — `repository/`

| Interface | Estende | Métodos customizados |
|-----------|---------|---------------------|
| `UsuarioRepository` | `JpaRepository<Usuario, Long>` | `existsByEmailIgnoreCase`, `existsByUsernameIgnoreCase`, `existsByEmailIgnoreCaseAndIdNot`, ... |
| `PermissaoRepository` | `JpaRepository<Permissao, Long>` | `existsByNomeIgnoreCase`, `existsByNomeIgnoreCaseAndIdNot` |
| `ProdutoRepository` | `JpaRepository<Produto, Long>` | `existsByNomeIgnoreCase`, `existsByNomeIgnoreCaseAndIdNot` |

> O Spring Data JPA **gera a implementação automaticamente** — o CRUD básico
> (`save`, `findAll`, `findById`, `deleteById`) vem pronto.

### Camada de modelo (entidades JPA) — `model/`

| Classe | Tabela | Destaques |
|--------|--------|-----------|
| `Usuario` | `usuario` | `@Id @GeneratedValue(IDENTITY)`; senha com `@JsonProperty(WRITE_ONLY)` (nunca exposta) |
| `Permissao` | `permissao` | id, nome (único), descricao |
| `Produto` | `produto` | preco com `BigDecimal` (correto para dinheiro); quantidade; categoria |

### Camada de DTOs — `dto/`

| Classe | Uso |
|--------|-----|
| `UsuarioDTO` | Resposta da API — **não contém a senha** |
| `UsuarioRequest` | Entrada do `POST` — inclui senha (obrigatória, min. 6) |
| `UsuarioAtualizacaoRequest` | Entrada do `PUT` — **sem senha** (Aula 05) |
| `PermissaoDTO` / `PermissaoRequest` | Resposta / entrada de permissões |
| `ProdutoDTO` / `ProdutoRequest` | Resposta / entrada de produtos |
| `ErroResposta` | Corpo padrão de erro: `{status, mensagem, dataHora, campos?}` |
| `CampoInvalido` | Detalhe campo a campo em erros de validação |

### Camada de exceções — `exception/`

| Classe | Converte para HTTP |
|--------|--------------------|
| `RecursoNaoEncontradoException` | `404 Not Found` |
| `RegraDeNegocioException` | `400 Bad Request` |
| `GlobalExceptionHandler` (`@RestControllerAdvice`) | Trata validação (`@Valid`), corpo JSON inválido, parâmetro com tipo errado e exceções inesperadas (`500`) |

### Outras

| Classe | Responsabilidade |
|--------|------------------|
| `BraullyWeb2FullstackApplication` | Classe principal — `@SpringBootApplication` inicia o Spring e o Tomcat |
| `DataSeeder` (`@Component` + `CommandLineRunner`) | Cadastra dados de exemplo na **primeira** execução (idempotente) |

---

## ⚙️ Configuração principal (`application.properties`)

| Propriedade | Valor | Motivo |
|-------------|-------|--------|
| `server.port` | `8080` | Porta padrão da API |
| `spring.datasource.url` | `jdbc:h2:file:./database.db` | Banco **em arquivo** — dados sobrevivem ao reinício |
| `spring.jpa.hibernate.ddl-auto` | `update` | Hibernate cria/atualiza as tabelas a partir das entidades (Aula 03) |
| `spring.h2.console.enabled` | `true` | Console H2 em `/h2-console` |
| `spring.jpa.open-in-view` | `false` | Boa prática: evita sessão aberta durante a renderização |
| `spring.jpa.show-sql` | `true` | Exibe as SQL geradas pelo JPA (útil para estudar) |

---

## 🧪 Testes

```bash
./mvnw test
```

| Classe de teste | O que cobre |
|-----------------|-------------|
| `UsuarioServiceTest` | Criação de usuário; bloqueio de e-mail/login duplicado; 404 em id inexistente; ausência da senha no DTO |
| `ApiEndpointsTest` | `GET /api`, `GET /api/{entidades}`, `POST` com 201, validação com 400, 404 em id inexistente, tipo inválido, preço negativo |

Os testes usam **H2 em memória** (`src/test/resources/application.properties`) —
rápidos e independentes do banco `database.db` de desenvolvimento.

---

## 🔌 Testando a API (curl)

```bash
# Banner
curl http://localhost:8080/api

# Listar usuários
curl http://localhost:8080/api/usuarios

# Criar usuário (201 Created)
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Ana Souza","username":"ana.souza","senha":"senha123","email":"ana@web2.com"}'

# E-mail duplicado → 400 com mensagem explicativa
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Outro","username":"outra.conta","senha":"senha123","email":"ana@web2.com"}'

# Validação de campo → 400 com lista de campos
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{"nome":"X","preco":-5,"quantidade":1}'

# Id inexistente → 404
curl http://localhost:8080/api/usuarios/999999
```
