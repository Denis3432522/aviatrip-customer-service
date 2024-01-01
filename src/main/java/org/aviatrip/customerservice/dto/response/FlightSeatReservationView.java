package org.aviatrip.customerservice.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class FlightSeatReservationView {

    @NotNull
    private ZonedDateTime flightDepartureTimestamp;
    @NotNull
    private FlightSeatReservationDuration flightReservationDuration;
    @NotNull
    private UUID flightId;
}
