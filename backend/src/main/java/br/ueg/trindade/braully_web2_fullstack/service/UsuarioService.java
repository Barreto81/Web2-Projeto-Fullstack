package br.ueg.trindade.braully_web2_fullstack.service;

import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioAtualizacaoRequest;
import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioRequest;
import br.ueg.trindade.braully_web2_fullstack.exception.RecursoNaoEncontradoException;
import br.ueg.trindade.braully_web2_fullstack.exception.RegraDeNegocioException;
import br.ueg.trindade.braully_web2_fullstack.model.Usuario;
import br.ueg.trindade.braully_web2_fullstack.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * SERVICE DE USUARIO — CAMADA DE REGRA DE NEGOCIO
 *
 * <p><b>Arquitetura em camadas (Aula 06):</b></p>
 * <pre>
 *   Controller  →  SERVICE  →  Repository  →  Banco
 * </pre>
 *
 * <p>Esta classe e o <b>coracao do sistema</b>. Ela:</p>
 * <ul>
 *   <li>coordena o acesso ao banco (via {@link UsuarioRepository});</li>
 *   <li>aplica as <b>regras de negocio</b> que o controller nao deve tratar;</li>
 *   <li>converte entidades em DTOs antes de devolver ao controller.</li>
 * </ul>
 *
 * <p><b>Por que separar Controller e Service?</b> (Aula 06)</p>
 * <ul>
 *   <li>O Controller cuida apenas de HTTP (ler a URL, o body, devolver status);</li>
 *   <li>O Service cuida da logica — assim a mesma regra pode ser usada por
 *       outros controllers ou por testes <b>sem subir o servidor</b>;</li>
 *   <li>Ao adicionar validacoes/permissoes, o Controller nao vira um
 *       "canivete suico".</li>
 * </ul>
 *
 * <p><b>Regras de negocio implementadas (exercicio da Aula 06):</b></p>
 * <ol>
 *   <li>E-mail nao pode se repetir (criacao e edicao);</li>
 *   <li>Login (username) nao pode se repetir (criacao e edicao);</li>
 *   <li>E-mail e login sao comparados <b>sem diferenciar maiusculas</b> —
 *       "Joao@Exemplo.com" e "joao@exemplo.com" contam como o mesmo.</li>
 * </ol>
 *
 * <p><b>Annotacao {@code @Service}:</b> marca a classe como um bean da
 * camada de servico. O Spring a registra no Container IoC e permite que os
 * controllers injetem-na com {@code @Autowired}.</p>
 *
 * <p><b>Annotacao {@code @Transactional}:</b> garante atomicidade — ou a
 * operacao inteira e gravada, ou nenhuma e gravada (evita dados meio
 * salvos em caso de erro).</p>
 */
@Service
public class UsuarioService {

