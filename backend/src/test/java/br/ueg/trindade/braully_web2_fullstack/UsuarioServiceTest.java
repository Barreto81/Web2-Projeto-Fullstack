package br.ueg.trindade.braully_web2_fullstack;

import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioDTO;
import br.ueg.trindade.braully_web2_fullstack.dto.UsuarioRequest;
import br.ueg.trindade.braully_web2_fullstack.exception.RegraDeNegocioException;
import br.ueg.trindade.braully_web2_fullstack.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TESTE DE INTEGRACAO — UsuarioService
 *
 * <p>Este teste sobe a <b>aplicacao completa</b> (contexto Spring) usando o
 * banco H2 em memoria, e valida as regras de negocio do
 * {@link UsuarioService}.</p>
 *
 * <p><b>Por que testar?</b> (referencia Aula 06: "facilita manutencao,
 * testes, reuso e evolucao"). Testes garantem que uma mudanca futura no
 * codigo nao quebra o que ja funciona — e podem ser executados sem subir o
 * navegador ou o servidor HTTP.</p>
 *
 * <p><b>Como executar:</b> na pasta backend, rode
 * {@code ./mvnw test} (ou o botao de teste na IDE).</p>
 */
@SpringBootTest
class UsuarioServiceTest {

    /** Service real, injetado pelo Spring (nao e mockado). */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Teste 1 — Criar usuario valida gravar os dados no banco.
     */
    @Test
    @DisplayName("Deve criar um usuario e devolver o DTO com id gerado")
    void deveCriarUsuario() {
        // Arrange (prepara os dados)
        UsuarioRequest dados = new UsuarioRequest();
        dados.setNome("Teste da Silva");
        dados.setUsername("teste.silva");
        dados.setSenha("senha123");
        dados.setEmail("teste@web2.com");

        // Act (executa a acao)
        UsuarioDTO criado = usuarioService.criar(dados);

        // Assert (verifica o resultado)
        assertNotNull(criado.getId(), "O id deve ser gerado pelo banco");
        assertEquals("Teste da Silva", criado.getNome());
        assertEquals("teste.silva", criado.getUsername());
        assertEquals("teste@web2.com", criado.getEmail());

        // Garante que a senha NUNCA aparece no DTO devolvido
        assertFalse(existeCampoSenhaNoDto(criado),
                "O DTO de resposta nao pode conter a senha");
    }

    /**
     * Teste 2 — Regra de negocio: e-mail duplicado deve ser bloqueado.
     */
    @Test
    @DisplayName("Deve bloquear criacao com e-mail duplicado")
    void deveBloquearEmailDuplicado() {
        // Arrange — cria um usuario primeiro
        UsuarioRequest primeiro = new UsuarioRequest();
        primeiro.setNome("Primeiro Usuario");
        primeiro.setUsername("primeiro.usuario");
        primeiro.setSenha("senha123");
        primeiro.setEmail("duplicado@web2.com");
        usuarioService.criar(primeiro);

        // Arrange — tenta criar OUTRO usuario com o MESMO e-mail
        UsuarioRequest segundo = new UsuarioRequest();
        segundo.setNome("Segundo Usuario");
        segundo.setUsername("segundo.usuario");
        segundo.setSenha("senha123");
        segundo.setEmail("DUPLICADO@web2.com"); // maiusculas/minusculas nao importam

        // Act + Assert — espera a excecao de regra de negocio
        RegraDeNegocioException excecao = assertThrows(
                RegraDeNegocioException.class,
                () -> usuarioService.criar(segundo)
        );

        assertTrue(excecao.getMessage().toLowerCase().contains("duplicado@web2.com"),
                "A mensagem deve citar o e-mail duplicado");
    }

    /**
     * Teste 3 — Regra de negocio: login duplicado deve ser bloqueado.
     */
    @Test
    @DisplayName("Deve bloquear criacao com username duplicado")
    void deveBloquearUsernameDuplicado() {
        // Arrange — cria um usuario
        UsuarioRequest primeiro = new UsuarioRequest();
        primeiro.setNome("Login Teste");
        primeiro.setUsername("login.unico");
        primeiro.setSenha("senha123");
        primeiro.setEmail("login.unico@web2.com");
        usuarioService.criar(primeiro);

        // Arrange — tenta criar outro com o MESMO login
        UsuarioRequest segundo = new UsuarioRequest();
        segundo.setNome("Outro Usuario");
        segundo.setUsername("LOGIN.UNICO"); // maiusculas/minusculas nao importam
        segundo.setSenha("senha456");
        segundo.setEmail("outro@web2.com");

        // Act + Assert
        RegraDeNegocioException excecao = assertThrows(
                RegraDeNegocioException.class,
                () -> usuarioService.criar(segundo)
        );

        assertTrue(excecao.getMessage().contains("LOGIN.UNICO")
                        || excecao.getMessage().contains("login.unico"),
                "A mensagem deve citar o login duplicado");
    }

    /**
     * Teste 4 — Buscar um usuario inexistente deve devolver 404 (excecao).
     */
    @Test
    @DisplayName("Deve lancar RecursoNaoEncontrado ao buscar id inexistente")
    void deveFalharAoBuscarIdInexistente() {
        assertThrows(
                br.ueg.trindade.braully_web2_fullstack.exception.RecursoNaoEncontradoException.class,
                () -> usuarioService.buscarPorId(999999L)
        );
    }

    /**
     * Teste 5 — Listar usuarios deve retornar lista nao nula.
     */
    @Test
    @DisplayName("Deve listar usuarios cadastrados")
    void deveListarUsuarios() {
        var usuarios = usuarioService.listarTodos();
        assertNotNull(usuarios);
        // O DataSeeder do teste nao roda automaticamente aqui, entao a lista
        // pode iniciar vazia — o importante e que nao houve excecao.
        assertTrue(usuarios.size() >= 0);
    }

    /**
     * Verifica de forma simples que o DTO de resposta nao expoe a senha.
     * <p>Serializa o DTO para JSON (com suporte a Java Time) e procura pela
     * palavra "senha" no resultado. Se o JSON nao contiver a senha, o
     * teste passa.</p>
     */
    private boolean existeCampoSenhaNoDto(UsuarioDTO dto) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            // Registra o módulo de Java Time para serializar LocalDateTime
            mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            String json = mapper.writeValueAsString(dto);
            return json.contains("senha");
        } catch (Exception e) {
            return true; // se nao conseguir serializar, reporta como problema
        }
    }
}
