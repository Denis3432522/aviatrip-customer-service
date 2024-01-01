package org.aviatrip.customerservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.PaymentSystem;
import org.aviatrip.customerservice.validation.annotation.FormattableEnumString;

import java.util.UUID;

@Getter
public class TicketPurchaseRequest {

    @NotNull
    private UUID reservationId;

    @NotNull
    @FormattableEnumString(value = PaymentSystem.class)
    private String paymentSystem;

    @NotNull
    @Size(min = 10, max = 48)
    private String paymentSystemIdentifier;

    public PaymentSystem getPaymentSystem() {
        return PaymentSystem.of(paymentSystem);
    }
}
