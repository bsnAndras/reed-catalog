//Partners

//for a basic name info
export interface PartnerBase {
  id: number;
  name: string;
}

// for the list display
export interface PartnerListItem extends PartnerBase {
  orderInfo: {
    debt: number;
    lastOrderDate: Date;
  };
}

// for the page display
export interface PartnerDetails extends PartnerBase {
  orderList: Order[];
  balanceSummary: {
    balance: number;
    debt: number;
  };
}

//----------------------------------------------------------------------
//Orders

export interface Order {
  id: number;
  dateOfPurchase: Date;
  totalPrice: number;
  amountToPay: number;
  notes: string;
  partner: PartnerBase;
}

//------------------------------------------------------------------------
//Logs

export type Log = {
  dateTime: Date;
  event: string;
  orderId: number | null;
  moneyExchanged: number;
  partner: PartnerBase | null;
};
