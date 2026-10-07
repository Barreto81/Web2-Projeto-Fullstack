// =============================================================================
// pages/ProdutosPage.tsx — TELA (PAGE) DE PRODUTOS
// =============================================================================
// Exercicio das Aulas 02, 03, 05 e 06: completar o CRUD da entidade de
// interesse (Produto), organizada na camada pages/ conforme a Aula 06.
// =============================================================================
import { useCallback, useEffect, useState } from "react";
import api from "../services/api";
import type { Produto } from "../types/Produto";
import ProdutoItem from "../components/ProdutoItem";
import ProdutoForm from "../components/ProdutoForm";
import MensagemErro from "../components/MensagemErro";

/**
 * Tela completa de gestao de produtos.
 *
 * Coordena o CRUD de Produto consumindo
 * {@code /api/produtos} (GET, POST, PUT, DELETE).
 */
function ProdutosPage() {
  /** Lista de produtos vinda da API */
  const [produtos, setProdutos] = useState<Produto[]>([]);
  /** Produto selecionado para edicao (null = modo cadastro) */
  const [editando, setEditando] = useState<Produto | null>(null);
  /** Estado de carregamento (Aula 04 — exercicio 3) */
  const [carregando, setCarregando] = useState(true);
  /** Mensagem de erro (Aula 04 — exercicio 4) */
  const [erro, setErro] = useState<string | null>(null);
  /** Mensagem de sucesso apos operacao de escrita */
  const [aviso, setAviso] = useState<string | null>(null);

  /** Recarrega a lista de produtos da API. */
  const carregarProdutos = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      const resposta = await api.get<Produto[]>("/produtos");
      setProdutos(resposta.data);
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
    carregarProdutos();
  }, [carregarProdutos]);

  /** Exclui um produto e recarrega a lista. */
  async function excluir(id: number) {
    if (!window.confirm("Deseja realmente excluir este produto?")) {
      return;
    }
    try {
      setErro(null);
      await api.delete(`/produtos/${id}`);
      setAviso("Produto excluido com sucesso.");
      await carregarProdutos();
    } catch (erroRequisicao) {
      const mensagem =
        (erroRequisicao as { response?: { data?: { mensagem?: string } } })?.response?.data
          ?.mensagem ?? "Falha ao excluir o produto.";
      setErro(mensagem);
    }
  }

  /** Chamado pelo ProdutoForm apos salvar — recarrega e limpa a edicao. */
  function aoSalvar() {
    setAviso(editando ? "Produto atualizado com sucesso." : "Produto cadastrado com sucesso.");
    setEditando(null);
    carregarProdutos();
  }

  return (
    <div className="pagina">
      <header className="pagina-cabecalho">
        <h1>Produtos</h1>
        <p className="pagina-subtitulo">
          Entidade de interesse do projeto · CRUD em{" "}
          <code>GET/POST/PUT/DELETE /api/produtos</code>
        </p>
      </header>

      {aviso && (
        <div className="mensagem mensagem-sucesso" role="status">
          {aviso}
        </div>
      )}

      {/* key força o React a recriar o formulario quando o produto em
          edicao muda, recarregando os valores iniciais do state */}
      <ProdutoForm
        key={editando?.id ?? "novo"}
        produtoEditando={editando}
        onProdutoSalvo={aoSalvar}
      />

      <section className="lista-secao">
        <h2>Produtos cadastrados ({produtos.length})</h2>

        <MensagemErro carregando={carregando} erro={erro}>
          {produtos.length === 0 ? (
            <p className="lista-vazia">Nenhum produto cadastrado ainda.</p>
          ) : (
            <ul className="lista">
              {produtos.map((produto) => (
                <li key={produto.id} className="lista-item">
                  <ProdutoItem produto={produto} />
                  <div className="lista-acoes">
                    <button
                      type="button"
                      className="botao botao-secundario"
                      onClick={() => {
                        setAviso(null);
                        setErro(null);
                        setEditando(produto);
                      }}
                    >
                      Editar
                    </button>
                    <button
                      type="button"
                      className="botao botao-perigo"
                      onClick={() => excluir(produto.id)}
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

export default ProdutosPage;
