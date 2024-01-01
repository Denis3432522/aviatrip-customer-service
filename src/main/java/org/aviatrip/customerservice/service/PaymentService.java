package org.aviatrip.customerservice.service;

import org.aviatrip.customerservice.enumeration.PaymentSystem;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    public void payTicket(UUID customerId, String paymentSystemIdentifier, PaymentSystem paymentSystem, String tickerPrice) {

    }
}
