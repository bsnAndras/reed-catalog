//biome-ignore-all lint: temp file, no need to lint
//biome-ignore-all assist/source/organizeImports: temp file, no need to assist

import { Outlet } from "react-router";
import { Navbar } from "./components/Navbar";

function App() {
  return (
    <>
      <Navbar />
      <main>
        <Outlet />
      </main>
    </>
  );
}

export { App };
