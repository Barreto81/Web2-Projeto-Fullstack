// =============================================================================
// main.tsx — PONTO DE ENTRADA DO REACT
// =============================================================================
// Este arquivo monta a aplicacao React dentro da div#root do index.html.
//
// Fluxo:
//   index.html ──► main.tsx ──► <BrowserRouter> ──► <App /> ──► paginas
//
// O <BrowserRouter> e quem habilita o React Router (rotas /usuarios,
// /permissoes, /produtos...). Ele deve envolver toda a aplicacao.
// =============================================================================
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import "./index.css";
import App from "./App";

// Busca a div raiz do index.html e renderiza a aplicacao dentro dela
createRoot(document.getElementById("root")!).render(
  <StrictMode>
    {/* BrowserRouter: habilita o roteamento por URL no React */}
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>
);
