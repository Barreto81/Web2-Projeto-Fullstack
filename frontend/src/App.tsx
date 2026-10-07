// =============================================================================
// App.tsx — APLICACAO REACT (definicao das ROTAS)
// =============================================================================
// A Aula 06 diz: "App.tsx deixa de conter a logica da tela — apenas monta a
// pagina. Na Aula 08, ele passara a definir as rotas com React Router."
//
// Aqui implementamos o React Router com as rotas do sistema:
//   /              → Home (banner da API)
//   /usuarios      → UsuariosPage
//   /permissoes    → PermissoesPage
//   /produtos      → ProdutosPage
//   *              → pagina nao encontrada (404)
//
// O <Menu /> fica fixo no topo em todas as telas.
// =============================================================================
import { Routes, Route, Link } from "react-router-dom";
import Menu from "./components/Menu";
import UsuariosPage from "./pages/UsuariosPage";
import PermissoesPage from "./pages/PermissoesPage";
import ProdutosPage from "./pages/ProdutosPage";

/**
 * Componente raiz da aplicacao.
 *
 * Estrutura:
 *   1. <Menu />       → navegacao entre as telas (sempre visivel)
 *   2. <main>         → area onde as paginas sao renderizadas conforme a rota
 *   3. <footer>       → rodape com informacoes do projeto
 */
function App() {
  return (
    <div className="app">
      {/* Menu de navegacao fixo */}
      <Menu />

      {/* Area principal: o Router escolhe qual pagina renderizar */}
      <main className="app-main">
        <Routes>
          {/* Rota raiz — tela inicial com o banner da API */}
          <Route
            path="/"
            element={
              <div className="pagina">
                <header className="pagina-cabecalho">
                  <h1>Programacao Web II</h1>
                  <p className="pagina-subtitulo">
                    Projeto completo · Spring Boot + React · Arquitetura em camadas
                  </p>
                </header>

                <section className="cartao">
                  <h2>O que este projeto demonstra</h2>
                  <p>
                    Backend em <strong>Spring Boot</strong> organizado na arquitetura em
                    camadas <strong>Controller → Service → Repository → Banco H2</strong>,
                    com CRUD completo de <strong>Usuario</strong>,{" "}
                    <strong>Permissao</strong> e <strong>Produto</strong>.
                  </p>
                  <p>
                    Front-end em <strong>React + TypeScript</strong> consumindo a API via{" "}
                    <strong>Axios</strong>, com formulacoes controladas, rotas e estados de
                    carregamento/erro.
                  </p>
                </section>

                <section className="cartao">
                  <h2>Endpoints da API</h2>
                  <ul className="endpoints">
                    <li>
                      <code>GET/POST</code> <code>/api/usuarios</code>
                    </li>
                    <li>
                      <code>GET/PUT/DELETE</code> <code>/api/usuarios/&#123;id&#125;</code>
                    </li>
                    <li>
                      <code>GET/POST</code> <code>/api/permissoes</code>
                    </li>
                    <li>
                      <code>GET/PUT/DELETE</code> <code>/api/permissoes/&#123;id&#125;</code>
                    </li>
                    <li>
                      <code>GET/POST</code> <code>/api/produtos</code>
                    </li>
                    <li>
                      <code>GET/PUT/DELETE</code> <code>/api/produtos/&#123;id&#125;</code>
                    </li>
                    <li>
                      <code>GET</code> <code>/actuator/health</code> (saude da aplicacao)
                    </li>
                    <li>
                      Console H2:{" "}
                      <a href="http://localhost:8080/h2-console" target="_blank" rel="noreferrer">
                        http://localhost:8080/h2-console
                      </a>
                    </li>
                  </ul>
                </section>

                <section className="cartao">
                  <h2>Comece por aqui</h2>
                  <p>
                    Use o menu no topo para acessar as telas de{" "}
                    <Link to="/usuarios">Usuarios</Link>, <Link to="/permissoes">Permissoes</Link>{" "}
                    e <Link to="/produtos">Produtos</Link>.
                  </p>
                </section>
              </div>
            }
          />

          {/* Tela de usuarios (CRUD completo) */}
          <Route path="/usuarios" element={<UsuariosPage />} />

          {/* Tela de permissoes (exercicio das aulas) */}
          <Route path="/permissoes" element={<PermissoesPage />} />

          {/* Tela de produtos (entidade de interesse do projeto) */}
          <Route path="/produtos" element={<ProdutosPage />} />

          {/* Rota 404 — qualquer URL desconhecida */}
          <Route
            path="*"
            element={
              <div className="pagina">
                <header className="pagina-cabecalho">
                  <h1>404 — Pagina nao encontrada</h1>
                  <p className="pagina-subtitulo">
                    A rota acessada nao existe.{" "}
                    <Link to="/">Voltar para o inicio</Link>
                  </p>
                </header>
              </div>
            }
          />
        </Routes>
      </main>

      {/* Rodape da aplicacao */}
      <footer className="app-footer">
        <p>
          Programacao Web II · Spring Boot + React · Arquitetura em camadas (Aula 06) ·
          Banco H2 · Java 17+
        </p>
      </footer>
    </div>
  );
}

export default App;
