package br.ueg.trindade.braully_web2_fullstack.service;

import br.ueg.trindade.braully_web2_fullstack.dto.ProdutoDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.ProdutoRequest;
import br.ueg.trindade.braully_web2_fullstack.exception.RecursoNaoEncontradoException;
import br.ueg.trindade.braully_web2_fullstack.exception.RegraDeNegocioException;
import br.ueg.trindade.braully_web2_fullstack.model.Produto;
import br.ueg.trindade.braully_web2_fullstack.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SERVICE DE PRODUTO — CAMADA DE REGRA DE NEGOCIO
 *
 * <p>Entidade de interesse do projeto final, refatorada para o padrao
 * <b>Controller → Service → Repository</b> conforme a Aula 06.</p>
 *
 * <p><b>Regra de negocio implementada:</b> o nome do produto nao pode se
 * repetir (comparado sem diferenciar maiusculas/minusculas).</p>
 *
 * <p>Obs.: o preco ja e validado no DTO com {@code @Positive} e a quantidade
 * com {@code @PositiveOrZero} — regras que o Spring aplica antes mesmo de o
 * Service ser chamado.</p>
 */
@Service
public class ProdutoService {

    /** Repository injetado pelo Spring (IoC). */
    private final ProdutoRepository produtoRepository;

    /**
     * Construtor com injecao de dependencia.
     *
     * @param produtoRepository repository da entidade Produto
     */
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * LISTAR todos os produtos.
     *
     * <p>Endpoint: {@code GET /api/produtos} → {@code findAll()}</p>
     *
     * @return lista de {@link ProdutoDTO}
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoDTO::from)
                .toList();
    }

    /**
     * BUSCAR um produto pelo id.
     *
     * <p>Endpoint: {@code GET /api/produtos/{id}} → {@code findById()}</p>
     *
     * @param id identificador do produto
     * @return {@link ProdutoDTO} encontrado
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto nao encontrado com o id " + id
                ));
        return ProdutoDTO.from(produto);
    }

    /**
     * CRIAR um novo produto.
     *
     * <p>Endpoint: {@code POST /api/produtos} → {@code save()}</p>
     *
     * @param dados dados vindos do corpo JSON (preco e quantidade ja validados)
     * @return {@link ProdutoDTO} criado
     * @throws RegraDeNegocioException se o nome ja existir
     */
    @Transactional
    public ProdutoDTO criar(ProdutoRequest dados) {
        // REGRA DE NEGOCIO: nome unico
        if (produtoRepository.existsByNomeIgnoreCase(dados.getNome())) {
            throw new RegraDeNegocioException(
                    "Ja existe um produto cadastrado com o nome " + dados.getNome()
            );
        }

        Produto produto = dados.toEntity();
        Produto salvo = produtoRepository.save(produto);
        return ProdutoDTO.from(salvo);
    }

    /**
     * ATUALIZAR um produto existente.
     *
     * <p>Endpoint: {@code PUT /api/produtos/{id}} → {@code save()}</p>
     *
     * @param id    identificador do produto
     * @param dados novos dados
     * @return {@link ProdutoDTO} atualizado
     * @throws RecursoNaoEncontradoException se o id nao existir
     * @throws RegraDeNegocioException se o novo nome pertencer a outro produto
     */
    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoRequest dados) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto nao encontrado com o id " + id
                ));

        // REGRA DE NEGOCIO: nome unico (ignorando o proprio id na edicao)
        if (produtoRepository.existsByNomeIgnoreCaseAndIdNot(dados.getNome(), id)) {
            throw new RegraDeNegocioException(
                    "Ja existe outro produto cadastrado com o nome " + dados.getNome()
            );
        }

        // Copia apenas os campos editaveis
        produto.setNome(dados.getNome());
        produto.setPreco(dados.getPreco());
        produto.setQuantidade(dados.getQuantidade());
        produto.setCategoria(dados.getCategoria());
        produto.setDescricao(dados.getDescricao());

        // save() com id preenchido = UPDATE
        Produto atualizado = produtoRepository.save(produto);
        return ProdutoDTO.from(atualizado);
    }

    /**
     * EXCLUIR um produto pelo id.
     *
     * <p>Endpoint: {@code DELETE /api/produtos/{id}} → {@code delete()}</p>
     *
     * @param id identificador do produto
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    @Transactional
    public void excluir(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto nao encontrado com o id " + id
                ));
        produtoRepository.delete(produto);
    }
}
