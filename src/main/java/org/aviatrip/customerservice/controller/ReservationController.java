package org.aviatrip.customerservice.controller;

import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.client.FlightSeatClient;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.aviatrip.customerservice.dto.response.ReservationView;
import org.aviatrip.customerservice.entity.FlightSeatReservation;
import org.aviatrip.customerservice.service.ReservationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final FlightSeatClient flightSeatClient;

    @PostMapping("/{flightSeatId}")
    public Map<String, UUID> reserveTicket(@PathVariable UUID flightSeatId, @RequestParam(defaultValue = "false") boolean payLater,
                                           @RequestHeader("Subject") UUID userId) {

        FlightSeatReservationView reservationView = flightSeatClient.getFlightSeatReservationData(flightSeatId);
        ZonedDateTime reservedUntil = reservationService.calcTimestampReservationActiveUntil(reservationView, payLater);
        FlightSeatReservation reservation = reservationService.createReservation(flightSeatId, reservedUntil, userId);

        try {
            flightSeatClient.reserveFlightSeat(flightSeatId);
        } catch (Throwable ex) {
            reservationService.deleteReservation(flightSeatId);
            throw ex;
        }
        return Map.of("reservation_id", reservation.getFlightSeatId());
    }

    @GetMapping
    public List<ReservationView> getReservations(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                 @RequestHeader("Subject") UUID userId) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        return reservationService.getReservationsViews(userId, pageRequest);
    }

    @GetMapping("/{reservationId}")
    public ReservationView getTickets(@PathVariable UUID reservationId,
                                            @RequestHeader("Subject") UUID userId) {
        return reservationService.getReservationView(reservationId, userId);
    }
}
