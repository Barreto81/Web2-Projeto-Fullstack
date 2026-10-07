package br.ueg.trindade.braully_web2_fullstack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CONFIGURACAO DE CORS (Aula 04)
 *
 * <p><b>O que e CORS?</b><br>
 * <i>Cross-Origin Resource Sharing</i> e um mecanismo de seguranca do
 * navegador. Ele existe porque o React (porta 5173) e o Spring Boot
 * (porta 8080) rodam em <b>origens diferentes</b>. Por padrao, o navegador
 * <b>bloqueia</b> o front-end de falar com o back-end de outra porta —
 * exatamente o erro "CORS policy" que aparece no console.</p>
 *
 * <p><b>Solucao:</b> avisar ao Spring que ele <b>aceita requisicoes</b>
 * vindo da origem do front-end.</p>
 *
 * <p><b>Duas formas de configurar (Aula 04):</b></p>
 * <ol>
 *   <li><b>{@code @CrossOrigin} na classe/metodo do controller</b> —
 *       como mostrado nas demonstracoes das aulas;</li>
 *   <li><b>Configuracao global</b> (esta classe) — como citado no slide:
 *       "Configurar {@code @CrossOrigin} no UsuarioController (ou uma
 *       CorsConfiguration global)".</li>
 * </ol>
 *
 * <p>Escolhemos a <b>opcao 2</b> porque:</p>
 * <ul>
 *   <li>a regra e unica para todos os endpoints (menos repeticao);</li>
 *   <li>ao adicionar novos controllers, o CORS ja vem configurado;</li>
 *   <li>e mais proximo de como projetos reais sao estruturados.</li>
 * </ul>
 *
 * <p><b>{@code @Configuration}:</b> marca a classe como fonte de beans de
 * configuracao do Spring.</p>
 */
@Configuration
public class CorsConfig {

    /**
     * Registra um {@code WebMvcConfigurer} com as regras de CORS.
     *
     * <p><b>Regras aplicadas:</b></p>
     * <ul>
     *   <li>{@code allowedOrigins} — quais portas de origem podem chamar a API
     *       (5173 = Vite default; 3000 = Create React App; 4200 = Angular);</li>
     *   <li>{@code allowedMethods} — quais verbos HTTP sao liberados;</li>
     *   <li>{@code allowedHeaders} — quais cabecalhos o front pode enviar
     *       (Content-Type e necessario para enviar JSON);</li>
     *   <li>{@code allowCredentials} — se o front pode usar cookies/autenticacao
     *       (nao usado neste projeto, mas comum em sistemas com login);</li>
     * </ul>
     *
     * @return configurador de MVC com as regras de CORS
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // vale para todos os endpoints
                        .allowedOrigins(
                                "http://localhost:5173", // Vite (padrao do projeto React)
                                "http://localhost:3000", // Create React App
                                "http://localhost:4200"  // Angular (caso use)
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .maxAge(3600); // guarda a resposta do pre-flight por 1h (performance)
            }
        };
    }
}
