// =============================================================================
// components/PermissaoItem.tsx — ITEM DA LISTA DE PERMISSOES
// =============================================================================
// Exercicio das Aulas 05 e 06: apresentar cada permissao na listagem.
// =============================================================================
import type { Permissao } from "../types/Permissao";

/** Props do componente */
interface PermissaoItemProps {
  /** Permissao a ser exibida */
  permissao: Permissao;
}

/**
 * Apresenta as informacoes de uma permissao em formato de lista.
 * Componente "apenas de apresentacao": recebe props e renderiza JSX.
 */
function PermissaoItem({ permissao }: PermissaoItemProps) {
  return (
    <div className="item">
      <div className="item-conteudo">
        <strong className="item-titulo">{permissao.nome}</strong>
        <span className="item-detalhe">{permissao.descricao || "Sem descricao"}</span>
      </div>
    </div>
  );
}

export default PermissaoItem;
