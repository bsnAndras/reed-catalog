package com.bsnandras.reedcatalog.services.database;

import com.bsnandras.reedcatalog.dtos.newOrder.NewOrderRequestDto;
import com.bsnandras.reedcatalog.dtos.paymentReceived.PaymentRequestDto;
import com.bsnandras.reedcatalog.errors.OrderNotFoundException;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.models.Partner;
import com.bsnandras.reedcatalog.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    /**
     * Place a new order without upfront payment,
     * and updates the partner's account balance and the order's amountToPay accordingly.
     *
     * @param requestDto the new order request
     * @return a message whether the order registration was successful
     */
    @Override
    public Order placeNewOrder(Partner partner, NewOrderRequestDto requestDto) {
        Order newOrder = Order.builder()
                .dateOfPurchase(new Date())
                .partner(partner)
                .totalPrice(requestDto.totalPrice())
                .amountToPay(requestDto.totalPrice())
                .build();

        orderRepository.save(newOrder);

        return newOrder;
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("No order found under this id: " + orderId));
    }

    /**
     * The method for paying an order from partner's excess money from his/her account
     *
     * @param order The Order you wish to pay from Partner balance
     * @return the Order after paying from the partner balance
     */
    public Order payOrderFromPartnerBalance(Order order) {
        Partner partner = order.getPartner();
        int currentBalance = partner.getBalance();
        int partnerDebt = order.getAmountToPay();

        int amountToBePayed;

        if ((currentBalance <= 0 && partnerDebt >= 0) || partnerDebt == 0) {
            return order;
        }
        if (partnerDebt < 0) {
            amountToBePayed = partnerDebt;
        } else {
            amountToBePayed = Math.min(currentBalance, partnerDebt);
        }

//        partnerService.setBalance(partner.getId(),
//                currentBalance - amountToBePayed);

        order.setAmountToPay(partnerDebt - amountToBePayed);

        return order;
    }

    /**
     * Pays the order with the money the user paid. Returns the remaining debt on the order.
     *
     * @param order     the order to be paid
     * @param moneyPaid the money, the partner paid
     * @return the remaining debt on the order, that is still to be paid
     */
    public int payOrderFromNewMoney(Order order, int moneyPaid) {
        int partnerDebt = order.getAmountToPay();
        int remainingDebt = partnerDebt - moneyPaid; //can go negative if excess money is paid

        order.setAmountToPay(Math.max(remainingDebt, 0));
        return remainingDebt;
    }

    @Override
    public int payOrder(PaymentRequestDto requestDto) {
        Order order = getOrder(requestDto.orderId());

        order = payOrderFromPartnerBalance(order);
        int remainingDebt = payOrderFromNewMoney(order, requestDto.paymentAmount());

        orderRepository.save(order);

        return remainingDebt;
    }
}
