# Frontend — React + TypeScript (Programação Web II)

SPA consumindo a API Spring Boot do backend, com **formulários controlados**,
**rotas**, **estados de carregamento/erro** e **CRUD completo**.

> Guia completo do projeto (backend incluído): veja o [`README.md`](../README.md)

---

## ▶ Como rodar

```bash
npm install      # instala as dependências (usa o package-lock.json)
npm run dev      # sobe o Vite em http://localhost:5173

# Build de produção
npm run build    # gera os arquivos em dist/
npm run preview  # serve o build de produção

# Lint (oxlint)
npm run lint
```

> **Importante:** o backend precisa estar no ar (`http://localhost:8080`) para o
> front-end conseguir buscar os dados.

---

## 🗂️ Organização em pastas (Aula 06)

A Aula 06 ensina a separar **telas** de **componentes reutilizáveis**:

```
src/
├── main.tsx              ← entrada: monta <BrowserRouter> + <App />
├── App.tsx               ← define as ROTAS (React Router)
├── index.css             ← estilos da aplicacao
│
├── pages/                ← TELAS do sistema (vao em rotas)
│   ├── UsuariosPage.tsx      ← CRUD de Usuarios
│   ├── PermissoesPage.tsx    ← CRUD de Permissoes
│   └── ProdutosPage.tsx      ← CRUD de Produtos
│
├── components/           ← PEÇAS REUTILIZÁVEIS
│   ├── Menu.tsx              ← menu de navegacao (NavLink)
│   ├── UsuarioForm.tsx       ← formulario controlado (criar/editar)
│   ├── UsuarioItem.tsx       ← apresentacao de um usuario (props)
│   ├── PermissaoForm.tsx / PermissaoItem.tsx
│   ├── ProdutoForm.tsx / ProdutoItem.tsx
│   └── MensagemErro.tsx      ← loading + erro (exercicios da Aula 04)
│
├── services/
│   └── api.ts            ← instancia unica do Axios (baseURL)
│
└── types/                ← interfaces TypeScript
    ├── Usuario.ts
    ├── Permissao.ts
    └── Produto.ts
```

**Regra prática (Aula 06):** se é uma tela inteira → `pages/`;
se é um pedaço reutilizável (botão, formulário, card) → `components/`.

---

## 🧠 Conceitos das aulas usados no código

| Conceito | Onde está |
|----------|-----------|
| **Componentes** (função que retorna JSX) | todos os arquivos de `components/` e `pages/` |
| **Props** (dados do pai para o filho) | `UsuarioItem`, `PermissaoItem`, `ProdutoItem`, `UsuarioForm` |
| **State + useState** | formulários controlados e listagens |
| **useEffect** | `useEffect(() => { carregarUsuarios(); }, [])` — busca dados ao montar a tela |
| **useCallback** | `carregarUsuarios` memoizada nas pages |
| **Formulários controlados** | `value` ligado ao state + `onChange` atualizando o state |
| **Axios + baseURL** | `services/api.ts` |
| **Estados de carregando/erro** | `MensagemErro` (Aula 04, exercícios 3 e 4) |
| **React Router** | `App.tsx` (`Routes`/`Route`) + `Menu.tsx` (`NavLink`) — preparação da Aula 08 |

---

## 🔗 Rotas

| Rota | Página | O que faz |
|------|--------|-----------|
| `/` | Home | Banner da API + lista de endpoints |
| `/usuarios` | `UsuariosPage` | CRUD completo de usuários |
| `/permissoes` | `PermissoesPage` | CRUD completo de permissões |
| `/produtos` | `ProdutosPage` | CRUD completo de produtos |
| `*` | 404 | Página não encontrada |

---

## 🔗 Comunicação com o backend

```ts
// services/api.ts
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export default api;
```

Chamadas nos componentes:

```ts
api.get<Usuario[]>("/usuarios");                    // GET  /api/usuarios
api.post("/usuarios", { nome, username, senha, email });  // POST /api/usuarios
api.put(`/usuarios/${id}`, { nome, username, email });    // PUT  /api/usuarios/{id}
api.delete(`/usuarios/${id}`);                      // DELETE /api/usuarios/{id}
```

O **CORS** já está liberado no backend (`CorsConfig`) para `http://localhost:5173`.

---

## 🎨 Interface

O CSS (`index.css`) usa **variáveis CSS** para as cores e está organizado por seções:
layout, menu, páginas, formulários, listas, botões e mensagens. É responsivo
(menu empilha em telas pequenas).
