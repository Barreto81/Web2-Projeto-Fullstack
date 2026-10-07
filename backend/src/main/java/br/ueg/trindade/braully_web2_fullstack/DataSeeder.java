package br.ueg.trindade.braully_web2_fullstack;

import br.ueg.trindade.braully_web2_fullstack.model.Permissao;
import br.ueg.trindade.braully_web2_fullstack.model.Produto;
import br.ueg.trindade.braully_web2_fullstack.model.Usuario;
import br.ueg.trindade.braully_web2_fullstack.repository.PermissaoRepository;
import br.ueg.trindade.braully_web2_fullstack.repository.ProdutoRepository;
import br.ueg.trindade.braully_web2_fullstack.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * DATA SEEDER — CARGA INICIAL DE DADOS DE EXEMPLO
 *
 * <p><b>Para que serve?</b> A cada vez que a aplicacao sobe com um banco
 * novo (ou vazio), esta classe cadastra automaticamente dados de exemplo,
 * para que voce ja possa testar os endpoints e ver o React funcionando
 * sem precisar cadastrar nada manualmente.</p>
 *
 * <p><b>Como funciona?</b> A classe implementa {@link CommandLineRunner}:
 * interface do Spring que executa o metodo {@link #run(String...)} uma vez
 * unica, <b>apos</b> toda a aplicacao estar inicializada.</p>
 *
 * <p><b>Por que nao usar data.sql?</b> Por causa da estrategia
 * {@code ddl-auto=update}: no primeiro boot o Hibernate cria as tabelas e
 * no segundo boot elas ja existem com dados — gerando conflito de chave
 * primaria. Aqui verificamos antes se a tabela esta vazia, tornando o
 * processo <b>idempotente</b> (rodar N vezes gera o mesmo resultado).</p>
 *
 * <p><b>Log:</b> usamos {@link Logger} para imprimir mensagens no console
 * informando que os dados de exemplo foram carregados.</p>
 */
@Component
public class DataSeeder implements CommandLineRunner {

    /** Logger da classe — imprime informacoes no console do servidor. */
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    /** Repositories injetados pelo Spring (IoC). */
    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final ProdutoRepository produtoRepository;

    /**
     * Construtor com injecao de dependencia.
     *
     * @param usuarioRepository    repository de Usuario
     * @param permissaoRepository  repository de Permissao
     * @param produtoRepository    repository de Produto
     */
    public DataSeeder(UsuarioRepository usuarioRepository,
                      PermissaoRepository permissaoRepository,
                      ProdutoRepository produtoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.produtoRepository = produtoRepository;
    }

    /**
     * Metodo executado automaticamente pelo Spring apos a inicializacao.
     *
     * @param args argumentos de linha de comando (nao utilizados aqui)
     */
    @Override
    public void run(String... args) {
        // Sementra apenas se as tabelas estiverem vazias (primeira execucao)
        if (usuarioRepository.count() == 0
                && permissaoRepository.count() == 0
                && produtoRepository.count() == 0) {

            log.info(">>> Banco vazio detectado. Carregando dados de exemplo...");

            carregarPermissoes();
            carregarUsuarios();
            carregarProdutos();

            log.info(">>> Dados de exemplo carregados com sucesso!");
            log.info(">>> Teste a API em: http://localhost:8080/api");
            log.info(">>> Console H2 em:  http://localhost:8080/h2-console");
        } else {
            log.info(">>> Banco ja possui dados. Carga de exemplo ignorada.");
        }
    }

    /**
     * Cria permissoes de exemplo (id, nome, descricao).
     */
    private void carregarPermissoes() {
        List<Permissao> permissoes = List.of(
                new Permissao("ADMIN", "Acesso total ao sistema"),
                new Permissao("OPERADOR", "Gerencia cadastros e operacoes do dia a dia"),
                new Permissao("CONSULTA", "Somente visualizacao de dados")
        );

        // prepararParaPersistencia() / setNome() ja foram chamados nos construtores
        permissaoRepository.saveAll(permissoes);
        log.info("  - {} permissoes cadastradas", permissoes.size());
    }

    /**
     * Cria usuarios de exemplo (nome, username, senha, email).
     */
    private void carregarUsuarios() {
        List<Usuario> usuarios = List.of(
                new Usuario("Administrador do Sistema", "admin", "admin123", "admin@web2.com"),
                new Usuario("Braully Rocha da Silva", "braully", "prof2026", "braully@ueg.br"),
                new Usuario("Maria da Silva Santos", "maria.santos", "maria123", "maria@web2.com"),
                new Usuario("Joao Souza Oliveira", "joao.souza", "joao123", "joao@web2.com")
        );

        usuarios.forEach(Usuario::prepararParaPersistencia);
        usuarioRepository.saveAll(usuarios);
        log.info("  - {} usuarios cadastrados", usuarios.size());
    }

    /**
     * Cria produtos de exemplo.
     * <p>Observe o uso de {@link BigDecimal} para o preco — o tipo correto
     * para valores monetarios (evita erros de arredondamento do double).</p>
     */
    private void carregarProdutos() {
        List<Produto> produtos = List.of(
                new Produto("Notebook Gamer", new BigDecimal("4599.90"), 10,
                        "Eletronicos", "Notebook com processador de alta performance e placa de video dedicada"),
                new Produto("Mouse Gamer RGB", new BigDecimal("149.90"), 50,
                        "Eletronicos", "Mouse optico com iluminacao RGB e sensor de 16000 DPI"),
                new Produto("Teclado Mecanico", new BigDecimal("299.90"), 30,
                        "Eletronicos", "Teclado mecanico switches azuis, layout ABNT2"),
                new Produto("Livro - Clean Code", new BigDecimal("79.90"), 25,
                        "Livros", "Manual de estilo para o desenvolvimento de software orientado a objetos"),
                new Produto("Fone de Ouvido Bluetooth", new BigDecimal("199.90"), 40,
                        "Eletronicos", "Fone wireless com cancelamento de ruido e bateria de 30h")
        );

        produtoRepository.saveAll(produtos);
        log.info("  - {} produtos cadastrados", produtos.size());
    }
}
