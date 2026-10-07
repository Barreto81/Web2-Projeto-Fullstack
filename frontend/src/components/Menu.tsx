// =============================================================================
// components/Menu.tsx — MENU DE NAVEGACAO (preparacao Aula 06 / Aula 08)
// =============================================================================
// A Aula 06 prepara o terreno para o "Menu de Navegação" e o React Router:
// "a estrutura do projeto ja esta pronta para receber essa funcionalidade".
//
// Este menu usa <NavLink> do react-router-dom para destaque automatico da
// rota ativa (classe "ativo").
// =============================================================================
import { NavLink } from "react-router-dom";

/**
 * Menu de navegacao fixo no topo da aplicacao.
 *
 * Cada item e um link de rota:
 *   /              → informacoes da API (rota "home")
 *   /usuarios      → UsuariosPage
 *   /permissoes    → PermissoesPage
 *   /produtos      → ProdutosPage
 */
function Menu() {
  return (
    <nav className="menu" aria-label="Menu principal">
      <div className="menu-marca">
        <span className="menu-logo">Web II</span>
        <span className="menu-projeto">Spring Boot + React</span>
      </div>

      <ul className="menu-links">
        <li>
          <NavLink to="/" end className={({ isActive }) => (isActive ? "ativo" : "")}>
            Inicio
          </NavLink>
        </li>
        <li>
          <NavLink to="/usuarios" className={({ isActive }) => (isActive ? "ativo" : "")}>
            Usuarios
          </NavLink>
        </li>
        <li>
          <NavLink to="/permissoes" className={({ isActive }) => (isActive ? "ativo" : "")}>
            Permissoes
          </NavLink>
        </li>
        <li>
          <NavLink to="/produtos" className={({ isActive }) => (isActive ? "ativo" : "")}>
            Produtos
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}

export default Menu;
