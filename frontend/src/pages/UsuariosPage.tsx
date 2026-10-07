// =============================================================================
// pages/UsuariosPage.tsx — TELA (PAGE) DE USUARIOS
// =============================================================================
// Exercicio da Aula 06: "Refatorando o front-end — pages/UsuariosPage.tsx".
//
// A logica que antes estava concentrada no UsuarioList agora vive em uma
// PAGINA (pages/), separada dos componentes reutilizaveis (components/).
// Regra pratica (Aula 06): se e uma tela inteira, vai em pages/;
// se e um pedaco reutilizavel (botao, formulario, card), vai em components/.
//
// Fluxo:
//   useEffect ──► GET /api/usuarios ──► setUsuarios ──► re-render da lista
//   Botao Editar ──► setEditando(usuario) ──► UsuarioForm (modo edicao)
//   Botao Excluir ──► DELETE /api/usuarios/{id} ──► recarrega a lista
// =============================================================================
import { useCallback, useEffect, useState } from "react";
import api from "../services/api";
import type { Usuario } from "../types/Usuario";
import UsuarioItem from "../components/UsuarioItem";
import UsuarioForm from "../components/UsuarioForm";
import MensagemErro from "../components/MensagemErro";

/**
 * Tela completa de gestao de usuarios.
 *
 * Centraliza o estado da listagem e coordena as operacoes CRUD:
 * cadastrar, listar, editar e excluir — integrando front-end e back-end
 * conforme a Aula 05 ("CRUD Completo").
 */
function UsuariosPage() {
  /** Lista de usuarios vinda da API */
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  /** Usuario selecionado para edicao (null = modo cadastro) */
  const [editando, setEditando] = useState<Usuario | null>(null);
  /** true enquanto a requisicao ainda nao retornou (Aula 04 — exercicio 3) */
  const [carregando, setCarregando] = useState(true);
  /** Mensagem de erro (ex.: back-end desligado — Aula 04, exercicio 4) */
  const [erro, setErro] = useState<string | null>(null);
  /** Mensagem de sucesso exibida apos uma operacao de escrita */
  const [aviso, setAviso] = useState<string | null>(null);

  /**
   * Recarrega a lista de usuarios da API.
   * `useCallback` memoiza a funcao para que ela nao seja recriada a cada
   * renderizacao — boa pratica ao combinar com useEffect.
   */
  const carregarUsuarios = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      const resposta = await api.get<Usuario[]>("/usuarios");
      setUsuarios(resposta.data);
    } catch (erroRequisicao) {
      const mensagem =
        (erroRequisicao as { response?: { data?: { mensagem?: string } } })?.response?.data
          ?.mensagem ??
        "Nao foi possivel conectar a API. Verifique se o back-end esta rodando em http://localhost:8080";
      setErro(mensagem);
    } finally {
      setCarregando(false);
    }
  }, []);

  /**
   * Executa a carga inicial assim que a tela e montada.
   * `[]` como segundo argumento = executa apenas uma vez.
   */
  useEffect(() => {
    carregarUsuarios();
  }, [carregarUsuarios]);

  /** Exclui um usuario e recarrega a lista. */
  async function excluir(id: number) {
    if (!window.confirm("Deseja realmente excluir este usuario?")) {
      return;
    }
    try {
      setErro(null);
      await api.delete(`/usuarios/${id}`);
      setAviso("Usuario excluido com sucesso.");
      await carregarUsuarios();
    } catch (erroRequisicao) {
      const mensagem =
        (erroRequisicao as { response?: { data?: { mensagem?: string } } })?.response?.data
          ?.mensagem ?? "Falha ao excluir o usuario.";
      setErro(mensagem);
    }
  }

  /** Chamado pelo UsuarioForm apos salvar — recarrega e limpa a edicao. */
  function aoSalvar() {
    setAviso(editando ? "Usuario atualizado com sucesso." : "Usuario cadastrado com sucesso.");
    setEditando(null);
    carregarUsuarios();
  }

  return (
    <div className="pagina">
      <header className="pagina-cabecalho">
        <h1>Usuarios</h1>
        <p className="pagina-subtitulo">
          CRUD completo consumindo <code>GET/POST/PUT/DELETE /api/usuarios</code>
        </p>
      </header>

      {/* Aviso de sucesso apos criar/editar/excluir */}
      {aviso && (
        <div className="mensagem mensagem-sucesso" role="status">
          {aviso}
        </div>
      )}

      {/* Formulario: key={...} força o React a recriar o componente quando
          o usuario em edicao muda, recarregando os valores iniciais do state */}
      <UsuarioForm
        key={editando?.id ?? "novo"}
        usuarioEditando={editando}
        onUsuarioSalvo={aoSalvar}
        onErro={(mensagem) => {
          setErro(null);
          setAviso(null);
          // O erro ja e exibido dentro do proprio UsuarioForm
          console.error(mensagem);
        }}
      />

      {/* Lista de usuarios com estados de carregamento/erro */}
      <section className="lista-secao">
        <h2>Usuarios cadastrados ({usuarios.length})</h2>

        <MensagemErro carregando={carregando} erro={erro}>
          {usuarios.length === 0 ? (
            <p className="lista-vazia">Nenhum usuario cadastrado ainda.</p>
          ) : (
            <ul className="lista">
              {usuarios.map((usuario) => (
                <li key={usuario.id} className="lista-item">
                  <UsuarioItem usuario={usuario} />
                  <div className="lista-acoes">
                    <button
                      type="button"
                      className="botao botao-secundario"
                      onClick={() => {
                        setAviso(null);
                        setErro(null);
                        setEditando(usuario);
                      }}
                    >
                      Editar
                    </button>
                    <button
                      type="button"
                      className="botao botao-perigo"
                      onClick={() => excluir(usuario.id)}
                    >
                      Excluir
                    </button>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </MensagemErro>
      </section>
    </div>
  );
}

export default UsuariosPage;
