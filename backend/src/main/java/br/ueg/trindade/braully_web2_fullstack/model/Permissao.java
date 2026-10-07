package br.ueg.trindade.braully_web2_fullstack.model;

import jakarta.persistence.*;

/**
 * ENTIDADE PERMISSAO
 *
 * <p>Entidade criada como <b>exercício da Aula 02</b> e persistida na
 * Aula 03. Representa um perfil/permissão de acesso do sistema.</p>
 *
 * <p><b>Atributos exigidos pelo exercício:</b></p>
 * <ul>
 *   <li>{@code id} (Long) — chave primária;</li>
 *   <li>{@code nome} (String) — identificador da permissão;</li>
 *   <li>{@code descricao} (String) — texto explicando a permissão.</li>
 * </ul>
 *
 * <p><b>Regra de negócio adicional:</b> o nome da permissão não pode se
 * repetir (validada no {@code PermissaoService}, conforme a Aula 06).</p>
 */
@Entity
@Table(name = "permissao")
public class Permissao {

    /** Chave primária gerada automaticamente pelo banco. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da permissão (ex.: ADMIN, OPERADOR, VENDEDOR).
     * {@code unique = true} força o banco a rejeitar nomes duplicados,
     * e o Service devolve uma mensagem amigável antes mesmo disso.
     */
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;

    /** Descrição legível da permissão. */
    @Column(name = "descricao", length = 255)
    private String descricao;

    /** Construtor padrão — obrigatório para o Hibernate. */
    public Permissao() {
    }

    /** Construtor completo para criação de dados de exemplo. */
    public Permissao(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    /* ============================ GETTERS ============================ */

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    /* ============================ SETTERS ============================ */

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
