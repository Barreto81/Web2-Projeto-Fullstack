// =============================================================================
// components/MensagemErro.tsx — COMPONENTE REUTILIZAVEL DE ERRO / CARREGANDO
// =============================================================================
// Cobre os exercicios da Aula 04:
//   3. Adicionar um estado de carregando (loading) exibido enquanto a
//      requisicao nao retorna;
//   4. Tratar o caso de erro (ex: back-end desligado) exibindo uma mensagem
//      para o usuario.
//
// Props:
//   - carregando: boolean → mostra o spinner/mensagem de carregamento
//   - erro: string | null → mostra a mensagem de erro (se houver)
// =============================================================================
import type { ReactNode } from "react";

/** Props aceitas pelo componente */
interface MensagemErroProps {
  /** true = exibe o estado de carregamento */
  carregando?: boolean;
  /** texto do erro (null/undefined = sem erro) */
  erro?: string | null;
  /** conteudo a ser renderizado quando nao ha carregamento nem erro */
  children?: ReactNode;
}

/**
 * Componente que gerencia três estados:
 *
 * 1. **Carregando** — enquanto a requisicao a API ainda nao retornou;
 * 2. **Erro** — quando a requisicao falhou (back-end desligado, rede...);
 * 3. **Conteúdo** — os dados recebidos da API.
 *
 * Uso:
 * ```tsx
 * <MensagemErro carregando={carregando} erro={erro}>
 *   <ul>{usuarios.map(...)}</ul>
 * </MensagemErro>
 * ```
 */
function MensagemErro({ carregando = false, erro = null, children }: MensagemErroProps) {
  // Estado 1: carregamento
  if (carregando) {
    return (
      <div className="mensagem mensagem-carregando" role="status">
        <span className="spinner" aria-hidden="true" />
        Carregando dados da API...
      </div>
    );
  }

  // Estado 2: erro (ex.: back-end desligado)
  if (erro) {
    return (
      <div className="mensagem mensagem-erro" role="alert">
        <strong>Ocorreu um erro:</strong> {erro}
        <div className="mensagem-erro-dica">
          Verifique se o back-end Spring Boot está rodando em{" "}
          <code>http://localhost:8080</code>.
        </div>
      </div>
    );
  }

  // Estado 3: conteúdo normal
  return <>{children}</>;
}

export default MensagemErro;
