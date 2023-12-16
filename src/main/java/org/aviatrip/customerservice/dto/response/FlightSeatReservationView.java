package org.aviatrip.customerservice.dto.response;

import lombok.Getter;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;

@Getter
public class FlightSeatReservationView {

    private ZonedDateTime flightDepartureTimestamp;
    private FlightSeatReservationDuration flightReservationDuration;
}
