package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Produto;

import java.math.BigDecimal;

/**
 * DTO DE RESPOSTA — PRODUTO
 *
 * <p>Objeto exposto pela API em {@code GET /api/produtos} e
 * {@code GET /api/produtos/{id}}.</p>
 *
 * <p>Observe que o {@code preco} usa {@link BigDecimal} — o mesmo tipo da
 * entidade — para não perder precisao na conversao JSON.</p>
 */
public class ProdutoDTO {

    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private String categoria;
    private String descricao;

    /** Construtor padrao — necessario para o Jackson. */
    public ProdutoDTO() {
    }

    /**
     * Converte a entidade {@link Produto} em {@link ProdutoDTO}.
     *
     * @param produto entidade vinda do banco
     * @return DTO pronto para serializacao JSON
     */
    public static ProdutoDTO from(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco(produto.getPreco());
        dto.setQuantidade(produto.getQuantidade());
        dto.setCategoria(produto.getCategoria());
        dto.setDescricao(produto.getDescricao());
        return dto;
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
