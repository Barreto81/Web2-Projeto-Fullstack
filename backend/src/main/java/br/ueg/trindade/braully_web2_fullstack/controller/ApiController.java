package br.ueg.trindade.braully_web2_fullstack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CONTROLLER RAIZ — INDICE DA API
 *
 * <p>Endpoint simples em {@code GET /api} que devolve um "banner" com as
 * informacoes basicas da aplicacao. e util para:</p>
 * <ul>
 *   <li>confirmar que a API esta no ar;</li>
 *   <li>descobrir os endpoints disponiveis ao abrir o navegador;</li>
 *   <li>servir como "pagina inicial" da API.</li>
 * </ul>
 *
 * <p><b>Obs.:</b> esta classe esta na camada <b>Controller</b>, sem
 * Service/Repository — porque nao ha regra de negocio nem banco envolvido.
 * Apenas monta e devolve informacoes estaticas.</p>
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    /**
     * Devolve um mapa com informacoes da API.
     *
     * @return JSON com nome, versao, status e a lista de endpoints
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> indice() {
        Map<String, Object> corpo = new LinkedHashMap<>();

        corpo.put("aplicacao", "Programação Web II — Spring Boot + React");
        corpo.put("versao", "1.0.0");
        corpo.put("status", "ONLINE");
        corpo.put("dataHora", LocalDateTime.now().toString());
        corpo.put("arquitetura", "Controller → Service → Repository → Banco H2");

        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("usuarios",    "GET/POST /api/usuarios · GET/PUT/DELETE /api/usuarios/{id}");
        endpoints.put("permissoes",  "GET/POST /api/permissoes · GET/PUT/DELETE /api/permissoes/{id}");
        endpoints.put("produtos",    "GET/POST /api/produtos · GET/PUT/DELETE /api/produtos/{id}");
        endpoints.put("saude",       "GET /actuator/health");
        endpoints.put("h2-console",  "http://localhost:8080/h2-console (JDBC URL: jdbc:h2:file:./database.db, usuário: sa, senha vazia)");
        corpo.put("endpoints", endpoints);

        return ResponseEntity.ok(corpo);
    }
}
