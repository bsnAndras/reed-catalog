package com.bsnandras.reedcatalog.utils;

import com.bsnandras.reedcatalog.utils.paymentHandlers.ManualPaymentHandler;
import com.bsnandras.reedcatalog.utils.paymentHandlers.PaymentHandler;
import com.bsnandras.reedcatalog.utils.paymentHandlers.PaymentHandlerStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceFactory {
    private final ManualPaymentHandler manualPaymentHandler;

    public PaymentHandler getPaymentHandler(PaymentHandlerStrategy strategy) {
        return switch (strategy) {
            case MANUAL -> manualPaymentHandler;
            default -> throw new IllegalArgumentException("Unknown PaymentHandlerStrategy: " + strategy);
        };
    }
}

