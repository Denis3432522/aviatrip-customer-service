package org.aviatrip.customerservice.client;

import org.aviatrip.customerservice.dto.response.DetailedFlightSeatForTicketView;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "representative-service", qualifiers = "flightSeatClient", path = "/api/flightseats")
public interface FlightSeatClient {

    @GetMapping("/{flightSeatId}/reservation")
    FlightSeatReservationView getFlightSeatReservationView(@PathVariable UUID flightSeatId);

    @PatchMapping("/{flightSeatId}/reservation")
    void reserveFlightSeat(@PathVariable UUID flightSeatId);

    @GetMapping("/{flightSeatId}/purchase")
    DetailedFlightSeatForTicketView getFlightSeatForTicketView(@PathVariable UUID flightSeatId,
                                                               @RequestParam("detailed") boolean detailed);
}