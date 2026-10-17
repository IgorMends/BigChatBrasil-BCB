"use client";
import { useCallback, useEffect, useState } from "react";
import { api, clearAdminToken, getAdminToken, setAdminToken } from "../../lib/api";

const brl = (v) => Number(v ?? 0).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
const EMPTY = { name: "", document: "", documentType: "CNPJ", planType: "prepaid", amount: "" };
const STATS = [
  ["normalQueueSize", "Fila normal"], ["urgentQueueSize", "Fila urgente"], ["queued", "Na fila"],
  ["processing", "Processando"], ["sent", "Enviadas"], ["delivered", "Entregues"], ["failed", "Falhas"],
];

export default function Admin() {
  const [ready, setReady] = useState(false);
  const [authed, setAuthed] = useState(false);
  const [tokenInput, setTokenInput] = useState("");
  const [clients, setClients] = useState([]);
  const [queue, setQueue] = useState(null);
  const [form, setForm] = useState(EMPTY);
  const [amounts, setAmounts] = useState({});
  const [error, setError] = useState("");
  const [notice, setNotice] = useState("");

  const fail = useCallback((e) => {
    if (e.status === 401) { clearAdminToken(); setAuthed(false); setError("Token de admin inválido."); }
    else setError(e.message);
  }, []);

  // A lista devolve só id e nome; os detalhes (documento, plano, saldo) vêm de GET /client/{id}
  const loadClients = useCallback(async () => {
    const list = await api("/client/all", { admin: true });
    const full = await Promise.all(
      list.map((c) => api(`/client/${c.id}`, { admin: true }).then((d) => ({ ...d, id: c.id })).catch(() => c))
    );
    setClients(full);
  }, []);

  const loadQueue = useCallback(async () => setQueue(await api("/queue/status", { admin: true })), []);

  useEffect(() => { if (getAdminToken()) setAuthed(true); setReady(true); }, []);

  useEffect(() => {
    if (!authed) return;
    loadClients().catch(fail);
    const run = () => loadQueue().catch(fail);
    run();
    const t = setInterval(run, 3000);
    return () => clearInterval(t);
  }, [authed, loadClients, loadQueue, fail]);

  const enter = async (e) => {
    e.preventDefault();
    setError("");
    setAdminToken(tokenInput.trim());
    try { await loadQueue(); setAuthed(true); }
    catch (err) { clearAdminToken(); setError(err.status === 401 ? "Token de admin inválido." : err.message); }
  };

  const logout = () => { clearAdminToken(); setAuthed(false); setTokenInput(""); };

  const create = async (e) => {
    e.preventDefault();
    setError(""); setNotice("");
    const amount = Number(form.amount || 0);
    const prepaid = form.planType === "prepaid";
    try {
      await api("/client/save", {
        method: "POST", admin: true,
        body: {
          name: form.name.trim(), document: form.document.replace(/\D/g, ""), documentType: form.documentType,
          planType: form.planType, balance: prepaid ? amount : 0, creditLimit: prepaid ? 0 : amount, active: true,
        },
      });
      setForm(EMPTY); setNotice("Cliente criado."); await loadClients();
    } catch (err) { fail(err); }
  };

  // Pré-pago: ajusta o saldo. Pós-pago: ajusta o limite mensal.
  const adjust = async (c, operation) => {
    const amount = Number(amounts[c.id]);
    setError(""); setNotice("");
    if (!(amount > 0)) { setError("Informe um valor maior que zero."); return; }
    try {
      await api(`/client/${c.id}/credits`, { method: "POST", admin: true, body: { operation, amount } });
      setAmounts({ ...amounts, [c.id]: "" });
      setNotice(`${operation === "increase" ? "Aumentado" : "Reduzido"}: ${brl(amount)} em ${c.name}.`);
      await loadClients();
    } catch (err) { fail(err); }
  };

  const deactivate = async (c) => {
    if (!window.confirm(`Inativar ${c.name}? O cliente não poderá mais entrar.`)) return;
    setError(""); setNotice("");
    try {
      await api(`/client/deactivate/${c.id}`, { method: "PATCH", admin: true });
      setNotice(`${c.name} inativado.`); await loadClients();
    } catch (err) { fail(err); }
  };

  if (!ready) return null;

  if (!authed) {
    return (
      <main className="login">
        <form onSubmit={enter}>
          <h1>Administração</h1>
          <p>Informe o token de admin (variável ADMIN_TOKEN do backend).</p>
          <input className="field" type="password" placeholder="Token de admin" value={tokenInput} onChange={(e) => setTokenInput(e.target.value)} required />
          {error && <div className="error" role="alert">{error}</div>}
          <button className="btn">Entrar</button>
          <a href="/">Voltar ao login de cliente</a>
        </form>
      </main>
    );
  }

  const prepaidForm = form.planType === "prepaid";

  return (
    <>
      <header className="top">
        <strong>Administração</strong>
        <button className="link" onClick={logout}>Sair</button>
      </header>

      <main className="adm">
        {error && <div className="alert" role="alert">{error}</div>}
        {notice && <div className="ok" role="status">{notice}</div>}

        <section className="card">
          <h2>Fila de mensagens</h2>
          <div className="stats">
            {STATS.map(([key, label]) => (
              <div className="stat" key={key}><b>{queue?.[key] ?? "…"}</b><span>{label}</span></div>
            ))}
          </div>
        </section>

        <section className="card">
          <h2>Novo cliente</h2>
          <form className="form-grid" onSubmit={create}>
            <input className="field" placeholder="Nome" required value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} />
            <input className="field" placeholder="CPF ou CNPJ" required inputMode="numeric" value={form.document} onChange={(e) => setForm({ ...form, document: e.target.value })} />
            <select className="field" value={form.documentType} onChange={(e) => setForm({ ...form, documentType: e.target.value })}>
              <option value="CNPJ">CNPJ</option>
              <option value="CPF">CPF</option>
            </select>
            <select className="field" value={form.planType} onChange={(e) => setForm({ ...form, planType: e.target.value })}>
              <option value="prepaid">Pré-pago</option>
              <option value="postpaid">Pós-pago</option>
            </select>
            <input className="field" type="number" min="0" step="0.01" placeholder={prepaidForm ? "Saldo inicial" : "Limite mensal"} value={form.amount} onChange={(e) => setForm({ ...form, amount: e.target.value })} />
            <button className="btn">Criar cliente</button>
          </form>
        </section>

        <section className="card">
          <h2>Clientes</h2>
          <div className="tbl-wrap">
            <table>
              <thead>
                <tr><th>Nome</th><th>Documento</th><th>Plano</th><th>Saldo ou limite</th><th>Status</th><th>Ajustar crédito</th></tr>
              </thead>
              <tbody>
                {clients.length === 0 && <tr><td colSpan={6} className="empty">Nenhum cliente cadastrado.</td></tr>}
                {clients.map((c) => {
                  const prepaid = c.planType === "prepaid";
                  return (
                    <tr key={c.id}>
                      <td>{c.name}</td>
                      <td>{c.document ?? "-"}</td>
                      <td>{c.planType ? (prepaid ? "Pré-pago" : "Pós-pago") : "-"}</td>
                      <td>{c.planType ? (prepaid ? `Saldo ${brl(c.balance)}` : `Limite ${brl(c.creditLimit)}`) : "-"}</td>
                      <td><span className={`tag ${c.active === false ? "off" : ""}`}>{c.active === false ? "Inativo" : "Ativo"}</span></td>
                      <td>
                        <div className="row-actions">
                          <input className="field" type="number" min="0" step="0.01" placeholder="Valor" aria-label={`Valor para ${c.name}`} value={amounts[c.id] ?? ""} onChange={(e) => setAmounts({ ...amounts, [c.id]: e.target.value })} />
                          <button className="sm add" onClick={() => adjust(c, "increase")}>Aumentar</button>
                          <button className="sm rm" onClick={() => adjust(c, "decrease")}>Diminuir</button>
                          {c.active !== false && <button className="sm off" onClick={() => deactivate(c)}>Inativar</button>}
                        </div>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>
        </section>
      </main>
    </>
  );
}
