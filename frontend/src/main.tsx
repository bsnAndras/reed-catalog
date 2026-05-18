import { StrictMode } from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter } from "react-router";
import { RouterProvider } from "react-router/dom";
import "./index.css";
import { App } from "./App";
import { Dashboard } from "./components/pages/Dashboard";
import { PartnerProfile } from "./components/pages/PartnerProfile";
import { Partners } from "./components/pages/Partners";
import { RootErrorBoundary } from "./components/pages/RootErrorBoundary";

const router = createBrowserRouter([
  {
    path: "/",
    ErrorBoundary: RootErrorBoundary,
    Component: App,
    children: [
      {
        index: true,
        Component: Dashboard,
      },
      {
        path: "partners",
        Component: Partners,
      },
      {
        path: "partner/:id",
        Component: PartnerProfile,
      },
    ],
  },
]);

//biome-ignore lint/style/noNonNullAssertion: we know for sure that 'root' exists
ReactDOM.createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>,
);
