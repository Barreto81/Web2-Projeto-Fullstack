// =============================================================================
// components/UsuarioItem.tsx — ITEM DA LISTA (usa PROPS)
// =============================================================================
// Demonstracao da Aula 04: "Props (propriedades)".
//
// Props sao a forma de passar dados de um componente PAI para um componente
// FILHO. Sao somente leitura — o filho nao deve alterar as props recebidas.
//
//   function UsuarioItem({ usuario }: UsuarioItemProps) { ... }
//   <UsuarioItem usuario={joao} />
// =============================================================================
import type { Usuario } from "../types/Usuario";

/** Props do componente */
interface UsuarioItemProps {
  /** Usuario a ser exibido (recebido do componente pai) */
  usuario: Usuario;
}

/**
 * Apresenta as informacoes de um usuario em formato de lista.
 *
 * Componente "apenas de apresentacao" (sem state, sem chamadas HTTP):
 * ele apenas recebe props e renderiza JSX — mais facil de testar e reutilizar.
 */
function UsuarioItem({ usuario }: UsuarioItemProps) {
  return (
    <div className="item">
      <div className="item-conteudo">
        <strong className="item-titulo">{usuario.nome}</strong>
        <span className="item-subtitulo">@{usuario.username}</span>
        <span className="item-detalhe">{usuario.email}</span>
      </div>
    </div>
  );
}

export default UsuarioItem;
