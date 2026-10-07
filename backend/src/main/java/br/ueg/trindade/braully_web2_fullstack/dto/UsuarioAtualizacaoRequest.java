package br.ueg.trindade.braully_web2_fullstack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO DE ENTRADA — ATUALIZAÇÃO DE USUARIO (PUT)
 *
 * <p>Representa o corpo JSON aceito em
 * {@code PUT /api/usuarios/{id}}.</p>
 *
 * <p><b>Por que um DTO separado do {@link UsuarioRequest}?</b><br>
 * Porque as regras de edição são diferentes das de criação:</p>
 * <ul>
 *   <li><b>Criação (POST):</b> a senha é obrigatória — o usuário nasce com
 *       uma senha;</li>
 *   <li><b>Atualização (PUT):</b> a senha <b>não é alterada</b> (conforme a
 *       Aula 05: "a senha não é alterada no PUT porque ela está com
 *       {@code @JsonIgnore} e não chega no JSON — trataremos autenticação
 *       na Aula 10"). Por isso este DTO <b>não possui o campo senha</b>.</li>
 * </ul>
 */
public class UsuarioAtualizacaoRequest {

    /** Novo nome — obrigatório. */
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    /** Novo login — obrigatório. */
    @NotBlank(message = "O username é obrigatório")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    private String username;

    /** Novo e-mail — obrigatório e válido. */
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    /** Construtor padrão — necessário para o Jackson. */
    public UsuarioAtualizacaoRequest() {
    }

    /* ============================ GETTERS ============================ */

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    /* ============================ SETTERS ============================ */

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
