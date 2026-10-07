package br.ueg.trindade.braully_web2_fullstack.controller;

import br.ueg.trindade.braully_web2_fullstack.dto.PermissaoDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.PermissaoRequest;
import br.ueg.trindade.braully_web2_fullstack.service.PermissaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER DE PERMISSAO — CAMADA HTTP (exercicios das Aulas 02, 05 e 06)
 *
 * <p>Criado para atender ao exercicio da <b>Aula 02</b>
 * ("criar o endpoint /permissoes com os metodos GET") e refatorado na
 * <b>Aula 06</b> para o padrao Controller → Service → Repository.</p>
 *
 * <p><b>Endpoints expostos:</b></p>
 * <ul>
 *   <li>{@code GET    /api/permissoes}     — listar todas;</li>
 *   <li>{@code GET    /api/permissoes/{id}} — buscar por id;</li>
 *   <li>{@code POST   /api/permissoes}     — criar;</li>
 *   <li>{@code PUT    /api/permissoes/{id}} — atualizar;</li>
 *   <li>{@code DELETE /api/permissoes/{id}} — excluir.</li>
 * </ul>
 *
 * <p>Assim como no {@code UsuarioController}, esta classe <b>nao</b> acessa
 * o repository diretamente — toda a logica e o acesso ao banco sao
 * responsabilidade do {@link PermissaoService}.</p>
 */
@RestController
@RequestMapping("/api/permissoes")
public class PermissaoController {

    /** Service injetado pelo Spring (IoC). */
    private final PermissaoService permissaoService;

    /**
     * Construtor com injecao de dependencia.
     *
     * @param permissaoService camada de servico de Permissao
     */
    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    /**
     * LISTAR todas as permissoes — {@code GET /api/permissoes}
     *
     * @return lista de permissoes (JSON array)
     */
    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> listarTodas() {
        List<PermissaoDTO> permissoes = permissaoService.listarTodas();
        return ResponseEntity.ok(permissoes); // 200 OK
    }

    /**
     * BUSCAR uma permissao pelo id — {@code GET /api/permissoes/{id}}
     *
     * @param id identificador da permissao
     * @return permissao encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissaoDTO> buscarPorId(@PathVariable Long id) {
        PermissaoDTO permissao = permissaoService.buscarPorId(id);
        return ResponseEntity.ok(permissao); // 200 OK
    }

    /**
     * CRIAR uma nova permissao — {@code POST /api/permissoes}
     *
     * @param dados dados da permissao vindos do corpo JSON
     * @return permissao criada (com id gerado pelo banco)
     */
    @PostMapping
    public ResponseEntity<PermissaoDTO> criar(@Valid @RequestBody PermissaoRequest dados) {
        PermissaoDTO criada = permissaoService.criar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada); // 201 Created
    }

    /**
     * ATUALIZAR uma permissao — {@code PUT /api/permissoes/{id}}
     *
     * @param id    identificador da permissao
     * @param dados novos dados vindos do corpo JSON
     * @return permissao atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermissaoDTO> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody PermissaoRequest dados) {
        PermissaoDTO atualizada = permissaoService.atualizar(id, dados);
        return ResponseEntity.ok(atualizada); // 200 OK
    }

    /**
     * EXCLUIR uma permissao — {@code DELETE /api/permissoes/{id}}
     *
     * @param id identificador da permissao
     * @return resposta 204 sem corpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        permissaoService.excluir(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
