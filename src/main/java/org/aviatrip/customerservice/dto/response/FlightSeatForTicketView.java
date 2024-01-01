package org.aviatrip.customerservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.aviatrip.customerservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Getter
public class FlightSeatForTicketView {

    @NotNull
    private UUID id;
    @NotNull
    @JsonProperty("window_seat")
    private boolean isWindowSeat;
    @NotNull
    private String position;
    @NotNull
    private Double price;
    @NotNull
    private FlightSeatClass seatClass;
}
