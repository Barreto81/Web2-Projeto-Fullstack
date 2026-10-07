package br.ueg.trindade.braully_web2_fullstack.exception;

/**
 * EXCECAO CUSTOMIZADA — RECURSO NAO ENCONTRADO
 *
 * <p>Lancada quando o Service procura um registro pelo id e ele nao existe
 * no banco (ex.: {@code GET /api/usuarios/999}).</p>
 *
 * <p><b>Por que criar excecoes proprias em vez de {@code RuntimeException}
 * generica?</b> (evolucao do que foi mostrado nas Aulas 03 e 05)</p>
 * <ul>
 *   <li>Permitem que o {@link GlobalExceptionHandler} trate cada erro com o
 *       codigo HTTP correto (404 para "nao encontrado");</li>
 *   <li>Deixam o codigo do Service mais legivel e intencional;</li>
 *   <li>Evitam que falhas de sistema (ex.: banco fora do ar) sejam
 *       confundidas com "registro inexistente".</li>
 * </ul>
 *
 * <p><b>Herança:</b> estende {@code RuntimeException} para ser
 * <i>unchecked</i> — ou seja, nao precisa ser declarada na assinatura dos
 * metodos (nada de {@code throws} em toda a pilha de chamadas).</p>
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    /** Serializacao padrao (boa pratica ao estender Exception). */
    private static final long serialVersionUID = 1L;

    /**
     * Cria a excecao com uma mensagem descritiva.
     *
     * @param mensagem texto explicando o que nao foi encontrado
     *                 (ex.: "Usuario nao encontrado com o id 5")
     */
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
