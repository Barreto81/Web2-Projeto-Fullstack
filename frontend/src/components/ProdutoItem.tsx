// =============================================================================
// components/ProdutoItem.tsx — ITEM DA LISTA DE PRODUTOS
// =============================================================================
// Exercicio das Aulas 05 e 06: apresentar cada produto na listagem.
// =============================================================================
import type { Produto } from "../types/Produto";

/** Props do componente */
interface ProdutoItemProps {
  /** Produto a ser exibido */
  produto: Produto;
}

/**
 * Apresenta as informacoes de um produto em formato de lista.
 *
 * Formata o preco com duas casas decimais usando
 * {@link Intl.NumberFormat} — evita exibir "199.9" em vez de "199,90".
 */
function ProdutoItem({ produto }: ProdutoItemProps) {
  // Formata o preco no padrao brasileiro: R$ 1.234,56
  const precoFormatado = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(produto.preco);

  return (
    <div className="item">
      <div className="item-conteudo">
        <strong className="item-titulo">{produto.nome}</strong>
        <span className="item-preco">{precoFormatado}</span>
        <span className="item-detalhe">
          Estoque: {produto.quantidade} un.
          {produto.categoria ? ` · ${produto.categoria}` : ""}
        </span>
      </div>
    </div>
  );
}

export default ProdutoItem;
