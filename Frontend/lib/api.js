const KEY = "bcb_session";
const ADMIN_KEY = "bcb_admin";

export const getSession = () => {
  try { return JSON.parse(localStorage.getItem(KEY)); } catch { return null; }
};
export const setSession = (s) => localStorage.setItem(KEY, JSON.stringify(s));
export const clearSession = () => localStorage.removeItem(KEY);

// O token de admin fica só na aba (sessionStorage) e some ao fechá-la.
export const getAdminToken = () => sessionStorage.getItem(ADMIN_KEY);
export const setAdminToken = (t) => sessionStorage.setItem(ADMIN_KEY, t);
export const clearAdminToken = () => sessionStorage.removeItem(ADMIN_KEY);

// Cliente: header X-Document-Id (CPF/CNPJ). Admin ({ admin: true }): header X-Admin-Token.
export async function api(path, { method = "GET", body, admin = false } = {}) {
  const headers = { "Content-Type": "application/json" };
  if (admin) headers["X-Admin-Token"] = getAdminToken() || "";
  else {
    const s = getSession();
    if (s) headers["X-Document-Id"] = s.document;
  }

  const res = await fetch(`/api${path}`, { method, headers, body: body ? JSON.stringify(body) : undefined });
  const data = await res.json().catch(() => null);
  if (!res.ok) {
    const err = new Error(data?.error || data?.message || `Erro ${res.status}`);
    err.status = res.status;
    throw err;
  }
  return data;
}
