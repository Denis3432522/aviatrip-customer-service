package org.aviatrip.customerservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.dto.request.TicketPurchaseRequest;
import org.aviatrip.customerservice.dto.response.FlightSeatForTicketView;
import org.aviatrip.customerservice.entity.CustomerTicket;
import org.aviatrip.customerservice.entity.FlightInfo;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.aviatrip.customerservice.repository.CustomerRepository;
import org.aviatrip.customerservice.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketManagementService {

    private final TicketService ticketService;
    private final ReservationService reservationService;

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerTicket purchaseTicket(TicketPurchaseRequest request, FlightSeatForTicketView flightSeat,
                                         FlightInfo flightInfo, UUID customerId) {

        var customerTicket = ticketService.createTicket(flightSeat, customerRepository.getReferenceById(customerId), flightInfo);
        var ticketPaymentDetails = ticketService.createTicketPaymentDetails(request, flightSeat.getPrice(), customerTicket);

        customerTicket.setPaymentDetails(ticketPaymentDetails);

        ticketRepository.save(customerTicket);

        if(!reservationService.deleteExistentReservation(request.getReservationId()))
            throw new InternalServerErrorException();

        return customerTicket;
    }
}
