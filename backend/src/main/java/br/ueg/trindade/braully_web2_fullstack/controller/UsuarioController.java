package br.ueg.trindade.braully_web2_fullstack.controller;

import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioAtualizacaoRequest;
import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioRequest;
import br.ueg.trindade.braully_web2_fullstack.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER DE USUARIO — CAMADA HTTP (Aula 06)
 *
 * <p><b>Responsabilidade desta classe:</b> RECEBER requisicoes HTTP e
 * DEVOLVER respostas. Ela <b>nao</b> acessa o banco diretamente — quem
 * coordena e o {@code UsuarioService} (arquitetura em camadas).</p>
 *
 * <pre>
 *   Requisicao HTTP
 *        │
 *        ▼
 *   UsuarioController  ← voces estao aqui
 *        │
 *        ▼
 *   UsuarioService     (regras de negocio)
 *        │
 *        ▼
 *   UsuarioRepository  (Spring Data JPA)
 *        │
 *        ▼
 *   Banco H2
 * </pre>
 *
 * <p><b>Mapeamento CRUD completo (Aula 05) com as respostas corretas:</b></p>
 * <table border="1">
 *   <tr><th>Operacao</th><th>Metodo HTTP</th><th>Endpoint</th><th>Status</th></tr>
 *   <tr><td>Create</td><td>POST</td><td>/api/usuarios</td><td>201 Created</td></tr>
 *   <tr><td>Read (todos)</td><td>GET</td><td>/api/usuarios</td><td>200 OK</td></tr>
 *   <tr><td>Read (um)</td><td>GET</td><td>/api/usuarios/{id}</td><td>200 OK</td></tr>
 *   <tr><td>Update</td><td>PUT</td><td>/api/usuarios/{id}</td><td>200 OK</td></tr>
 *   <tr><td>Delete</td><td>DELETE</td><td>/api/usuarios/{id}</td><td>204 No Content</td></tr>
 * </table>
 *
 * <p><b>Validacao declarativa:</b> a anotacao {@code @Valid} (antes de
 * {@code @RequestBody}) faz o Spring validar as anotacoes do DTO
 * ({@code @NotBlank}, {@code @Email}, etc.). Se algo falhar, o
 * {@code GlobalExceptionHandler} devolve HTTP 400 automaticamente — o
 * controller nem precisa saber disso.</p>
 *
 * <p><b>CORS:</b> aqui NAO usamos {@code @CrossOrigin} no metodo/classe.
 * Em vez disso configuramos uma regra GLOBAL em
 * {@link br.ueg.trindade.braully_web2_fullstack.config.CorsConfig}, que o
 * professor citou na Aula 04 como alternativa ("ou uma CorsConfiguration
 * global"). Isso evita repetir a anotacao em cada controller.</p>
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    /** Service injetado pelo Spring — unica dependencia desta classe. */
    private final UsuarioService usuarioService;

    /**
     * Construtor com injecao de dependencia (IoC / Inversion of Control).
     * <p>O Spring injeta o {@code UsuarioService} automaticamente porque
     * ele esta marcado com {@code @Service}.</p>
     *
     * @param usuarioService camada de servico de Usuario
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * LISTAR todos os usuarios — {@code GET /api/usuarios}
     *
     * @return lista de usuarios (JSON array)
     */
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios); // 200 OK
    }

    /**
     * BUSCAR um usuario pelo id — {@code GET /api/usuarios/{id}}
     *
     * <p><b>{@code @PathVariable}</b> indica que o valor vem do caminho da
     * URL (ex.: /api/usuarios/5 → id = 5).</p>
     *
     * @param id identificador do usuario
     * @return usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario); // 200 OK
    }

    /**
     * CRIAR um novo usuario — {@code POST /api/usuarios}
     *
     * <p><b>{@code @RequestBody}</b> indica que o JSON do corpo da requisicao
     * sera convertido automaticamente em {@link UsuarioRequest} (Jackson).</p>
     *
     * <p><b>{@code @Valid}</b> ativa a validacao declarativa do DTO.</p>
     *
     * <p><b>Resposta 201 Created</b> e o codigo HTTP correto para quando um
     * recurso e criado — melhor pratica REST do que devolver 200.</p>
     *
     * @param dados dados do usuario vindos do corpo JSON
     * @return usuario criado (com o id gerado pelo banco)
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioRequest dados) {
        UsuarioDTO criado = usuarioService.criar(dados);
        // 201 Created indica que o recurso foi criado com sucesso
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    /**
     * ATUALIZAR um usuario — {@code PUT /api/usuarios/{id}}
     *
     * <p><b>Regra do REST:</b> o id vem na URL (PathVariable), <b>nunca</b>
     * no corpo da requisicao. Por isso usamos um DTO diferente do POST
     * ({@link UsuarioAtualizacaoRequest}), que nao possui o campo senha.</p>
     *
     * @param id    identificador do usuario a ser atualizado
     * @param dados novos dados vindos do corpo JSON
     * @return usuario atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody UsuarioAtualizacaoRequest dados) {
        UsuarioDTO atualizado = usuarioService.atualizar(id, dados);
        return ResponseEntity.ok(atualizado); // 200 OK
    }

    /**
     * EXCLUIR um usuario — {@code DELETE /api/usuarios/{id}}
     *
     * <p><b>HTTP 204 No Content</b> e o codigo correto para exclusao bem
     * sucedida: a operacao deu certo e NAO ha corpo na resposta.</p>
     *
     * <p><b>Obs.:</b> {@code DELETE} e {@code GET} nao possuem corpo de
     * requisicao — por isso o id sempre vem na URL via
     * {@code @PathVariable} (nunca via {@code @RequestBody}).</p>
     *
     * @param id identificador do usuario a ser removido
     * @return resposta 204 sem corpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
