package br.ueg.trindade.braully_web2_fullstack.service;

import br.ueg.trindade.braully_web2_fullstack.dto.PermissaoDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.PermissaoRequest;
import br.ueg.trindade.braully_web2_fullstack.exception.RecursoNaoEncontradoException;
import br.ueg.trindade.braully_web2_fullstack.exception.RegraDeNegocioException;
import br.ueg.trindade.braully_web2_fullstack.model.Permissao;
import br.ueg.trindade.braully_web2_fullstack.repository.PermissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SERVICE DE PERMISSAO — CAMADA DE REGRA DE NEGOCIO
 *
 * <p>Exercicio das Aulas 03, 05 e 06: replicar o mesmo fluxo
 * <b>Controller → Service → Repository</b> da entidade Permissao
 * (criada na Aula 02 com id, nome e descricao).</p>
 *
 * <p><b>Regra de negocio implementada:</b> o nome da permissao nao pode
 * se repetir (comparado sem diferenciar maiusculas/minusculas).</p>
 *
 * <p>Esta classe recebe a anotacao {@code @Service} — o Spring a registra
 * como bean da camada de servico e injeta o {@code PermissaoRepository}
 * automaticamente no construtor (Injecao de Dependencia).</p>
 */
@Service
public class PermissaoService {

    /** Repository injetado pelo Spring (IoC). */
    private final PermissaoRepository permissaoRepository;

    /**
     * Construtor com injecao de dependencia.
     *
     * @param permissaoRepository repository da entidade Permissao
     */
    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    /**
     * LISTAR todas as permissoes.
     *
     * <p>Endpoint: {@code GET /api/permissoes} → {@code findAll()}</p>
     *
     * @return lista de {@link PermissaoDTO}
     */
    @Transactional(readOnly = true)
    public List<PermissaoDTO> listarTodas() {
        return permissaoRepository.findAll()
                .stream()
                .map(PermissaoDTO::from)
                .toList();
    }

    /**
     * BUSCAR uma permissao pelo id.
     *
     * <p>Endpoint: {@code GET /api/permissoes/{id}} → {@code findById()}</p>
     *
     * @param id identificador da permissao
     * @return {@link PermissaoDTO} encontrada
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    @Transactional(readOnly = true)
    public PermissaoDTO buscarPorId(Long id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Permissao nao encontrada com o id " + id
                ));
        return PermissaoDTO.from(permissao);
    }

    /**
     * CRIAR uma nova permissao.
     *
     * <p>Endpoint: {@code POST /api/permissoes} → {@code save()}</p>
     *
     * @param dados dados vindos do corpo JSON
     * @return {@link PermissaoDTO} criada
     * @throws RegraDeNegocioException se o nome ja existir
     */
    @Transactional
    public PermissaoDTO criar(PermissaoRequest dados) {
        // REGRA DE NEGOCIO: nome unico
        if (permissaoRepository.existsByNomeIgnoreCase(dados.getNome())) {
            throw new RegraDeNegocioException(
                    "Ja existe uma permissao cadastrada com o nome " + dados.getNome()
            );
        }

        Permissao permissao = dados.toEntity();
        Permissao salva = permissaoRepository.save(permissao);
        return PermissaoDTO.from(salva);
    }

    /**
     * ATUALIZAR uma permissao existente.
     *
     * <p>Endpoint: {@code PUT /api/permissoes/{id}} → {@code save()}</p>
     *
     * @param id    identificador da permissao
     * @param dados novos dados (nome, descricao)
     * @return {@link PermissaoDTO} atualizada
     * @throws RecursoNaoEncontradoException se o id nao existir
     * @throws RegraDeNegocioException se o novo nome pertencer a outra permissao
     */
    @Transactional
    public PermissaoDTO atualizar(Long id, PermissaoRequest dados) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Permissao nao encontrada com o id " + id
                ));

        // REGRA DE NEGOCIO: nome unico (ignorando o proprio id na edicao)
        if (permissaoRepository.existsByNomeIgnoreCaseAndIdNot(dados.getNome(), id)) {
            throw new RegraDeNegocioException(
                    "Ja existe outra permissao cadastrada com o nome " + dados.getNome()
            );
        }

        permissao.setNome(dados.getNome());
        permissao.setDescricao(dados.getDescricao());

        Permissao atualizada = permissaoRepository.save(permissao);
        return PermissaoDTO.from(atualizada);
    }

    /**
     * EXCLUIR uma permissao pelo id.
     *
     * <p>Endpoint: {@code DELETE /api/permissoes/{id}} → {@code delete()}</p>
     *
     * @param id identificador da permissao
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    @Transactional
    public void excluir(Long id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Permissao nao encontrada com o id " + id
                ));
        permissaoRepository.delete(permissao);
    }
}
