package org.aviatrip.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.client.FlightSeatClient;
import org.aviatrip.customerservice.dto.request.TicketPurchaseRequest;
import org.aviatrip.customerservice.dto.response.CustomerTicketView;
import org.aviatrip.customerservice.entity.CustomerTicket;
import org.aviatrip.customerservice.entity.FlightInfo;
import org.aviatrip.customerservice.entity.FlightSeatReservation;
import org.aviatrip.customerservice.exception.InternalServerErrorException;
import org.aviatrip.customerservice.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketManagementService ticketManagementService;
    private final TicketService ticketService;
    private final ReservationService reservationService;
    private final FlightInfoService flightInfoService;
    private final FlightSeatClient flightSeatClient;
    private final FlightSeatValidator flightSeatValidator;

    @PostMapping
    public Map<String, UUID> purchaseTicket(@RequestBody @Valid TicketPurchaseRequest request, @RequestHeader("Subject") UUID userId) {

        var reservation = reservationService.getReservationView(request.getReservationId(), userId, FlightSeatReservation.class);
        reservationService.assertReservationNotExpired(reservation);

        boolean detailedFlightSeatInfo = !flightInfoService.existsFlightInfo(reservation.getFlightId());
        var flightSeatForTicketView = flightSeatClient.getFlightSeatForTicketView(request.getReservationId(), detailedFlightSeatInfo);
        flightSeatValidator.validate(flightSeatForTicketView);

        FlightInfo flightInfo;
        if(detailedFlightSeatInfo)
            flightInfo = flightInfoService.createFlightInfo(flightSeatForTicketView);
        else
            flightInfo = flightInfoService.getFlightInfo(reservation.getFlightId())
                    .orElseThrow(InternalServerErrorException::new);

        CustomerTicket ticket = ticketManagementService.purchaseTicket(request, flightSeatForTicketView, flightInfo, userId);

        return Map.of("ticket_id", ticket.getId());
    }

    @GetMapping
    public List<CustomerTicketView> getTickets(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                               @RequestHeader("Subject") UUID userId) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        return ticketService.getCustomerTickets(userId, pageRequest);
    }

    @GetMapping("/{ticketId}")
    public CustomerTicketView getTicket(@PathVariable UUID ticketId, @RequestHeader("Subject") UUID userId) {
        return ticketService.getCustomerTicket(ticketId, userId);
    }
}
