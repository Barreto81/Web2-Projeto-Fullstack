// =============================================================================
// components/PermissaoForm.tsx — FORMULARIO CONTROLADO DE PERMISSAO
// =============================================================================
// Exercicio das Aulas 05 e 06: criar o PermissaoForm + CRUD completo,
// replicando o mesmo fluxo do UsuarioForm.
// =============================================================================
import { useState, type FormEvent } from "react";
import api from "../services/api";
import type { Permissao, PermissaoRequest } from "../types/Permissao";

/** Props do componente */
interface PermissaoFormProps {
  /** Funcao chamada apos salvar — a pagina recarrega a lista */
  onPermissaoSalva: () => void;
  /** Permissao em edicao (null = modo cadastro) */
  permissaoEditando?: Permissao | null;
}

/**
 * Formulario controlado para criar ou editar uma Permissao.
 *
 * - **Cadastros:** POST /api/permissoes
 * - **Edicao:** PUT /api/permissoes/{id}
 *
 * A descricao e opcional — o back-end aceita valores nulos/ausentes.
 */
function PermissaoForm({ onPermissaoSalva, permissaoEditando = null }: PermissaoFormProps) {
  const [nome, setNome] = useState(permissaoEditando?.nome ?? "");
  const [descricao, setDescricao] = useState(permissaoEditando?.descricao ?? "");
  const [salvando, setSalvando] = useState(false);
  const [erroLocal, setErroLocal] = useState<string | null>(null);

  /** Envia o formulario (cria ou atualiza). */
  async function handleSubmit(evento: FormEvent) {
    evento.preventDefault();
    setErroLocal(null);

    try {
      setSalvando(true);

      const dados: PermissaoRequest = { nome, descricao };

      if (permissaoEditando) {
        await api.put(`/permissoes/${permissaoEditando.id}`, dados);
      } else {
        await api.post("/permissoes", dados);
      }

      onPermissaoSalva();
    } catch (erro) {
      const mensagem =
        (erro as { response?: { data?: { mensagem?: string } } })?.response?.data?.mensagem ??
        "Falha ao salvar a permissao. Verifique se o back-end esta rodando.";
      setErroLocal(mensagem);
    } finally {
      setSalvando(false);
    }
  }

  return (
    <form className="formulario" onSubmit={handleSubmit}>
      <h2>{permissaoEditando ? "Editar permissao" : "Cadastrar permissao"}</h2>

      {erroLocal && <div className="mensagem mensagem-erro">{erroLocal}</div>}

      {/* Campo: NOME (obrigatorio, unico no sistema) */}
      <label>
        Nome da permissao
        <input
          type="text"
          value={nome}
          onChange={(evento) => setNome(evento.target.value)}
          placeholder="Ex.: ADMIN, OPERADOR, CONSULTA"
          required
          minLength={2}
          maxLength={50}
        />
      </label>

      {/* Campo: DESCRICAO (opcional) */}
      <label>
        Descricao
        <textarea
          value={descricao}
          onChange={(evento) => setDescricao(evento.target.value)}
          placeholder="Ex.: Acesso total ao sistema"
          rows={2}
          maxLength={255}
        />
      </label>

      <button type="submit" disabled={salvando}>
        {salvando ? "Salvando..." : permissaoEditando ? "Salvar alteracoes" : "Cadastrar"}
      </button>
    </form>
  );
}

export default PermissaoForm;
