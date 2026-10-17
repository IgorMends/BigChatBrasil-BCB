"use client";
import { useCallback, useEffect, useRef, useState } from "react";
import { useRouter } from "next/navigation";
import { api, clearSession, getSession } from "../../lib/api";

const brl = (v) => Number(v ?? 0).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
const hhmm = (t) => (t ? new Date(t).toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }) : "");
const day = (t) => {
  if (!t) return "";
  const d = new Date(t);
  return d.toDateString() === new Date().toDateString() ? hhmm(t) : d.toLocaleDateString("pt-BR", { day: "2-digit", month: "2-digit" });
};
const initials = (n = "?") => n.trim().split(/\s+/).slice(0, 2).map((w) => w[0]?.toUpperCase()).join("") || "?";
const hue = (n = "") => [...n].reduce((a, c) => a + c.charCodeAt(0), 0) % 360;
const Avatar = ({ name }) => <span className="avatar" style={{ background: `hsl(${hue(name)} 45% 42%)` }}>{initials(name)}</span>;
const TICKS = { queued: "🕓", processing: "⏳", sent: "✓", delivered: "✓✓", read: "✓✓", failed: "⚠ erro" };

export default function Chat() {
  const router = useRouter();
  const [session, setSession] = useState(null);
  const [balance, setBalance] = useState(null);
  const [convs, setConvs] = useState([]);
  const [search, setSearch] = useState("");
  const [active, setActive] = useState(null); // conversa existente ou { isNew, recipientId, recipientName }
  const [newForm, setNewForm] = useState(null);
  const [msgs, setMsgs] = useState([]);
  const [text, setText] = useState("");
  const [urgent, setUrgent] = useState(false);
  const [sending, setSending] = useState(false);
  const [error, setError] = useState("");
  const bottom = useRef(null);

  useEffect(() => {
    const s = getSession();
    if (s) setSession(s); else router.replace("/");
  }, [router]);

  const guard = useCallback((e) => {
    if (e.status === 401) { clearSession(); router.replace("/"); } else setError(e.message);
  }, [router]);

  const loadConvs = useCallback(async () => {
    const q = search.trim() ? `?search=${encodeURIComponent(search.trim())}` : "";
    const list = await api(`/conversation${q}`);
    setConvs(list);
    return list;
  }, [search]);

  const loadBalance = useCallback(async () => {
    if (session) setBalance(await api(`/client/balance/${session.id}`));
  }, [session]);

  const loadMsgs = useCallback(async (id) => {
    setMsgs(await api(`/message/${id}/messages?page=0&size=100`));
  }, []);

  const activeId = active && !active.isNew ? active.id : null;

  // Atualização automática (simula tempo real): a cada 3s recarrega conversas, saldo e mensagens abertas
  useEffect(() => {
    if (!session) return;
    const run = () => Promise.all([loadConvs(), loadBalance(), activeId ? loadMsgs(activeId) : null]).catch(guard);
    run();
    const t = setInterval(run, 3000);
    return () => clearInterval(t);
  }, [session, loadConvs, loadBalance, loadMsgs, activeId, guard]);

  useEffect(() => { bottom.current?.scrollIntoView({ behavior: "smooth" }); }, [msgs.length, activeId]);

  const open = (c) => {
    setActive(c); setMsgs([]); setError(""); setNewForm(null);
    if (c.unreadCount > 0) api(`/conversation/${c.id}/read`, { method: "PATCH" }).catch(() => {});
  };

  const startNew = (e) => {
    e.preventDefault();
    setActive({ isNew: true, ...newForm }); setMsgs([]); setError(""); setNewForm(null);
  };

  const send = async (e) => {
    e.preventDefault();
    if (!text.trim() || !active) return;
    setSending(true); setError("");
    try {
      const body = { content: text.trim(), priority: urgent ? "urgent" : "normal" };
      if (active.isNew) { body.recipientId = active.recipientId; body.recipientName = active.recipientName; }
      else body.conversationId = active.id;

      const res = await api("/message", { method: "POST", body });
      setText(""); setUrgent(false);
      if (res.currentBalance != null) setBalance((b) => ({ ...b, balance: res.currentBalance }));

      const list = await loadConvs();
      if (active.isNew) {
        const c = list.find((x) => x.recipientId === active.recipientId) || [...list].sort((a, b) => new Date(b.lastMessageTime) - new Date(a.lastMessageTime))[0];
        if (c) { setActive(c); await loadMsgs(c.id); }
      } else await loadMsgs(active.id);
    } catch (err) {
      guard(err); // 402 = saldo/limite insuficiente (mensagem vem do backend)
    } finally {
      setSending(false);
    }
  };

  const logout = () => { clearSession(); router.replace("/"); };
  if (!session) return null;

  const balanceText = balance?.remainingLimit != null
    ? `Limite restante ${brl(balance.remainingLimit)}`
    : `Saldo ${brl(balance?.balance)}`;

  return (
    <div className={`app ${active ? "open" : ""}`}>
      <header className="top">
        <span className="mark">BCB</span><strong>{session.name}</strong>
        <span className="chip">{balance ? balanceText : "…"}</span>
        <button className="link" onClick={logout}>Sair</button>
      </header>

      <aside className="side">
        <input className="field" placeholder="Buscar conversas" value={search} onChange={(e) => setSearch(e.target.value)} />
        <div className="list">
          {convs.length === 0 && <p className="empty">Nenhuma conversa ainda. Comece uma nova abaixo.</p>}
          {convs.map((c) => (
            <button key={c.id} className={`conv ${activeId === c.id ? "on" : ""}`} onClick={() => open(c)}>
              <Avatar name={c.recipientName || c.recipientId} /><div className="row"><strong>{c.recipientName || c.recipientId}</strong><small>{day(c.lastMessageTime)}</small></div>
              <div className="row">
                <span className="last">{c.lastMessageContent}</span>
                {c.unreadCount > 0 && <span className="badge">{c.unreadCount}</span>}
              </div>
            </button>
          ))}
        </div>
        {newForm ? (
          <form className="new" onSubmit={startNew}>
            <input className="field" placeholder="ID do destinatário (ex.: telefone)" required value={newForm.recipientId} onChange={(e) => setNewForm({ ...newForm, recipientId: e.target.value })} />
            <input className="field" placeholder="Nome do destinatário" required value={newForm.recipientName} onChange={(e) => setNewForm({ ...newForm, recipientName: e.target.value })} />
            <button className="btn">Abrir conversa</button>
          </form>
        ) : (
          <div className="new"><button className="btn" onClick={() => setNewForm({ recipientId: "", recipientName: "" })}>+ Nova conversa</button></div>
        )}
      </aside>

      <section className="main">
        {!active ? (
          <div className="placeholder"><span className="big">💬</span><p>Escolha uma conversa ou comece uma nova.</p></div>
        ) : (
          <>
            <div className="head">
              <button className="back" onClick={() => setActive(null)} aria-label="Voltar">←</button>
              <Avatar name={active.recipientName || active.recipientId} />
              {active.recipientName || active.recipientId}
            </div>
            {error && <div className="alert" role="alert">{error}</div>}
            <div className="msgs">
              {msgs.length === 0 && <p className="placeholder">Sem mensagens. Envie a primeira.</p>}
              {msgs.map((m) => (
                <div key={m.id} className={`bubble ${m.senderType === "client" ? "out" : ""} ${m.priority === "urgent" ? "urgent" : ""}`}>
                  {m.content}
                  <div className="meta">
                    {m.priority === "urgent" && <span>⚡</span>}
                    <span>{hhmm(m.timestamp)}</span>
                    {m.senderType === "client" && <span className={m.status}>{TICKS[m.status] ?? m.status}</span>}
                  </div>
                </div>
              ))}
              <div ref={bottom} />
            </div>
            <form className="compose" onSubmit={send}>
              <button type="button" className="bolt" aria-pressed={urgent} onClick={() => setUrgent(!urgent)} title="Mensagem urgente (R$ 0,50)">⚡</button>
              <input className="field" placeholder={urgent ? "Mensagem urgente (R$ 0,50)" : "Mensagem (R$ 0,25)"} value={text} onChange={(e) => setText(e.target.value)} />
              <button className="send" disabled={sending || !text.trim()} aria-label="Enviar mensagem">➤</button>
            </form>
          </>
        )}
      </section>
    </div>
  );
}
