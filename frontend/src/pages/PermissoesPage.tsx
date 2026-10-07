// =============================================================================
// pages/PermissoesPage.tsx — TELA (PAGE) DE PERMISSOES
// =============================================================================
// Exercicio da Aula 06: "Repetir a refatoracao para Permissao: criar
// PermissaoService + PermissaoController e uma PermissoesPage no React".
//
// Mesmo fluxo da UsuariosPage, aplicado a entidade Permissao
// (id, nome, descricao) — criada no exercicio da Aula 02.
// =============================================================================
import { useCallback, useEffect, useState } from "react";
import api from "../services/api";
import type { Permissao } from "../types/Permissao";
import PermissaoItem from "../components/PermissaoItem";
import PermissaoForm from "../components/PermissaoForm";
import MensagemErro from "../components/MensagemErro";

/**
 * Tela completa de gestao de permissoes.
 *
 * Coordena o CRUD de Permissao consumindo
 * {@code /api/permissoes} (GET, POST, PUT, DELETE).
 */
function PermissoesPage() {
  /** Lista de permissoes vinda da API */
  const [permissoes, setPermissoes] = useState<Permissao[]>([]);
  /** Permissao selecionada para edicao (null = modo cadastro) */
  const [editando, setEditando] = useState<Permissao | null>(null);
  /** Estado de carregamento (Aula 04 — exercicio 3) */
  const [carregando, setCarregando] = useState(true);
  /** Mensagem de erro (Aula 04 — exercicio 4) */
  const [erro, setErro] = useState<string | null>(null);
  /** Mensagem de sucesso apos operacao de escrita */
  const [aviso, setAviso] = useState<string | null>(null);

  /** Recarrega a lista de permissoes da API. */
  const carregarPermissoes = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      const resposta = await api.get<Permissao[]>("/permissoes");
      setPermissoes(resposta.data);
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

  /** Executa a carga inicial quando a tela e montada. */
  useEffect(() => {
    carregarPermissoes();
  }, [carregarPermissoes]);

  /** Exclui uma permissao e recarrega a lista. */
  async function excluir(id: number) {
    if (!window.confirm("Deseja realmente excluir esta permissao?")) {
      return;
    }
    try {
      setErro(null);
      await api.delete(`/permissoes/${id}`);
      setAviso("Permissao excluida com sucesso.");
      await carregarPermissoes();
    } catch (erroRequisicao) {
      const mensagem =
        (erroRequisicao as { response?: { data?: { mensagem?: string } } })?.response?.data
          ?.mensagem ?? "Falha ao excluir a permissao.";
      setErro(mensagem);
    }
  }

  /** Chamado pelo PermissaoForm apos salvar — recarrega e limpa a edicao. */
  function aoSalvar() {
    setAviso(
      editando ? "Permissao atualizada com sucesso." : "Permissao cadastrada com sucesso."
    );
    setEditando(null);
    carregarPermissoes();
  }

  return (
    <div className="pagina">
      <header className="pagina-cabecalho">
        <h1>Permissoes</h1>
        <p className="pagina-subtitulo">
          CRUD completo consumindo <code>GET/POST/PUT/DELETE /api/permissoes</code>
        </p>
      </header>

      {aviso && (
        <div className="mensagem mensagem-sucesso" role="status">
          {aviso}
        </div>
      )}

      {/* key força o React a recriar o formulario quando o registro em
          edicao muda, recarregando os valores iniciais do state */}
      <PermissaoForm
        key={editando?.id ?? "nova"}
        permissaoEditando={editando}
        onPermissaoSalva={aoSalvar}
      />

      <section className="lista-secao">
        <h2>Permissoes cadastradas ({permissoes.length})</h2>

        <MensagemErro carregando={carregando} erro={erro}>
          {permissoes.length === 0 ? (
            <p className="lista-vazia">Nenhuma permissao cadastrada ainda.</p>
          ) : (
            <ul className="lista">
              {permissoes.map((permissao) => (
                <li key={permissao.id} className="lista-item">
                  <PermissaoItem permissao={permissao} />
                  <div className="lista-acoes">
                    <button
                      type="button"
                      className="botao botao-secundario"
                      onClick={() => {
                        setAviso(null);
                        setErro(null);
                        setEditando(permissao);
                      }}
                    >
                      Editar
                    </button>
                    <button
                      type="button"
                      className="botao botao-perigo"
                      onClick={() => excluir(permissao.id)}
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

export default PermissoesPage;
