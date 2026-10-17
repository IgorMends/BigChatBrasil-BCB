// O navegador chama /api/*; o Next repassa ao backend. Assim não precisa configurar CORS.
// Local: BACKEND_URL=http://localhost:8080 (arquivo .env.local). No Docker o padrão abaixo já serve.
const backend = process.env.BACKEND_URL || "http://backend:8080";

module.exports = {
  output: "standalone",
  async rewrites() {
    return [{ source: "/api/:path*", destination: `${backend}/:path*` }];
  },
};
