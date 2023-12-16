package org.aviatrip.customerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FlightSeatReservationViewMock extends FlightSeatReservationView{
    private ZonedDateTime flightDepartureTimestamp;
    private FlightSeatReservationDuration flightReservationDuration;
}