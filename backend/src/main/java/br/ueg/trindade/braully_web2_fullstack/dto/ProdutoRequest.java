package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO DE ENTRADA — PRODUTO (POST e PUT)
 *
 * <p>Corpo JSON aceito em {@code POST /api/produtos} e
 * {@code PUT /api/produtos/{id}}.</p>
 *
 * <p><b>Validacoes declarativas (spring-boot-starter-validation):</b></p>
 * <ul>
 *   <li>{@code @NotBlank} — campos de texto obrigatorios;</li>
 *   <li>{@code @NotNull} — campos numericos obrigatorios;</li>
 *   <li>{@code @Positive} — o preco deve ser maior que zero;</li>
 *   <li>{@code @PositiveOrZero} — a quantidade nao pode ser negativa
 *       (zero permite produto "sem estoque" cadastrado).</li>
 * </ul>
 */
public class ProdutoRequest {

    /** Nome do produto — obrigatorio, unico no sistema. */
    @NotBlank(message = "O nome do produto e obrigatorio")
    @Size(min = 2, max = 120, message = "O nome deve ter entre 2 e 120 caracteres")
    private String nome;

    /** Preco unitario — obrigatorio e positivo. */
    @NotNull(message = "O preco e obrigatorio")
    @Positive(message = "O preco deve ser maior que zero")
    private BigDecimal preco;

    /** Quantidade em estoque — obrigatoria e nao negativa. */
    @NotNull(message = "A quantidade e obrigatoria")
    @PositiveOrZero(message = "A quantidade nao pode ser negativa")
    private Integer quantidade;

    /** Categoria — opcional. */
    @Size(max = 80, message = "A categoria deve ter no maximo 80 caracteres")
    private String categoria;

    /** Descricao — opcional. */
    @Size(max = 500, message = "A descricao deve ter no maximo 500 caracteres")
    private String descricao;

    /** Construtor padrao — necessario para o Jackson. */
    public ProdutoRequest() {
    }

    /**
     * Converte o DTO de entrada na entidade {@link Produto}.
     * <p>O {@code id} nao e copiado: em INSERT ele e gerado pelo banco.</p>
     *
     * @return entidade pronta para persistencia
     */
    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setNome(this.nome);
        produto.setPreco(this.preco);
        produto.setQuantidade(this.quantidade);
        produto.setCategoria(this.categoria);
        produto.setDescricao(this.descricao);
        return produto;
    }

    /* ============================ GETTERS ============================ */

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
