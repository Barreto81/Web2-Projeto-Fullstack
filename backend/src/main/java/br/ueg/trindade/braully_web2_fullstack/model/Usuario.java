package br.ueg.trindade.braully_web2_fullstack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ENTIDADE USUARIO
 *
 * <p>Esta classe representa uma <b>tabela no banco de dados</b>. Ela foi
 * criada na Aula 02 (como POJO simples) e <b>persistida na Aula 03</b> ao
 * receber as anotações do JPA.</p>
 *
 * <p><b>Anotações do JPA usadas (Aula 03):</b></p>
 * <ul>
 *   <li>{@code @Entity} — marca a classe como entidade JPA (tabela);</li>
 *   <li>{@code @Table} — dá o nome exato da tabela no banco;</li>
 *   <li>{@code @Id} — marca o atributo como CHAVE PRIMÁRIA;</li>
 *   <li>{@code @GeneratedValue} — define a estratégia de geração automática
 *       do id (IDENTITY = o próprio banco de dados cria o número);</li>
 *   <li>{@code @Column} — detalhes da coluna (tamanho, obrigatoriedade,
 *       valor único...).</li>
 * </ul>
 *
 * <p><b>Restrições impostas pelo Hibernate (Aula 03):</b></p>
 * <ul>
 *   <li>Possui construtor padrão (sem argumentos) — obrigatório;</li>
 *   <li>Possui getters e setters para todos os atributos mapeados;</li>
 *   <li>Segue o padrão CamelCase;</li>
 *   <li>É um POJO — sem lógica de negócio complexa dentro dela.</li>
 * </ul>
 *
 * <p><b>Segurança da senha:</b> o campo {@code senha} nunca pode aparecer
 * em nenhuma resposta JSON. Na Aula 02 foi ensinado o {@code @JsonIgnore};
 * aqui usamos a versão mais completa: {@code @JsonProperty(access = WRITE_ONLY)},
 * que permite a senha chegar na requisição (POST) mas a OMITE em toda
 * resposta (GET/PUT/DELETE).</p>
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /**
     * Chave primária da tabela.
     * <p>IDENTITY = o H2/MySQL gera o valor automaticamente a cada insert.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo do usuário. Mínimo 2 caracteres (validado no Service). */
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    /** Nome de usuário para login. Único no sistema (regra de negócio). */
    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    /**
     * Senha de acesso.
     * <p>{@code WRITE_ONLY} → o Jackson aceita a senha na entrada (JSON do
     * POST) mas NUNCA inclui a senha em nenhuma resposta da API.</p>
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha", nullable = false, length = 100)
    private String senha;

    /** E-mail do usuário. Único no sistema (regra de negócio da Aula 06). */
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    /**
     * Data/hora de criação do registro.
     * <p>{@code @Column(insertable = true, updatable = false)} garante que a
     * data só é gravada no INSERT e nunca é alterada depois.</p>
     */
    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    /**
     * Construtor padrão sem argumentos — <b>obrigatório</b> para o Hibernate.
     * Sem ele, o JPA não consegue instanciar a entidade ao ler do banco.
     */
    public Usuario() {
    }

    /**
     * Construtor completo — usado para criar usuários de exemplo/sementes
     * e nos testes.
     *
     * @param nome     nome completo
     * @param username nome de usuário para login
     * @param senha    senha de acesso (em texto puro por enquanto)
     * @param email    e-mail de contato
     */
    public Usuario(String nome, String username, String senha, String email) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.email = email;
    }

    /* ============================ GETTERS ============================ */

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    /**
     * @return a senha em texto puro (usado apenas internamente pelo Service)
     */
    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    /* ============================ SETTERS ============================ */

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Método de conveniência chamado pelo Service antes de salvar um usuário
     * novo, para registrar automaticamente a data de cadastro.
     */
    public void registrarDataCadastro() {
        this.dataCadastro = LocalDateTime.now();
    }

    /**
     * Garante que a data de cadastro exista antes de gravar no banco
     * (chamado pelo DataSeeder quando o registro é criado manualmente).
     */
    public void prepararParaPersistencia() {
        if (this.dataCadastro == null) {
            this.dataCadastro = LocalDateTime.now();
        }
    }
}
