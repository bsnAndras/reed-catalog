const Navbar = () => {
  return (
    <header>
      <nav className="navbar bg-base-100 shadow-md mb-4">
        <div className="flex-20 mx-2">
          <a href="/" className="btn btn-ghost normal-case text-xl">
            Reed Catalog
          </a>
          <ul className="navlinks-container flex gap-2">
            <li className="btn navlink">
              <a href="/">Dashboard</a>
            </li>
            <li className="btn navlink">
              <a href="/partners">Partners</a>
            </li>
          </ul>
        </div>
      </nav>
    </header>
  );
};

export { Navbar };
