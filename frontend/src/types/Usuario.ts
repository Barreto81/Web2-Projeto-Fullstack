// =============================================================================
// types/Usuario.ts — TIPO (INTERFACE) DO USUARIO
// =============================================================================
// Em TypeScript, interface define a "forma" dos dados. Aqui espelhamos o
// UsuarioDTO devolvido pela API Spring Boot.
//
// ATENCAO: o campo `senha` NAO existe aqui de proposito — o back-end nunca
// devolve a senha nas respostas (protegida no DTO e com @JsonProperty
// WRITE_ONLY na entidade).
// =============================================================================

/** Estrutura JSON devolvida pela API em /api/usuarios */
export interface Usuario {
  id: number;
  nome: string;
  username: string;
  email: string;
  dataCadastro?: string; // ISO-8601, ex.: "2026-10-02T19:17:15"
}

/**
 * Corpo enviado no POST /api/usuarios (criacao).
 * A senha e obrigatoria apenas no cadastro — nunca volta na resposta.
 */
export interface UsuarioRequest {
  nome: string;
  username: string;
  senha: string;
  email: string;
}

/**
 * Corpo enviado no PUT /api/usuarios/{id} (edicao).
 * Sem campo senha: conforme a Aula 05, a senha nao e alterada no PUT.
 */
export interface UsuarioAtualizacaoRequest {
  nome: string;
  username: string;
  email: string;
}
