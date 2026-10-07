package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Permissao;

/**
 * DTO DE RESPOSTA — PERMISSAO
 *
 * <p>Objeto exposto pela API em {@code GET /api/permissoes} e
 * {@code GET /api/permissoes/{id}}.</p>
 *
 * <p>O método estático {@link #from(Permissao)} converte a entidade JPA em
 * DTO — prática recomendada para manter o contrato da API estável mesmo
 * que a tabela mude.</p>
 */
public class PermissaoDTO {

    private Long id;
    private String nome;
    private String descricao;

    /** Construtor padrão — necessário para o Jackson. */
    public PermissaoDTO() {
    }

    /**
     * Converte a entidade {@link Permissao} em {@link PermissaoDTO}.
     *
     * @param permissao entidade vinda do banco
     * @return DTO pronto para serialização JSON
     */
    public static PermissaoDTO from(Permissao permissao) {
        PermissaoDTO dto = new PermissaoDTO();
        dto.setId(permissao.getId());
        dto.setNome(permissao.getNome());
        dto.setDescricao(permissao.getDescricao());
        return dto;
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
