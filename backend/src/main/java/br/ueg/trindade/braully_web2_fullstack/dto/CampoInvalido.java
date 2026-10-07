package br.ueg.trindade.braully_web2_fullstack.dto;

/**
 * DTO AUXILIAR — ERRO DE UM CAMPO ESPECIFICO
 *
 * <p>Usado dentro de {@link ErroResposta} para indicar <b>qual campo</b> do
 * JSON foi invalido e <b>qual a mensagem</b> correspondente.</p>
 *
 * <p><b>Exemplo de uso no front-end:</b> o React pode ler
 * {@code resposta.data.campos[0].campo} e {@code .mensagem} para destacar o
 * input problemático em vermelho.</p>
 */
public class CampoInvalido {

    /** Nome do campo que falhou na validacao (ex.: "email", "nome"). */
    private String campo;

    /** Mensagem de validacao daquele campo. */
    private String mensagem;

    /** Construtor padrao — necessario para o Jackson. */
    public CampoInvalido() {
    }

    /**
     * Cria um erro de campo.
     *
     * @param campo    nome do campo invalido
     * @param mensagem mensagem de validacao
     * @return objeto de erro de campo
     */
    public static CampoInvalido de(String campo, String mensagem) {
        CampoInvalido item = new CampoInvalido();
        item.setCampo(campo);
        item.setMensagem(mensagem);
        return item;
    }

    /* ============================ GETTERS ============================ */

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    /* ============================ SETTERS ============================ */

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
