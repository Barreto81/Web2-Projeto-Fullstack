// =============================================================================
// types/Permissao.ts — TIPO (INTERFACE) DA PERMISSAO
// =============================================================================
// Espelha o PermissaoDTO devolvido pela API em /api/permissoes.
// Entidade criada como exercicio da Aula 02 e persistida na Aula 03.
// =============================================================================

/** Estrutura JSON devolvida pela API em /api/permissoes */
export interface Permissao {
  id: number;
  nome: string;
  descricao?: string; // opcional: o back-end pode devolver null
}

/**
 * Corpo enviado no POST /api/permissoes e PUT /api/permissoes/{id}.
 * O nome e obrigatorio; a descricao e opcional.
 */
export interface PermissaoRequest {
  nome: string;
  descricao?: string;
}
