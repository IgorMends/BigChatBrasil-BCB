import { Figtree } from "next/font/google";
import "./globals.css";

const font = Figtree({ subsets: ["latin"] });

export const metadata = { title: "Big Chat Brasil", description: "Chat entre empresas e clientes" };

export default function RootLayout({ children }) {
  return (
    <html lang="pt-BR">
      <body className={font.className}>{children}</body>
    </html>
  );
}
