package br.ueg.trindade.braully_web2_fullstack.repository;

import br.ueg.trindade.braully_web2_fullstack.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY DE USUARIO
 *
 * <p><b>O que é um Repository no Spring Data JPA (Aula 03)?</b><br>
 * É uma <b>interface</b> que abstrai o acesso ao banco. O Spring Data JPA
 * <b>gera a implementação automaticamente em tempo de execução</b> — por
 * isso não precisamos escrever nenhuma linha de SQL/JPQL para o CRUD
 * básico. Não existe a classe {@code UsuarioRepositoryImpl} no código:
 * o Spring cria em memória.</p>
 *
 * <p><b>Métodos que já vêm prontos</b> (herdados de
 * {@code JpaRepository<Usuario, Long>}):</p>
 * <ul>
 *   <li>{@code save(entidade)} — insere ou atualiza;</li>
 *   <li>{@code findAll()} — lista todos os registros;</li>
 *   <li>{@code findById(id)} — busca por id (retorna {@code Optional});</li>
 *   <li>{@code deleteById(id)} — remove pelo id;</li>
 *   <li>{@code existsById(id)}, {@code count()}, etc.</li>
 * </ul>
 *
 * <p><b>Métodos customizados:</b> o Spring Data também <b>deriva</b>
 * implementações a partir do <b>nome do método</b>. Se o nome seguir o padrão
 * {@code findBy<Propriedade>[IgnoreCase]}, ele gera a consulta sozinho.
 * Isso evita escrever JPQL manual na maioria dos casos.</p>
 *
 * <p>Fluxo (Aula 03):<br>
 * Requisição → Controller → Service → <b>Repository</b> → Hibernate → Banco</p>
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um usuário pelo e-mail, ignorando maiúsculas/minúsculas.
     * <p>Spring Data implementa isto automaticamente a partir do nome:</p>
     * <pre>SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(?1)</pre>
     *
     * @param email e-mail a ser buscado
     * @return {@code Optional} com o usuário encontrado (ou vazio)
     */
    Optional<Usuario> findByEmailIgnoreCase(String email);

    /**
     * Busca um usuário pelo nome de usuário (login), sem diferenciar
     * maiúsculas e minúsculas.
     *
     * @param username login a ser buscado
     * @return {@code Optional} com o usuário encontrado (ou vazio)
     */
    Optional<Usuario> findByUsernameIgnoreCase(String username);

    /**
     * Verifica se já existe algum usuário com o e-mail informado.
     * <p>Usado pela regra de negócio da Aula 06: impedir cadastro com
     * e-mail duplicado.</p>
     *
     * @param email e-mail a ser verificado
     * @return {@code true} se já existir, {@code false} caso contrário
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Verifica se já existe algum usuário com o login informado.
     *
     * @param username login a ser verificado
     * @return {@code true} se já existir, {@code false} caso contrário
     */
    boolean existsByUsernameIgnoreCase(String username);

    /**
     * Busca por e-mail <b>ignorando</b> o id de um usuário específico.
     * <p>É útil na atualização (PUT): precisamos saber se o novo e-mail já
     * pertence a <b>outro</b> usuário, sem bloquear o próprio.</p>
     *
     * @param email          e-mail a ser verificado
     * @param idExcluido     id do usuário que está sendo editado (ignorado)
     * @return {@code true} se o e-mail pertencer a outro usuário
     */
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long idExcluido);

    /**
     * Busca por login <b>ignorando</b> o id de um usuário específico.
     *
     * @param username       login a ser verificado
     * @param idExcluido     id do usuário que está sendo editado
     * @return {@code true} se o login pertencer a outro usuário
     */
    boolean existsByUsernameIgnoreCaseAndIdNot(String username, Long idExcluido);
}
