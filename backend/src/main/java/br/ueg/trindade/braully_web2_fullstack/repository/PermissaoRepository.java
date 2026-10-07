package br.ueg.trindade.braully_web2_fullstack.repository;

import br.ueg.trindade.braully_web2_fullstack.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * REPOSITORY DE PERMISSAO
 *
 * <p>Interface criada como <b>exercício da Aula 03</b>, estendendo
 * {@code JpaRepository<Permissao, Long>}.</p>
 *
 * <p>Assim como no {@code UsuarioRepository}, todos os métodos básicos do
 * CRUD são herdados prontos e os métodos customizados são <b>derivados do
 * nome</b> pelo Spring Data — nenhuma implementação precisa ser escrita.</p>
 *
 * <p><b>Exercício da Aula 03:</b> criar os endpoints POST /permissoes e
 * GET /permissoes/{id} usando esta interface.</p>
 */
@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    /**
     * Verifica se já existe alguma permissão com o nome informado.
     *
     * @param nome nome da permissão a ser verificado
     * @return {@code true} se já existir, {@code false} caso contrário
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Verifica se o nome informado pertence a outra permissão (diferente do id).
     * <p>Usado na atualização (PUT) para não bloquear o próprio registro.</p>
     *
     * @param nome       nome a ser verificado
     * @param idExcluido id da permissão que está sendo editada
     * @return {@code true} se o nome já pertencer a outra permissão
     */
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long idExcluido);

    /**
     * Busca uma permissão pelo nome, ignorando maiúsculas/minúsculas.
     *
     * @param nome nome da permissão
     * @return {@code Optional} com a permissão encontrada (ou vazio)
     */
    Optional<Permissao> findByNomeIgnoreCase(String nome);
}
