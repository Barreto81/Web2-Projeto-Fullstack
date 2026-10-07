// =============================================================================
// api.ts — CLIENTE HTTP (Axios)
// =============================================================================
// Centraliza toda a configuracao das chamadas HTTP para o back-end Spring Boot.
// Em vez de repetir a URL base em cada componente, usamos uma instancia
// unica do Axios com a baseURL ja configurada.
//
// Fluxo das aulas (Aula 04):
//   React (http://localhost:5173)
//        │
//        │  axios.get("/usuarios")   ← relativo, a baseURL completa a URL
//        ▼
//   http://localhost:8080/api/usuarios   (Spring Boot)
// =============================================================================
import axios from "axios";

/**
 * Instancia unica do Axios.
 *
 * `baseURL`: endereço do back-end Spring Boot. Qualquer chamada feita com
 * `api.get("/usuarios")` sera convertida em
 * `GET http://localhost:8080/api/usuarios`.
 *
 * Obs.: a porta 8080 e a padrao configurada em `application.properties`
 * (server.port=8080) do projeto backend. Se a porta 8080 estiver ocupada
 * na sua maquina, crie um arquivo `.env.local` na pasta `frontend` com:
 *
 *   VITE_API_URL=http://localhost:8081/api
 *
 * e suba o backend com `java -jar ... --server.port=8081`.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ?? "http://localhost:8080/api",
});

export default api;
