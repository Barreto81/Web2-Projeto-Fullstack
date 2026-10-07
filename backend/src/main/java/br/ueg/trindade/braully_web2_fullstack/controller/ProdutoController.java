package br.ueg.trindade.braully_web2_fullstack.controller;

import br.ueg.trindade.braully_web2_fullstack.dto.ProdutoDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.ProdutoRequest;
import br.ueg.trindade.braully_web2_fullstack.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER DE PRODUTO — CAMADA HTTP (entidade de interesse do projeto)
 *
 * <p>Replicado a partir do padrao das demais entidades conforme os
 * exercicios das Aulas 02, 03, 05 e 06:</p>
 * <ol>
 *   <li>Aula 02 — criar a classe e o endpoint de interesse;</li>
 *   <li>Aula 03 — persistir em banco e criar o Repository;</li>
 *   <li>Aula 05 — completar o CRUD (PUT e DELETE);</li>
 *   <li>Aula 06 — organizar em camadas (Controller → Service → Repository)
 *       e adicionar regra de negocio no Service.</li>
 * </ol>
 *
 * <p><b>Endpoints expostos:</b></p>
 * <ul>
 *   <li>{@code GET    /api/produtos}     — listar todos;</li>
 *   <li>{@code GET    /api/produtos/{id}} — buscar por id;</li>
 *   <li>{@code POST   /api/produtos}     — criar;</li>
 *   <li>{@code PUT    /api/produtos/{id}} — atualizar;</li>
 *   <li>{@code DELETE /api/produtos/{id}} — excluir.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    /** Service injetado pelo Spring (IoC). */
    private final ProdutoService produtoService;

    /**
     * Construtor com injecao de dependencia.
     *
     * @param produtoService camada de servico de Produto
     */
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * LISTAR todos os produtos — {@code GET /api/produtos}
     *
     * @return lista de produtos (JSON array)
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos); // 200 OK
    }

    /**
     * BUSCAR um produto pelo id — {@code GET /api/produtos/{id}}
     *
     * @param id identificador do produto
     * @return produto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto); // 200 OK
    }

    /**
     * CRIAR um novo produto — {@code POST /api/produtos}
     *
     * @param dados dados do produto vindos do corpo JSON
     * @return produto criado (com id gerado pelo banco)
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoRequest dados) {
        ProdutoDTO criado = produtoService.criar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado); // 201 Created
    }

    /**
     * ATUALIZAR um produto — {@code PUT /api/produtos/{id}}
     *
     * @param id    identificador do produto
     * @param dados novos dados vindos do corpo JSON
     * @return produto atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody ProdutoRequest dados) {
        ProdutoDTO atualizado = produtoService.atualizar(id, dados);
        return ResponseEntity.ok(atualizado); // 200 OK
    }

    /**
     * EXCLUIR um produto — {@code DELETE /api/produtos/{id}}
     *
     * @param id identificador do produto
     * @return resposta 204 sem corpo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