    /** Repository injetado pelo Spring (Injecao de Dependencia — IoC). */
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor com injecao de dependencia.
     * <p>O Spring injeta o {@code UsuarioRepository} automaticamente.
     * Prefira construtor a {@code @Autowired} em campo: deixa as dependencias
     * explicitas e facilita a criacao de testes.</p>
     *
     * @param usuarioRepository repository da entidade Usuario
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * LISTAR TODOS os usuarios cadastrados.
     *
     * <p>Mapeamento CRUD (Aula 05):<br>
     * <b>Read</b> → {@code GET /api/usuarios} → {@code findAll()}</p>
     *
     * @return lista de {@link UsuarioDTO} pronta para serializacao JSON
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        // findAll() vem pronto do JpaRepository — a implementacao e gerada
        // automaticamente pelo Spring Data JPA em tempo de execucao.
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioDTO::from)   // entidade -> DTO (sem expor a senha)
                .toList();
    }

    /**
     * BUSCAR um usuario pelo id.
     *
     * <p>Mapeamento CRUD (Aula 05):<br>
     * <b>Read (um)</b> → {@code GET /api/usuarios/{id}} → {@code findById()}</p>
     *
     * @param id identificador do usuario
     * @return {@link UsuarioDTO} do usuario encontrado
     * @throws RecursoNaoEncontradoException se o id nao existir no banco
     */
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        // findById() retorna Optional<Usuario>:
        //   - vazio  → o registro nao existe;
        //   - com valor → o registro existe e esta "dentro" do Optional.
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuario nao encontrado com o id " + id
                ));
        return UsuarioDTO.from(usuario);
    }

    /**
     * CRIAR um novo usuario (validando regras de negocio antes).
     *
     * <p>Mapeamento CRUD (Aula 05):<br>
     * <b>Create</b> → {@code POST /api/usuarios} → {@code save()}</p>
     *
     * @param dados dados vindos do corpo JSON (ja validados pelo Spring)
     * @return {@link UsuarioDTO} do usuario criado (com o id gerado)
     * @throws RegraDeNegocioException se o e-mail ou login ja existirem
     */
    @Transactional
    public UsuarioDTO criar(UsuarioRequest dados) {
        // ---- REGRA DE NEGOCIO: e-mail unico ----
        if (usuarioRepository.existsByEmailIgnoreCase(dados.getEmail())) {
            throw new RegraDeNegocioException(
                    "Ja existe um usuario cadastrado com o e-mail " + dados.getEmail()
            );
        }

        // ---- REGRA DE NEGOCIO: login unico ----
        if (usuarioRepository.existsByUsernameIgnoreCase(dados.getUsername())) {
            throw new RegraDeNegocioException(
                    "Ja existe um usuario cadastrado com o username " + dados.getUsername()
            );
        }

        // Converte o DTO de entrada em entidade (nao copia o id: o banco gera)
        Usuario usuario = dados.toEntity();

        // save() insere e retorna a entidade com o id preenchido pelo banco
        Usuario salvo = usuarioRepository.save(usuario);

        // Devolve sempre o DTO — nunca a entidade crua
        return UsuarioDTO.from(salvo);
    }

    /**
     * ATUALIZAR um usuario existente.
     *
     * <p>Mapeamento CRUD (Aula 05):<br>
     * <b>Update</b> → {@code PUT /api/usuarios/{id}} → {@code save()}</p>
     *
     * <p><b>Como o PUT funciona:</b> recuperamos o registro existente por
     * {@code findById}, copiamos apenas os campos alteraveis e chamamos
     * {@code save()} novamente. Como o objeto ja possui id, o Spring Data
     * executa um UPDATE (e nao um INSERT).</p>
     *
     * <p><b>Obs.:</b> a senha <b>nao e alterada</b> nesta operacao — conforme
     * a Aula 05, ela vem com {@code @JsonIgnore}/{@code WRITE_ONLY} e nao
     * chega no JSON. A troca de senha sera tratada na Aula 10 (autenticacao).</p>
     *
     * @param id    identificador do usuario a ser atualizado
     * @param dados novos dados (nome, username, email)
     * @return {@link UsuarioDTO} atualizado
     * @throws RecursoNaoEncontradoException se o id nao existir
     * @throws RegraDeNegocioException se o novo e-mail/login pertencer a outro usuario
     */
    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioAtualizacaoRequest dados) {
        // 1) Recupera o registro existente (ou lanca 404)
        Usuario usuario = buscarEntidadePorId(id);

        // 2) REGRA DE NEGOCIO: novo e-mail nao pode pertencer a OUTRO usuario
        if (usuarioRepository.existsByEmailIgnoreCaseAndIdNot(dados.getEmail(), id)) {
            throw new RegraDeNegocioException(
                    "Ja existe outro usuario cadastrado com o e-mail " + dados.getEmail()
            );
        }

        // 3) REGRA DE NEGOCIO: novo login nao pode pertencer a OUTRO usuario
        if (usuarioRepository.existsByUsernameIgnoreCaseAndIdNot(dados.getUsername(), id)) {
            throw new RegraDeNegocioException(
                    "Ja existe outro usuario cadastrado com o username " + dados.getUsername()
            );
        }

        // 4) Copia APENAS os campos permitidos (a senha nao e tocada)
        usuario.setNome(dados.getNome());
        usuario.setUsername(dados.getUsername());
        usuario.setEmail(dados.getEmail());

        // 5) save() com id preenchido = UPDATE no banco
        Usuario atualizado = usuarioRepository.save(usuario);
        return UsuarioDTO.from(atualizado);
    }

    /**
     * EXCLUIR um usuario pelo id.
     *
     * <p>Mapeamento CRUD (Aula 05):<br>
     * <b>Delete</b> → {@code DELETE /api/usuarios/{id}} → {@code deleteById()}</p>
     *
     * @param id identificador do usuario a ser removido
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    @Transactional
    public void excluir(Long id) {
        // Verifica a existencia antes de excluir para devolver 404 amigavel
        // (o deleteById do Spring Data lanca EmptyResultDataAccessException
        //  caso o id nao exista — aqui tratamos isso antes, com nossa propria
        //  excecao e mensagem).
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    /**
     * Busca a entidade Usuario bruta (interno do Service).
     *
     * @param id identificador do usuario
     * @return entidade Usuario encontrada
     * @throws RecursoNaoEncontradoException se o id nao existir
     */
    private Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuario nao encontrado com o id " + id
                ));
    }
}
