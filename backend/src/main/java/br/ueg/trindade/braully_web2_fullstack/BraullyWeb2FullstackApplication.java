package br.ueg.trindade.braully_web2_fullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * <p><b>O que esta classe faz:</b> ela é o "ponto de partida" do sistema.
 * Quando você roda a aplicação (via IDE, {@code ./mvnw spring-boot:run} ou
 * {@code java -jar ...}), o Spring executa o método {@link #main(String[])}.</p>
 *
 * <p><b>Anotações importantes:</b></p>
 * <ul>
 *   <li>{@code @SpringBootApplication} — atalho que reúne 3 anotações:
 *       <ul>
 *         <li>{@code @Configuration} — marca a classe como fonte de beans;</li>
 *         <li>{@code @EnableAutoConfiguration} — configura automaticamente
 *             tudo que o Spring precisa (Tomcat, JPA, Jackson, H2...) com base
 *             nas dependências do pom.xml;</li>
 *         <li>{@code @ComponentScan} — procura e registra todos os beans
 *             (controllers, services, repositories) do pacote deste pacote
 *             raiz para baixo.</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <p><b>Arquitetura em camadas (Aula 06):</b> por causa do
 * {@code @ComponentScan}, toda a aplicação é organizada nas subpastas:</p>
 * <pre>
 *   br.ueg.trindade.braully_web2_fullstack/
 *   ├── controller/   → recebe requisições HTTP e devolve respostas
 *   ├── service/      → concentra regras de negócio e validações
 *   ├── repository/   → acesso ao banco de dados (Spring Data JPA)
 *   ├── model/        → entidades JPA (tabelas do banco)
 *   ├── dto/          → objetos de transferência de dados (JSON exposto)
 *   ├── exception/    → exceções customizadas e tratamento global
 *   └── config/       → configurações (CORS, etc.)
 * </pre>
 */
@SpringBootApplication
public class BraullyWeb2FullstackApplication {

    /**
     * Método principal — ponto de entrada da JVM para a aplicação.
     *
     * @param args argumentos de linha de comando (ignorados por enquanto)
     */
    public static void main(String[] args) {
        // SpringApplication.run() inicia o container do Spring e o servidor
        // HTTP embutido (Tomcat), normalmente na porta 8080.
        SpringApplication.run(BraullyWeb2FullstackApplication.class, args);
    }

}
