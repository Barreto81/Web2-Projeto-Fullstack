package br.ueg.trindade.braully_web2_fullstack.repository;

import br.ueg.trindade.braully_web2_fullstack.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * REPOSITORY DE PRODUTO
 *
 * <p>Interface criada para a entidade de <b>interesse do projeto final</b>
 * (exercícios das Aulas 02, 03, 05 e 06).</p>
 *
 * <p>Assim como nos demais repositories, o CRUD completo (save, findAll,
 * findById, deleteById) é <b>fornecido automaticamente</b> pela
 * {@code JpaRepository}. Apenas as consultas específicas são declaradas
 * aqui — o Spring Data gera a implementação pelo nome do método.</p>
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Verifica se já existe algum produto com o nome informado.
     *
     * @param nome nome do produto a ser verificado
     * @return {@code true} se já existir, {@code false} caso contrário
     */
    boolean existsByNomeIgnoreCase(String nome);

    /**
     * Verifica se o nome informado pertence a outro produto (diferente do id).
     *
     * @param nome       nome a ser verificado
     * @param idExcluido id do produto que está sendo editado
     * @return {@code true} se o nome já pertencer a outro produto
     */
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long idExcluido);
}
