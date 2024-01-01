package org.aviatrip.customerservice.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.aviatrip.customerservice.enumeration.City;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
public class DetailedFlightSeatForTicketView extends FlightSeatForTicketView {

    @NotNull
    private UUID flightId;
    @NotNull
    private City flightSource;
    @NotNull
    private City flightDestination;
    @NotNull
    private ZonedDateTime flightDepartureTimestamp;
    @NotNull
    private ZonedDateTime flightArrivalTimestamp;
    @NotNull
    private String companyName;
    @NotNull
    private String airplaneModel;
}
