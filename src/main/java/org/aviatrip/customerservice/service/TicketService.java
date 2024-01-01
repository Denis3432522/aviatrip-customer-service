package org.aviatrip.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.dto.request.TicketPurchaseRequest;
import org.aviatrip.customerservice.dto.response.CustomerTicketView;
import org.aviatrip.customerservice.dto.response.FlightSeatForTicketView;
import org.aviatrip.customerservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.customerservice.entity.Customer;
import org.aviatrip.customerservice.entity.CustomerTicket;
import org.aviatrip.customerservice.entity.CustomerTicketPaymentDetails;
import org.aviatrip.customerservice.entity.FlightInfo;
import org.aviatrip.customerservice.exception.ResourceNotFoundException;
import org.aviatrip.customerservice.repository.TicketRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public CustomerTicket createTicket(FlightSeatForTicketView flightSeat, Customer customer, FlightInfo flightInfo) {
         return CustomerTicket.builder()
                .id(flightSeat.getId())
                .position(flightSeat.getPosition())
                .isWindowSeat(flightSeat.isWindowSeat())
                .seatClass(flightSeat.getSeatClass())
                .customer(customer)
                .flightInfo(flightInfo)
                .isNew(true)
                .build();
    }

    public CustomerTicketPaymentDetails createTicketPaymentDetails(TicketPurchaseRequest request, double ticketPrice,
                                                                    CustomerTicket customerTicket) {
        return CustomerTicketPaymentDetails.builder()
                .paymentSystemIdentifier(request.getPaymentSystemIdentifier())
                .paymentSystem(request.getPaymentSystem())
                .paymentAmount(ticketPrice)
                .ticket(customerTicket)
                .build();
    }

    public List<CustomerTicketView> getCustomerTickets(UUID customerId, Pageable pageable) {
         return ticketRepository.findByCustomerId(customerId, pageable);
    }

    public CustomerTicketView getCustomerTicket(UUID ticketId, UUID customerId) {
        return ticketRepository.findByIdAndCustomerId(ticketId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundResponse.of("Ticket with ID " + ticketId)));
    }
}
