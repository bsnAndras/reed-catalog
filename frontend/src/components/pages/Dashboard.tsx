import { useState } from "react";

export const Dashboard = () => {
  type Log = {
    dateTime: Date;
    event: string;
    orderId: number | null;
    moneyExchanged: number;
    partner: LogPartner | null;
  };

  type LogPartner = {
    id: number;
    name: string;
  };

  // Setting up current date info
  const currentDate = new Date();
  const currYear = currentDate.getFullYear();
  const currMonth = currentDate.toLocaleString("default", { month: "long" });

  // Log data state
  const [logs, setLogs] = useState<Log[]>([]);

  // Dummy log data for now.
  // TODO: Replace with real data fetching
  const dummyLogs = [
    {
      dateTime: new Date("2024-06-01T10:15:00"),
      event: "Initial balance",
      orderId: null,
      partner: null,
      moneyExchanged: 0,
    },
    {
      dateTime: new Date("2024-06-05T14:30:00"),
      event: "New order placed",
      orderId: 1,
      partner: { id: 101, name: "John Doe" },
      moneyExchanged: 0,
    },
    {
      dateTime: new Date("2024-06-10T09:45:00"),
      event: "Payment received",
      orderId: 1,
      partner: { id: 101, name: "John Doe" },
      moneyExchanged: 8000,
    },
    {
      dateTime: new Date("2024-06-12T11:20:00"),
      event: "New order placed",
      orderId: 2,
      partner: { id: 102, name: "Jane Smith" },
      moneyExchanged: 0,
    },
    {
      dateTime: new Date("2024-06-15T16:00:00"),
      event: "New order placed",
      orderId: 3,
      partner: { id: 103, name: "Bob Johnson" },
      moneyExchanged: 0,
    },
    {
      dateTime: new Date("2024-06-18T13:10:00"),
      event: "Payment received",
      orderId: 2,
      partner: { id: 102, name: "Jane Smith" },
      moneyExchanged: 12000,
    },
    {
      dateTime: new Date("2024-06-20T15:30:00"),
      event: "New reed batch purchased",
      orderId: null,
      partner: { id: -1, name: "EXTERNAL PARTNER" },
      moneyExchanged: 100_000,
    },
  ];

  // Simulate data loading after 5 seconds
  window.onload = () => {
    setTimeout(() => {
      setLogs(dummyLogs);
    }, 5000);
  };

  return (
    <>
      <h1 className="text-3xl font-bold text-center">Dashboard</h1>
      <section className="stats m-auto w-full max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
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
        <figure className="w-full overflow-x-auto">
          <table className="stats-per-month table-auto m-auto p-2 border-collapse">
            <col className="w-30"></col>
            {Array.from<number>({ length: 12 }).map((i) => (
              <col key={i} className=""></col>
            ))}
            <thead>
              <tr className="bg-amber-400">
                <th>
                  <span className="loading loading-dots loading-lg"></span>
                </th>
                <th className="px-2">Jan</th>
                <th className="px-2">Feb</th>
                <th className="px-2">Mar</th>
                <th className="px-2">Apr</th>
                <th className="px-2">May</th>
                <th className="px-2">Jun</th>
                <th className="px-2">Jul</th>
                <th className="px-2">Aug</th>
                <th className="px-2">Sep</th>
                <th className="px-2">Oct</th>
                <th className="px-2">Nov</th>
                <th className="px-2">Dec</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td className="p-1">Income</td>
              </tr>
              <tr>
                <td className="p-1">Expense</td>
              </tr>
              <tr>
                <td className="p-1">Cash-flow</td>
              </tr>
              <tr>
                <td className="p-1">Balance</td>
              </tr>
            </tbody>
          </table>
        </figure>
      </section>
      <div className="divider"></div>
      <section className="log m-auto w-full max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
        <h2 className="text-3xl font-bold text-center">Log</h2>
        <table className="logTable table-auto w-full p-2 border-collapse">
          <thead className="bg-amber-400">
            <tr>
              <th className="px-2 text-left">Date & Time</th>
              <th className="px-2 text-left">Event</th>
              <th className="px-2 text-right">Order ID</th>
              <th className="px-2 text-right">Money Exchanged</th>
              <th className="px-2 text-left">Partner</th>
            </tr>
          </thead>
          <tbody>
            {logs.length > 0 ? (
              logs.map((log) => (
                <tr className="log" key={log.dateTime.toISOString()}>
                  <td className="p-2 text-left">
                    {log.dateTime.toLocaleString("default", {
                      month: "2-digit",
                      day: "2-digit",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      second: "2-digit",
                    })}
                  </td>
                  <td className="p-2 text-left">{log.event || "<N/A>"}</td>
                  <td className="p-2 text-right">{log.orderId ?? "-"}</td>
                  <td className="p-2 text-right">
                    {log.moneyExchanged ?? "0"}
                  </td>
                  <td className="p-2 text-left">{log.partner?.name || "-"}</td>
                </tr>
              ))
            ) : (
              <td colSpan={5}>
                <div className="flex w-full flex-col my-4 gap-4">
                  <div className="skeleton h-4 w-3/4"></div>
                  <div className="skeleton h-4 w-full"></div>
                  <div className="skeleton h-4 w-full"></div>
                  <div className="skeleton h-4 w-full"></div>
                </div>
              </td>
            )}
          </tbody>
        </table>
      </section>
    </>
  );
};
