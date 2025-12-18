export const Partners = () => {
  const handleAddPartner = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    //TODO: provide Add-partner functionality
  };

  return (
    <>
      <h1 className="text-3xl font-bold text-center my-5">Partners</h1>
      <section className="log m-auto max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
        <div className="w-4/5 flex justify-end">
          <span>
            <button
              type="button"
              className="btn btn-primary disabled:bg-gray-300"
              onClick={handleAddPartner}
              disabled
            >
              Add partner
            </button>
          </span>
        </div>
        <table className="table-auto w-4/5 m-2 overflow-x-scroll border-collapse">
          <col className="w-20"></col>
          <col className="w-52"></col>
          <col className="w-32"></col>
          <col className="w-64"></col>
          <thead>
            <tr className="bg-amber-400">
              <th>ID</th>
              <th>Name</th>
              <th>Debt</th>
              <th>Last order date</th>
            </tr>
          </thead>
          <tbody>
            <tr></tr>
          </tbody>
        </table>
        <div className="skeletons flex w-4/5 flex-col gap-4">
          <div className="skeleton h-4 w-full"></div>
          <div className="skeleton h-4 w-3/4"></div>
          <div className="skeleton h-4 w-full"></div>
          <div className="skeleton h-4 w-full"></div>
          <div className="skeleton h-4 w-full"></div>
        </div>
      </section>
    </>
  );
};
