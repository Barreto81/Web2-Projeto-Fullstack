package br.ueg.trindade.braully_web2_fullstack.dto;

import br.ueg.trindade.braully_web2_fullstack.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO DE ENTRADA — CRIAÇÃO DE USUARIO (POST)
 *
 * <p>Representa o corpo (body) JSON aceito em {@code POST /api/usuarios}.</p>
 *
 * <p><b>Validação declarativa (spring-boot-starter-validation):</b> as
 * anotações abaixo fazem o Spring recusar a requisição automaticamente com
 * HTTP 400 quando alguma regra não é atendida — sem precisar escrever
 * {@code if} dentro do Service.</p>
 *
 * <p><b>Fluxo:</b> Controller recebe → valida → Service cria a entidade →
 * Repository salva no banco → Controller devolve um {@code UsuarioDTO}.</p>
 */
public class UsuarioRequest {

    /** Nome completo — obrigatório, de 2 a 100 caracteres. */
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    /** Login — obrigatório, de 3 a 50 caracteres. */
    @NotBlank(message = "O username é obrigatório")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres")
    private String username;

    /**
     * Senha — obrigatória, mínimo 6 caracteres.
     * <p>Aqui aceitamos a senha porque é a entrada do cadastro. Ela é
     * gravada no banco e <b>nunca volta em nenhuma resposta</b> (o DTO de
     * resposta não possui o campo senha).</p>
     */
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    private String senha;

    /** E-mail — obrigatório e em formato válido. */
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    private String email;

    /** Construtor padrão — necessário para o Jackson deserializar o JSON. */
    public UsuarioRequest() {
    }

    /**
     * Converte o DTO de entrada em entidade {@link Usuario}.
     * <p>O {@code id} NÃO é copiado de propósito: em INSERT, o id é gerado
     * pelo banco (IDENTITY). Se o cliente enviasse um id, o Hibernate
     * tentaria um UPDATE e falharia.</p>
     *
     * @return entidade pronta para ser persistida pelo Service
     */
    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setUsername(this.username);
        usuario.setSenha(this.senha);
        usuario.setEmail(this.email);
        usuario.registrarDataCadastro();
        return usuario;
    }

    /* ============================ GETTERS ============================ */

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
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

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
