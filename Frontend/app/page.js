"use client";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { api, setSession } from "../lib/api";

export default function Login() {
  const router = useRouter();
  const [doc, setDoc] = useState("");
  const [type, setType] = useState("CNPJ");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const submit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    const digits = doc.replace(/\D/g, "");
    try {
      const client = await api("/auth", { method: "POST", body: { document: digits, documentType: type } });
      setSession({ ...client, document: digits });
      router.push("/chat");
    } catch (err) {
      setError(err.status === 401 ? "Documento não encontrado ou cliente inativo." : err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="auth">
      <section className="auth-brand">
        <div className="logo">
          <svg width="30" height="30" viewBox="0 0 24 24" fill="none" aria-hidden="true"><path d="M4 5h16v11H9l-5 4V5z" fill="#8de0c6" /></svg>
          Big Chat Brasil
        </div>
        <h2>Converse com seus clientes, sem complicação.</h2>
        <ul>
          <li>Mensagens normais e urgentes</li>
          <li>Saldo e consumo sempre à vista</li>
          <li>Histórico completo de cada conversa</li>
        </ul>
      </section>

      <section className="auth-form">
        <form onSubmit={submit}>
          <h1>Entrar</h1>
          <p>Use o documento cadastrado da sua empresa.</p>
          <input className="field" placeholder="CPF ou CNPJ" value={doc} onChange={(e) => setDoc(e.target.value)} inputMode="numeric" autoFocus required />
          <div className="seg">
            {[["CPF", "Pessoa física"], ["CNPJ", "Pessoa jurídica"]].map(([v, label]) => (
              <label key={v}>
                <input type="radio" name="type" value={v} checked={type === v} onChange={() => setType(v)} />
                {label}
              </label>
            ))}
          </div>
          {error && <div className="error" role="alert">{error}</div>}
          <button className="btn" disabled={loading}>{loading ? "Entrando…" : "Entrar"}</button>
          <a href="/admin">Sou administrador</a>
        </form>
      </section>
    </main>
  );
}
