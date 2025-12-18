const Navbar = () => {
  return (
    <header className="bg-amber-950">
      <nav className="navbar m-auto max-w-210 xl:max-w-3/5 shadow-sm">
        <div className="flex-1 sm:max-h-16 h-12 w-24 mx-2">
          <a href="/">
            <img className="h-full" src="/src/assets/logo.jpg" alt="logo" />
          </a>
        </div>
        <div className="flex-none">
          <ul className="navlinks-container menu menu-horizontal text-amber-100 font-bold text-md">
            <li>
              <a href="/">Dashboard</a>
            </li>
            <li>
              <a href="/partners">Partners</a>
            </li>
          </ul>
        </div>
      </nav>
    </header>
  );
};

export { Navbar };
