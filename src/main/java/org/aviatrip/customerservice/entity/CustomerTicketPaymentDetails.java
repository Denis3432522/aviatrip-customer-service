package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.PaymentSystem;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Table(name = "ticket_payment_details")
@Immutable
@Getter
public class CustomerTicketPaymentDetails {

    @Column(name = "ticket_payment_details_id")
    @Id
    private UUID id;

    @Column(name = "payment_system")
    @Enumerated(EnumType.STRING)
    private PaymentSystem paymentSystem;

    @Column(name = "payment_system_identifier")
    private String paymentSystemIdentifier;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @OneToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(name = "ticker_id", nullable = false, unique = true)
    @MapsId
    private CustomerTicket ticket;

    protected CustomerTicketPaymentDetails() {}

    public CustomerTicketPaymentDetails(PaymentSystem paymentSystem, String paymentSystemIdentifier, int paymentAmount, CustomerTicket ticket) {
        this.paymentSystem = paymentSystem;
        this.paymentSystemIdentifier = paymentSystemIdentifier;
        this.paymentAmount = paymentAmount;
        this.ticket = ticket;
    }
}
