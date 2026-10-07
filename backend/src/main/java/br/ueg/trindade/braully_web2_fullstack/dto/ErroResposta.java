package br.ueg.trindade.braully_web2_fullstack.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO DE ERRO — RESPOSTA PADRAO DE FALHAS
 *
 * <p>Toda vez que a API devolve um erro (400, 404, 500...), o corpo da
 * resposta segue <b>este formato padrao</b>, facilitando o tratamento no
 * front-end (o Axios consegue ler sempre os mesmos campos).</p>
 *
 * <p><b>Formato exemplo (HTTP 404):</b></p>
 * <pre>
 * {
 *   "status": 404,
 *   "mensagem": "Usuario nao encontrado com o id 99",
 *   "dataHora": "2026-10-02T21:00:00.000",
 *   "campos": null
 * }
 * </pre>
 *
 * <p><b>Formato exemplo (HTTP 400 — validacao):</b></p>
 * <pre>
 * {
 *   "status": 400,
 *   "mensagem": "Erro de validacao nos campos enviados",
 *   "dataHora": "2026-10-02T21:00:00.000",
 *   "campos": [
 *     { "campo": "email", "mensagem": "Informe um e-mail valido" }
 *   ]
 * }
 * </pre>
 *
 * <p>{@code @JsonInclude(NON_NULL)} faz o Jackson <b>omiter</b> os campos
 * nulos — assim a resposta fica mais limpa quando nao ha lista de campos.</p>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResposta {

    /** Codigo HTTP do erro (ex.: 400, 404, 500). */
    private Integer status;

    /** Mensagem principal legivel para o usuario. */
    private String mensagem;

    /** Data/hora em que o erro ocorreu. */
    private LocalDateTime dataHora;

    /** Lista de erros de campo — preenchida apenas em falhas de validacao. */
    private List<CampoInvalido> campos;

    /** Construtor padrao — necessario para o Jackson. */
    public ErroResposta() {
    }

    /**
     * Cria uma resposta de erro simples (sem detalhes de campo).
     *
     * @param status   codigo HTTP
     * @param mensagem mensagem principal
     * @return resposta de erro pronta
     */
    public static ErroResposta de(Integer status, String mensagem) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(status);
        resposta.setMensagem(mensagem);
        resposta.setDataHora(LocalDateTime.now());
        return resposta;
    }

    /* ============================ GETTERS ============================ */

    public Integer getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<CampoInvalido> getCampos() {
        return campos;
    }

    /* ============================ SETTERS ============================ */

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setCampos(List<CampoInvalido> campos) {
        this.campos = campos;
    }
}
