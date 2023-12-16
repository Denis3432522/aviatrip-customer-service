package org.aviatrip.customerservice.client;

import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "representative-service", qualifiers = "flightSeatClient")
public interface FlightSeatClient {

    @GetMapping("/api/flightseats/{flightSeatId}/reservation")
    FlightSeatReservationView getFlightSeatReservationData(@PathVariable UUID flightSeatId);

    @PatchMapping("/api/flightseats/{flightSeatId}/reservation")
    void reserveFlightSeat(@PathVariable UUID flightSeatId);
}
