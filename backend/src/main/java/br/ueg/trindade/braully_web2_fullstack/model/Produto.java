package br.ueg.trindade.braully_web2_fullstack.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * ENTIDADE PRODUTO
 *
 * <p>Entidade de <b>interesse do projeto final</b>, sugerida nos exercícios
 * das Aulas 02, 03, 05 e 06 (sugestões do professor: Produto, Pedido,
 * Cliente, Fornecedor, Perfil...). Escolhemos <b>Produto</b> por ser a mais
 * comum em sistemas comerciais e ótima para demonstrar o CRUD completo.</p>
 *
 * <p><b>Destaques técnicos:</b></p>
 * <ul>
 *   <li>{@code BigDecimal} para o preço — <b>nunca use {@code double}</b>
 *       para dinheiro, pois o tipo decimal de ponto flutuante introduz erros
 *       de arredondamento;</li>
 *   <li>{@code @Column(precision, scale)} define exatamente a precisão da
 *       coluna no banco (10 dígitos, 2 decimais);</li>
 *   <li>{@code unique = true} no nome evita produtos duplicados.</li>
 * </ul>
 */
@Entity
@Table(name = "produto")
public class Produto {

    /** Chave primária gerada automaticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome comercial do produto. Único no sistema. */
    @Column(name = "nome", nullable = false, length = 120, unique = true)
    private String nome;

    /**
     * Preço unitário do produto.
     * <p>{@code BigDecimal} com 10 dígitos e 2 casas decimais — padrão para
     * valores monetários. Ex.: {@code 199.90}.</p>
     */
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /** Quantidade disponível em estoque (não pode ser negativa). */
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    /** Categoria do produto (ex.: Eletrônicos, Livros, Roupas). */
    @Column(name = "categoria", length = 80)
    private String categoria;

    /** Descrição longa do produto (opcional). */
    @Column(name = "descricao", length = 500)
    private String descricao;

    /** Construtor padrão — obrigatório para o Hibernate. */
    public Produto() {
    }

    /** Construtor completo para criação de dados de exemplo. */
    public Produto(String nome, BigDecimal preco, Integer quantidade,
                   String categoria, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    /* ============================ GETTERS ============================ */

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getCategoria() {
        return categoria;
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

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
