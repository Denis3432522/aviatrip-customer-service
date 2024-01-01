package org.aviatrip.customerservice.kafka.event;

import lombok.Builder;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationEventType;

import java.util.UUID;

@Builder
@Getter
public class FlightSeatReservationEvent {

    private UUID flightSeatId;

    private FlightSeatReservationEventType eventType;
}
