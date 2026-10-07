// =============================================================================
// types/Produto.ts — TIPO (INTERFACE) DO PRODUTO
// =============================================================================
// Espelha o ProdutoDTO devolvido pela API em /api/produtos.
// Entidade de interesse do projeto final (exercicios das Aulas 02, 03, 05, 06).
//
// Obs.: o preco usa `number` no TypeScript (JSON nao possui tipo decimal).
// Quando enviar de volta ao back-end, o Java converte automaticamente para
// BigDecimal.
// =============================================================================

/** Estrutura JSON devolvida pela API em /api/produtos */
export interface Produto {
  id: number;
  nome: string;
  preco: number;
  quantidade: number;
  categoria?: string;
  descricao?: string;
}

/**
 * Corpo enviado no POST /api/produtos e PUT /api/produtos/{id}.
 * preco e quantidade sao obrigatorios; preco deve ser positivo.
 */
export interface ProdutoRequest {
  nome: string;
  preco: number;
  quantidade: number;
  categoria?: string;
  descricao?: string;
}
