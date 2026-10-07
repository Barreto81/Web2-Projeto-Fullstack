package br.ueg.trindade.braully_web2_fullstack.exception;

import br.ueg.trindade.braully_web2_fullstack.dto.CampoInvalido;
import br.ueg.trindade.braully_web2_fullstack.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * TRATAMENTO GLOBAL DE EXCECOES
 *
 * <p><b>O que faz:</b> toda vez que um erro acontece em qualquer
 * controller da aplicacao, esta classe captura a excecao e converte em uma
 * <b>resposta JSON padronizada</b> com o codigo HTTP correto.</p>
 *
 * <p><b>Por que usar?</b> (Aula 02 citou "tratamento de excecoes e
 * validacao de dados" como recurso do Spring Boot)</p>
 * <ul>
 *   <li>Evita que o cliente receba um HTML de erro feio do Tomcat;</li>
 *   <li>Devolve um formato consistente ({@link ErroResposta}) que o
 *       front-end consegue interpretar sempre;</li>
 *   <li>Centraliza o tratamento: os controllers ficam "limpos", sem
 *       try/catch espalhado.</li>
 * </ul>
 *
 * <p><b>Anotacao {@code @RestControllerAdvice}:</b> combina
 * {@code @ControllerAdvice} (aplica-se a todos os controllers) com
 * {@code @RestController} (os metodos devolvem JSON direto, sem view).</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata {@link RecursoNaoEncontradoException}.
     *
     * <p>Ex.: GET /api/usuarios/999 → HTTP 404 com a mensagem
     * "Usuario nao encontrado com o id 999".</p>
     *
     * @param erro excecao lancada pelo Service
     * @return resposta HTTP 404 com o corpo padrao de erro
     */
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> tratarNaoEncontrado(RecursoNaoEncontradoException erro) {
        ErroResposta corpo = ErroResposta.de(
                HttpStatus.NOT_FOUND.value(),
                erro.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpo);
    }

    /**
     * Trata {@link RegraDeNegocioException}.
     *
     * <p>Ex.: POST /api/usuarios com e-mail ja existente → HTTP 400 com a
     * mensagem "Ja existe um usuario cadastrado com o e-mail ...".</p>
     *
     * @param erro excecao lancada pelo Service
     * @return resposta HTTP 400 com o corpo padrao de erro
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResposta> tratarRegraDeNegocio(RegraDeNegocioException erro) {
        ErroResposta corpo = ErroResposta.de(
                HttpStatus.BAD_REQUEST.value(),
                erro.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * Trata falhas de <b>validacao declarativa</b> (anotacoes jakarta).
     *
     * <p>Ex.: POST /api/usuarios sem o campo "nome" → HTTP 400 com a lista
     * de campos invalidos:</p>
     * <pre>
     * {
     *   "status": 400,
     *   "mensagem": "Erro de validacao nos campos enviados",
     *   "campos": [ { "campo": "nome", "mensagem": "O nome e obrigatorio" } ]
     * }
     * </pre>
     *
     * @param erro excecao disparada pelo Spring quando a validacao falha
     * @return resposta HTTP 400 com o detalhamento campo a campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(MethodArgumentNotValidException erro) {
        List<CampoInvalido> campos = new ArrayList<>();

        // Percorre cada erro de campo gerado pelas anotacoes (@NotBlank, @Email...)
        for (FieldError campoErro : erro.getBindingResult().getFieldErrors()) {
            campos.add(CampoInvalido.de(campoErro.getField(), campoErro.getDefaultMessage()));
        }

        ErroResposta corpo = ErroResposta.de(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validacao nos campos enviados"
        );
        corpo.setCampos(campos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * Trata corpo JSON malformado ou com tipos incompativeis.
     *
     * <p>Ex.: enviar "preco": "abc" quando o campo espera um numero →
     * HTTP 400 explicando o problema.</p>
     *
     * @param erro excecao de leitura do corpo da requisicao
     * @return resposta HTTP 400 com mensagem amigavel
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> tratarCorpoInvalido(HttpMessageNotReadableException erro) {
        ErroResposta corpo = ErroResposta.de(
                HttpStatus.BAD_REQUEST.value(),
                "Corpo da requisicao invalido ou com tipos de dados incompativeis"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * Trata parametro de URL com tipo errado.
     *
     * <p>Ex.: GET /api/usuarios/abc → o id espera Long, mas chegou String.
     * Devolve HTTP 400 em vez de estourar um erro 500.</p>
     *
     * @param erro excecao de tipo em parametro
     * @return resposta HTTP 400 com mensagem amigavel
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> tratarTipoDeParametro(MethodArgumentTypeMismatchException erro) {
        ErroResposta corpo = ErroResposta.de(
                HttpStatus.BAD_REQUEST.value(),
                "Parametro invalido: '" + erro.getValue()
                        + "' no campo '" + erro.getName() + "'"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * Trata parametro query ausente quando e obratorio.
     *
     * @param erro excecao de parametro faltante
     * @return resposta HTTP 400 com mensagem amigavel
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroResposta> tratarParametroAusente(MissingServletRequestParameterException erro) {
        ErroResposta corpo = ErroResposta.de(
                HttpStatus.BAD_REQUEST.value(),
                "O parametro '" + erro.getParameterName() + "' e obrigatorio"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    /**
     * Trata <b>qualquer outra excecao nao tratada</b> (fallback de seguranca).
     *
     * <p>Se algo inesperado falhar, devolvemos HTTP 500 com uma mensagem
     * generica. <b>Importante:</b> NAO expomos a pilha de excecao para o
     * cliente — isso seria um risco de seguranca. O detalhe tecnico fica
     * apenas no log do servidor.</p>
     *
     * @param erro excecao inesperada
     * @return resposta HTTP 500 com mensagem generica
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroInesperado(Exception erro) {
        // Loga o detalhe no console do servidor para o programador investigar
        System.err.println("Erro inesperado na API: " + erro.getMessage());
        erro.printStackTrace();

        ErroResposta corpo = ErroResposta.de(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor. Tente novamente mais tarde."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(corpo);
    }
}
