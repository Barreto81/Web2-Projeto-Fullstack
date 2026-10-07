// =============================================================================
// components/ProdutoForm.tsx — FORMULARIO CONTROLADO DE PRODUTO
// =============================================================================
// Exercicio das Aulas 05 e 06: criar o ProdutoForm para a entidade de
// interesse do projeto final, com o mesmo fluxo do UsuarioForm.
// =============================================================================
import { useState, type FormEvent } from "react";
import api from "../services/api";
import type { Produto, ProdutoRequest } from "../types/Produto";

/** Props do componente */
interface ProdutoFormProps {
  /** Funcao chamada apos salvar — a pagina recarrega a lista */
  onProdutoSalvo: () => void;
  /** Produto em edicao (null = modo cadastro) */
  produtoEditando?: Produto | null;
}

/**
 * Formulario controlado para criar ou editar um Produto.
 *
 * - **Cadastros:** POST /api/produtos
 * - **Edicao:** PUT /api/produtos/{id}
 *
 * Regras validadas no back-end (DTO):
 *   - preco: obrigatorio e MAIOR QUE ZERO (@Positive);
 *   - quantidade: obrigatoria e NAO NEGATIVA (@PositiveOrZero).
 */
function ProdutoForm({ onProdutoSalvo, produtoEditando = null }: ProdutoFormProps) {
  const [nome, setNome] = useState(produtoEditando?.nome ?? "");
  const [preco, setPreco] = useState(
    produtoEditando ? String(produtoEditando.preco) : ""
  );
  const [quantidade, setQuantidade] = useState(
    produtoEditando ? String(produtoEditando.quantidade) : ""
  );
  const [categoria, setCategoria] = useState(produtoEditando?.categoria ?? "");
  const [descricao, setDescricao] = useState(produtoEditando?.descricao ?? "");
  const [salvando, setSalvando] = useState(false);
  const [erroLocal, setErroLocal] = useState<string | null>(null);

  /** Envia o formulario (cria ou atualiza). */
  async function handleSubmit(evento: FormEvent) {
    evento.preventDefault();
    setErroLocal(null);

    try {
      setSalvando(true);

      // Converte os inputs de texto para numero antes de enviar
      const dados: ProdutoRequest = {
        nome,
        preco: Number(preco),
        quantidade: Number(quantidade),
        categoria: categoria || undefined,
        descricao: descricao || undefined,
      };

      if (produtoEditando) {
        await api.put(`/produtos/${produtoEditando.id}`, dados);
      } else {
        await api.post("/produtos", dados);
      }

      onProdutoSalvo();
    } catch (erro) {
      const mensagem =
        (erro as { response?: { data?: { mensagem?: string } } })?.response?.data?.mensagem ??
        "Falha ao salvar o produto. Verifique se o back-end esta rodando.";
      setErroLocal(mensagem);
    } finally {
      setSalvando(false);
    }
  }

  return (
    <form className="formulario" onSubmit={handleSubmit}>
      <h2>{produtoEditando ? "Editar produto" : "Cadastrar produto"}</h2>

      {erroLocal && <div className="mensagem mensagem-erro">{erroLocal}</div>}

      {/* Campo: NOME */}
      <label>
        Nome do produto
        <input
          type="text"
          value={nome}
          onChange={(evento) => setNome(evento.target.value)}
          placeholder="Ex.: Notebook Gamer"
          required
          minLength={2}
          maxLength={120}
        />
      </label>

      {/* Campo: PRECO */}
      <label>
        Preco (R$)
        <input
          type="number"
          step="0.01"
          min="0.01"
          value={preco}
          onChange={(evento) => setPreco(evento.target.value)}
          placeholder="Ex.: 199.90"
          required
        />
      </label>

      {/* Campo: QUANTIDADE */}
      <label>
        Quantidade em estoque
        <input
          type="number"
          step="1"
          min="0"
          value={quantidade}
          onChange={(evento) => setQuantidade(evento.target.value)}
          placeholder="Ex.: 10"
          required
        />
      </label>

      {/* Campo: CATEGORIA (opcional) */}
      <label>
        Categoria
        <input
          type="text"
          value={categoria}
          onChange={(evento) => setCategoria(evento.target.value)}
          placeholder="Ex.: Eletronicos"
          maxLength={80}
        />
      </label>

      {/* Campo: DESCRICAO (opcional) */}
      <label>
        Descricao
        <textarea
          value={descricao}
          onChange={(evento) => setDescricao(evento.target.value)}
          placeholder="Descricao do produto"
          rows={2}
          maxLength={500}
        />
      </label>

      <button type="submit" disabled={salvando}>
        {salvando ? "Salvando..." : produtoEditando ? "Salvar alteracoes" : "Cadastrar"}
      </button>
    </form>
  );
}

export default ProdutoForm;
