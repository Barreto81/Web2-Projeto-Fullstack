package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Permissao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO DE ENTRADA — PERMISSAO (POST e PUT)
 *
 * <p>Corpo JSON aceito em {@code POST /api/permissoes} e
 * {@code PUT /api/permissoes/{id}}.</p>
 *
 * <p><b>Validações declarativas:</b> nome obrigatório (pois é o identificador
 * da permissão); descrição opcional (um {@code @Size} apenas limita o
 * tamanho quando o valor é preenchido).</p>
 */
public class PermissaoRequest {

    /** Nome da permissão — obrigatório, único no sistema. */
    @NotBlank(message = "O nome da permissão é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    /** Descrição — opcional, máximo 255 caracteres. */
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    /** Construtor padrão — necessário para o Jackson. */
    public PermissaoRequest() {
    }

    /**
     * Converte o DTO de entrada na entidade {@link Permissao}.
     * <p>O {@code id} não é copiado: em INSERT ele é gerado pelo banco.</p>
     *
     * @return entidade pronta para persistência
     */
    public Permissao toEntity() {
        Permissao permissao = new Permissao();
        permissao.setNome(this.nome);
        permissao.setDescricao(this.descricao);
        return permissao;
    }

    /* ============================ GETTERS ============================ */

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    /* ============================ SETTERS ============================ */

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
