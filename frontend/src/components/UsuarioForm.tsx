// =============================================================================
// components/UsuarioForm.tsx — FORMULARIO CONTROLADO (criar/editar)
// =============================================================================
// Demonstracao da Aula 05: "Formularios Controlados (React)".
//
// Um input controlado tem o `value` ligado ao state e um `onChange` que
// atualiza esse state a cada digitacao. O React e a "fonte da verdade":
// o valor exibido vem sempre do state.
//
//   const [nome, setNome] = useState("");
//   <input value={nome} onChange={(e) => setNome(e.target.value)} />
//
// No envio (onSubmit), os valores do state sao enviados para a API via
// POST (novo) ou PUT (edicao).
// =============================================================================
import { useState, type FormEvent } from "react";
import api from "../services/api";
import type { Usuario, UsuarioRequest } from "../types/Usuario";

/** Props do componente */
interface UsuarioFormProps {
  /** Funcao chamada apos salvar — a pagina recarrega a lista */
  onUsuarioSalvo: () => void;
  /** Usuario em edicao (null = modo cadastro) */
  usuarioEditando?: Usuario | null;
  /** Funcao chamada quando a API devolve erro (para exibir na pagina) */
  onErro?: (mensagem: string) => void;
}

/**
 * Formulario controlado para criar ou editar um Usuario.
 *
 * - **Modo cadastro:** envia POST /api/usuarios (inclui a senha);
 * - **Modo edicao:** envia PUT /api/usuarios/{id} (nao envia senha,
 *   conforme a Aula 05: a senha nao e alterada no PUT).
 *
 * A propriedade `key` no componente pai (UsuariosPage) forca o React a
 * REcriar o formulario quando o usuario em edicao muda — recarregando os
 * valores iniciais do state.
 */
function UsuarioForm({ onUsuarioSalvo, usuarioEditando = null, onErro }: UsuarioFormProps) {
  // State inicial vem do usuario em edicao (ou vazio para cadastro)
  const [nome, setNome] = useState(usuarioEditando?.nome ?? "");
  const [username, setUsername] = useState(usuarioEditando?.username ?? "");
  const [email, setEmail] = useState(usuarioEditando?.email ?? "");
  const [senha, setSenha] = useState(""); // so usada no cadastro
  const [salvando, setSalvando] = useState(false);
  const [erroLocal, setErroLocal] = useState<string | null>(null);

  /**
   * Envia o formulario (cria ou atualiza).
   *
   * @param evento evento de submit do formulário HTML
   */
  async function handleSubmit(evento: FormEvent) {
    evento.preventDefault(); // impede o recarregamento da pagina
    setErroLocal(null);

    try {
      setSalvando(true);

      if (usuarioEditando) {
        // ---- MODO EDICAO: PUT /api/usuarios/{id} (sem senha) ----
        const dados: Omit<UsuarioRequest, "senha"> = { nome, username, email };
        await api.put(`/usuarios/${usuarioEditando.id}`, dados);
      } else {
        // ---- MODO CADASTRO: POST /api/usuarios (com senha) ----
        const dados: UsuarioRequest = { nome, username, senha, email };
        await api.post("/usuarios", dados);
      }

      // Notifica a pagina para recarregar a lista
      onUsuarioSalvo();
    } catch (erro) {
      // Extrai a mensagem de erro devolvida pelo back-end
      // (o formato e: { status, mensagem, campos? } — ver GlobalExceptionHandler)
      const mensagem =
        (erro as { response?: { data?: { mensagem?: string } } })?.response?.data?.mensagem ??
        "Falha ao salvar o usuario. Verifique se o back-end esta rodando.";
      setErroLocal(mensagem);
      onErro?.(mensagem);
    } finally {
      setSalvando(false);
    }
  }

  return (
    <form className="formulario" onSubmit={handleSubmit}>
      <h2>{usuarioEditando ? "Editar usuario" : "Cadastrar usuario"}</h2>

      {erroLocal && <div className="mensagem mensagem-erro">{erroLocal}</div>}

      {/* Campo: NOME */}
      <label>
        Nome completo
        <input
          type="text"
          value={nome}
          onChange={(evento) => setNome(evento.target.value)}
          placeholder="Ex.: Maria da Silva"
          required
          minLength={2}
          maxLength={100}
        />
      </label>

      {/* Campo: USERNAME */}
      <label>
        Username (login)
        <input
          type="text"
          value={username}
          onChange={(evento) => setUsername(evento.target.value)}
          placeholder="Ex.: maria.silva"
          required
          minLength={3}
          maxLength={50}
        />
      </label>

      {/* Campo: EMAIL */}
      <label>
        E-mail
        <input
          type="email"
          value={email}
          onChange={(evento) => setEmail(evento.target.value)}
          placeholder="Ex.: maria@exemplo.com"
          required
          maxLength={100}
        />
      </label>

      {/* Campo: SENHA — apenas no modo cadastro.
          Na edicao nao aparece porque o PUT nao altera a senha (Aula 05). */}
      {!usuarioEditando && (
        <label>
          Senha
          <input
            type="password"
            value={senha}
            onChange={(evento) => setSenha(evento.target.value)}
            placeholder="Minimo 6 caracteres"
            required
            minLength={6}
            maxLength={100}
          />
        </label>
      )}

      <button type="submit" disabled={salvando}>
        {salvando ? "Salvando..." : usuarioEditando ? "Salvar alteracoes" : "Cadastrar"}
      </button>
    </form>
  );
}

export default UsuarioForm;
