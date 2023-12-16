package org.aviatrip.customerservice.dto.response;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface ReservationView {

    UUID getFlightSeatId();
    ZonedDateTime getReservedUntil();
}
