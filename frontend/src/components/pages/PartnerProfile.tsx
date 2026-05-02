import type { Order, PartnerDetails } from "../../types";

export const PartnerProfile = () => {
  // Dummy data for demonstration purposes
  const dummyPartner = {
    id: 101,
    name: "John Doe",
    orderList: [],
    balanceSummary: {
      debt: 10000,
      balance: 5000,
    },
  } as PartnerDetails;

  const dummyOrders = [
    {
      id: 1,
      dateOfPurchase: new Date("2026-01-15T09:30:00"),
      totalPrice: 10000,
      amountToPay: 0,
      notes: "First order",
      partner: dummyPartner,
    },
    {
      id: 2,
      dateOfPurchase: new Date("2026-02-20T10:00:00"),
      totalPrice: 5000,
      amountToPay: 0,
      notes: "Second order",
      partner: dummyPartner,
    },
    {
      id: 3,
      dateOfPurchase: new Date("2026-03-25T14:30:00"),
      totalPrice: 15000,
      amountToPay: 10000,
      notes: "Third order",
      partner: dummyPartner,
    },
  ] as Order[];

  // Simulate fetching partner details and orders
  const partner: PartnerDetails = dummyPartner;
  partner.orderList = dummyOrders;

  let orders = partner.orderList;

  const handleAddNewOrder = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
  };

  const handlePayOrder = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
  }

  return (
    <>
      <h1 className="text-3xl font-bold text-center my-5">{partner.name}</h1>
      <section className="log m-auto max-w-210 xl:max-w-3/5 flex flex-col items-center gap-4 my-4">
        <div className="w-4/5 flex justify-end">
          <span>
            <button
              type="button"
              className="btn btn-primary btn-disabled"
              onClick={handleAddNewOrder}
            >
              Add new order
            </button>
          </span>
        </div>
        <table className="table-auto w-4/5 m-2 overflow-x-scroll border-collapse">
          <thead>
            <tr className="bg-amber-400">
              <th className="px-2 text-right">Order ID</th>
              <th className="px-2 text-left">Date of purchase</th>
              <th className="px-2 text-right">Amount to pay</th>
              <th className="px-2 text-right">Notes</th>
              <th className="px-2"></th>
            </tr>
          </thead>
          <tbody>
            {orders.length > 0 ? (
              orders.map((order) => (
                <tr key={order.id}>
                  <td className="p-2 text-right">{order.id}</td>
                  <td className="p-2 text-right">
                    {order.dateOfPurchase.toLocaleDateString("default", {
                      month: "2-digit",
                      day: "2-digit",
                      year: "numeric",
                      hour: "2-digit",
                      minute: "2-digit",
                      second: "2-digit",
                    })}
                  </td>
                  <td className="p-2 text-right">{order.amountToPay}</td>
                  <td className="p-2 text-right">{order.notes}</td>
                  <td className="p-2 text-right">
                    <button
                      type="button"
                      className="btn btn-secondary disabled:bg-gray-300"
                      onClick={handlePayOrder}
                    >
                      Pay Order
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={4}>
                  <div className="skeletons flex w-full flex-col my-4 gap-4">
                    <div className="skeleton h-4 w-full"></div>
                    <div className="skeleton h-4 w-3/4"></div>
                    <div className="skeleton h-4 w-full"></div>
                    <div className="skeleton h-4 w-full"></div>
                    <div className="skeleton h-4 w-full"></div>
                  </div>
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  );
};
