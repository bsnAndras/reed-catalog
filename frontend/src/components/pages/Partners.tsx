import { useState } from "react";
import { useNavigate } from "react-router";
import type { PartnerListItem, PartnerDetails } from "../../types";

export const Partners = () => {
  const dummyPartners: PartnerListItem[] = [
    {
      id: 101,
      name: "John Doe",
      orderInfo: {
        debt: 10000,
        lastOrderDate: new Date("2026-03-25T14:30:00"),
      },
    },
    {
      id: 102,
      name: "Jane Smith",
      orderInfo: {
        debt: 0,
        lastOrderDate: new Date("2024-06-18T10:15:00"),
      },
    },
    {
      id: 103,
      name: "Bob Johnson",
      orderInfo: {
        debt: 12000,
        lastOrderDate: new Date("2024-06-22T09:45:00"),
      },
    },
  ];

  const navigate = useNavigate();
  const [partners, setPartners] = useState<PartnerListItem[]>([]);

  window.onload = () => {
    setTimeout(() => {
      setPartners(dummyPartners);
    }, 1500);
  };

  const handleAddPartner = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    //TODO: provide Add-partner functionality
  };

  const handlePartnerRowClick = (
    e: React.MouseEvent<HTMLTableRowElement>,
    partner: PartnerListItem,
  ) => {
    e.preventDefault();
    navigate(`/partner/id=${partner.id}`, { state: {
      partner: {
        id: partner.id,
        name: partner.name,
        orderList: [],
        balanceSummary: {
          debt: partner.orderInfo.debt,
          balance: 0,
        },
      } as PartnerDetails,
    } });
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
          <thead>
            <tr className="bg-amber-400">
              <th className="px-2 text-right">ID</th>
              <th className="px-2 text-left">Name</th>
              <th className="px-2 text-right">Debt</th>
              <th className="px-2 text-right">Last order date</th>
            </tr>
          </thead>
          <tbody>
            {partners.length > 0 ? (
              partners.map((partner) => (
                <tr
                  key={partner.id}
                  className="hover:bg-amber-100 cursor-pointer"
                  onClick={(e) => handlePartnerRowClick(e, partner)}
                >
                  <td className="p-2 text-right">{partner.id}</td>
                  <td className="p-2 text-left">{partner.name}</td>
                  <td className="p-2 text-right">{partner.orderInfo.debt}</td>
                  <td className="p-2 text-right">
                    {partner.orderInfo.lastOrderDate.toLocaleDateString()}
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
