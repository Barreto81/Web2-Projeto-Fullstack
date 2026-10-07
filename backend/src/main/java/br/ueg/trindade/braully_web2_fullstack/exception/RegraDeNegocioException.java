package br.ueg.trindade.braully_web2_fullstack.exception;

/**
 * EXCECAO CUSTOMIZADA — REGRA DE NEGOCIO VIOLADA
 *
 * <p>Lancada pelo Service quando o dado e valido (passou na validacao de
 * campo) mas viola uma <b>regra de negocio</b>. Exemplos praticos deste
 * projeto:</p>
 * <ul>
 *   <li>Cadastrar um usuario com e-mail que ja existe (Aula 06 — exercicio 3:
 *       "adicionar uma regra de negocio no Service da sua entidade (ex.:
 *       impedir cadastro com e-mail duplicado)");</li>
 *   <li>Cadastrar um usuario com login ja utilizado;</li>
 *   <li>Criar uma permissao ou produto com nome duplicado.</li>
 * </ul>
 *
 * <p>O {@link GlobalExceptionHandler} converte esta excecao em uma resposta
 * HTTP 400 (Bad Request) com a mensagem explicando o que esta errado.</p>
 *
 * <p>Como {@code RuntimeException}, e <i>unchecked</i>: nao precisa ser
 * declarada com {@code throws}.</p>
 */
public class RegraDeNegocioException extends RuntimeException {

    /** Serializacao padrao. */
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com a mensagem da regra violada.
     *
     * @param mensagem texto explicando a regra (ex.: "Ja existe um usuario
     *                 cadastrado com o e-mail joao@exemplo.com")
     */
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
