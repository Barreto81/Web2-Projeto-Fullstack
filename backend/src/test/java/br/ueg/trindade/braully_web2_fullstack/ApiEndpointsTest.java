package br.ueg.trindade.braully_web2_fullstack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * TESTE DE INTEGRACAO — ENDPOINTS DA API (MockMvc)
 *
 * <p>Estes testes simulam <b>requisicoes HTTP reais</b> usando
 * {@code MockMvc} — ferramenta do Spring Test que exercita os controllers
 * <b>sem subir o servidor Tomcat</b>. Isso deixa os testes rapidos e
 * automaticos.</p>
 *
 * <p><b>Fluxo exercitado:</b></p>
 * <ul>
 *   <li>{@code GET /api} → deve devolver 200 e o banner da API;</li>
 *   <li>{@code GET /api/usuarios} → deve devolver 200 e um array JSON;</li>
 *   <li>{@code POST /api/usuarios} → deve devolver 201 Created;</li>
 *   <li>{@code POST /api/usuarios} sem dados → deve devolver 400 Bad Request
 *       (validacao declarativa);</li>
 *   <li>{@code GET /api/usuarios/999999} → deve devolver 404 Not Found;</li>
 *   <li>{@code GET /api/permissoes} → deve devolver 200;</li>
 *   <li>{@code GET /api/produtos} → deve devolver 200.</li>
 * </ul>
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApiEndpointsTest {

    /** MockMvc — cliente HTTP em memoria para testar os endpoints. */
    @Autowired
    private MockMvc mockMvc;

    /**
     * O banner da API deve responder com HTTP 200 e conter o nome da aplicacao.
     */
    @Test
    @DisplayName("GET /api deve responder com o banner da aplicacao")
    void deveRetornarBannerDaApi() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aplicacao").isNotEmpty())
                .andExpect(jsonPath("$.status").value("ONLINE"));
    }

    /**
     * GET /api/usuarios deve devolver 200 com um array JSON (mesmo vazio).
     */
    @Test
    @DisplayName("GET /api/usuarios deve responder 200 com array JSON")
    void deveListarUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * GET /api/permissoes deve devolver 200 com um array JSON.
     */
    @Test
    @DisplayName("GET /api/permissoes deve responder 200 com array JSON")
    void deveListarPermissoes() throws Exception {
        mockMvc.perform(get("/api/permissoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * GET /api/produtos deve devolver 200 com um array JSON.
     */
    @Test
    @DisplayName("GET /api/produtos deve responder 200 com array JSON")
    void deveListarProdutos() throws Exception {
        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    /**
     * POST /api/usuarios com dados validos deve devolver 201 Created.
     */
    @Test
    @DisplayName("POST /api/usuarios deve responder 201 Created")
    void deveCriarUsuarioViaApi() throws Exception {
        String corpoJson = """
                {
                    "nome": "Usuario MockMvc",
                    "username": "mockmvc.user",
                    "senha": "senha123",
                    "email": "mockmvc@web2.com"
                }
                """;

        mockMvc.perform(post("/api/usuarios")
                        .contentType("application/json")
                        .content(corpoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Usuario MockMvc"))
                .andExpect(jsonPath("$.email").value("mockmvc@web2.com"));
    }

    /**
     * POST /api/usuarios sem nome deve devolver 400 e o detalhamento do campo.
     */
    @Test
    @DisplayName("POST /api/usuarios sem nome deve responder 400 com erro de campo")
    void deveValidarCamposObrigatorios() throws Exception {
        String corpoInvalido = """
                {
                    "username": "sem.nome",
                    "senha": "senha123",
                    "email": "semnome@web2.com"
                }
                """;

        mockMvc.perform(post("/api/usuarios")
                        .contentType("application/json")
                        .content(corpoInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos").isArray());
    }

    /**
     * GET /api/usuarios/{id} inexistente deve devolver 404 Not Found.
     */
    @Test
    @DisplayName("GET /api/usuarios/999999 deve responder 404 Not Found")
    void deveDevolver404QuandoIdNaoExiste() throws Exception {
        mockMvc.perform(get("/api/usuarios/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").isNotEmpty());
    }

    /**
     * GET /api/usuarios/abc (tipo invalido) deve devolver 400 Bad Request
     * em vez de estourar um erro 500.
     */
    @Test
    @DisplayName("GET /api/usuarios/abc deve responder 400 (tipo invalido)")
    void deveTratarTipoDeParametroInvalido() throws Exception {
        mockMvc.perform(get("/api/usuarios/abc"))
                .andExpect(status().isBadRequest());
    }

    /**
     * POST /api/produtos com preco negativo deve devolver 400.
     */
    @Test
    @DisplayName("POST /api/produtos com preco negativo deve responder 400")
    void deveValidarPrecoPositivo() throws Exception {
        String corpoInvalido = """
                {
                    "nome": "Produto Invalido",
                    "preco": -10.50,
                    "quantidade": 5,
                    "categoria": "Teste"
                }
                """;

        mockMvc.perform(post("/api/produtos")
                        .contentType("application/json")
                        .content(corpoInvalido))
                .andExpect(status().isBadRequest());
    }

    /**
     * DELETE /api/produtos/{id} inexistente deve devolver 404.
     */
    @Test
    @DisplayName("DELETE /api/produtos/999999 deve responder 404")
    void deveDevolver404AoExcluirIdInexistente() throws Exception {
        mockMvc.perform(delete("/api/produtos/999999"))
                .andExpect(status().isNotFound());
    }
}
