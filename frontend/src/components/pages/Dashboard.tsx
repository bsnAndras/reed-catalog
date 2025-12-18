export const Dashboard = () => {
  const currentDate = new Date();
  const currYear = currentDate.getFullYear();
  const currMonth = currentDate.toLocaleString("default", { month: "long" });

  return (
    <>
      <h1 className="text-3xl font-bold text-center">Dashboard</h1>
      <section className="stats m-auto max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
        <h2 className="text-center text-xl font-semibold text-accent">
          {currYear}. {currMonth}
        </h2>
        <div className="summary font-bold">
          <ul>
            <li>
              Account total (all-time profit):{" "}
              <span className="skeleton inline-block h-4 w-34"></span>
            </li>
            <div className="divider"></div>
            <li>
              Income (current month):{" "}
              <span className="skeleton inline-block h-4 w-34"></span>
            </li>
            <li>
              Expenses (current month):{" "}
              <span className="skeleton inline-block h-4 w-34"></span>
            </li>
            <li>
              Cash-flow (current month):{" "}
              <span className="skeleton inline-block h-4 w-34"></span>
            </li>
          </ul>
        </div>
        <table className="table-auto m-2 overflow-x-scroll border-collapse">
          <col className="w-20"></col>
          {Array.from<number>({ length: 12 }).map((i) => (
            <col key={i} className="w-16"></col>
          ))}
          <thead>
            <tr className="bg-amber-400">
              <th>
                <span className="loading loading-dots loading-lg"></span>
              </th>
              <th>Jan</th>
              <th>Feb</th>
              <th>Mar</th>
              <th>Apr</th>
              <th>May</th>
              <th>Jun</th>
              {/* <th>Jul</th>
              <th>Aug</th>
              <th>Sep</th>
              <th>Oct</th>
              <th>Nov</th>
              <th>Dec</th> */}
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Income</td>
            </tr>
            <tr>
              <td>Expense</td>
            </tr>
            <tr>
              <td>Cash-flow</td>
            </tr>
            <tr>
              <td>Balance</td>
            </tr>
          </tbody>
        </table>
      </section>
      <div className="divider"></div>
      <section className="log m-auto max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
        <h2 className="text-3xl font-bold text-center">Log</h2>
        <div className="flex w-3/5 flex-col gap-4">
          <div className="skeleton h-8 w-full"></div>
          <div className="skeleton h-4 w-3/4"></div>
          <div className="skeleton h-4 w-full"></div>
          <div className="skeleton h-4 w-full"></div>
          <div className="skeleton h-4 w-full"></div>
        </div>
      </section>
    </>
  );
};
