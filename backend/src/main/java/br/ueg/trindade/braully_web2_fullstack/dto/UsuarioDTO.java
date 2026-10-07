package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Usuario;

import java.time.LocalDateTime;

/**
 * DTO DE RESPOSTA — USUARIO
 *
 * <p><b>O que é um DTO?</b><br>
 * <i>Data Transfer Object</i> (Objeto de Transferência de Dados) é uma
 * classe simples usada para <b>controlar exatamente quais campos da API
 * são expostos</b> ao cliente.</p>
 *
 * <p><b>Por que usar DTOs?</b> (Aula 02 — recomendação do professor)<br>
 * Ao devolver a entidade {@code Usuario} diretamente, o JSON sempre incluiria
 * todos os campos da tabela — inclusive a senha. Mesmo com
 * {@code @JsonIgnore}, em projetos reais recomendam-se DTOs porque:</p>
 * <ul>
 *   <li>dão controle total sobre o que trafega na rede;</li>
 *   <li>desacoplam a API da estrutura do banco (pode mudar a tabela sem
 *       quebrar o front-end);</li>
 *   <li>permitem expor campos derivados (como a data formatada).</li>
 * </ul>
 *
 * <p><b>Este DTO NUNCA contém o campo {@code senha}</b> — assim a senha
 * é protegida mesmo que alguém esqueça uma anotação na entidade.</p>
 */
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String username;
    private String email;
    private LocalDateTime dataCadastro;

    /** Construtor padrão — necessário para o Jackson (deserialização). */
    public UsuarioDTO() {
    }

    /**
     * Converte uma entidade {@link Usuario} em {@link UsuarioDTO}.
     * <p>Método estático de conveniência usado pelo Service para devolver
     * as respostas da API sem vazar dados internos.</p>
     *
     * @param usuario entidade vinda do banco
     * @return DTO pronto para serialização JSON
     */
    public static UsuarioDTO from(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setDataCadastro(usuario.getDataCadastro());
        // repare: senha NUNCA é copiada para o DTO
        return dto;
    }

    /* ============================ GETTERS ============================ */

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    /* ============================ SETTERS ============================ */

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
