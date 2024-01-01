package org.aviatrip.customerservice.dto.response;

import lombok.Getter;
import org.aviatrip.customerservice.enumeration.City;
import org.aviatrip.customerservice.enumeration.FlightSeatClass;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class CustomerTicketView {

    private final UUID id;
    private final boolean windowSeat;
    private final String position;
    private final Double price;
    private final FlightSeatClass seatClass;
    private final FlightInfoView flight;

    public CustomerTicketView(UUID id, boolean isWindowSeat, String position, Double paymentDetailsPaymentAmount, FlightSeatClass seatClass,
                                           UUID flightInfoId, City flightInfoSource, City flightInfoDestination, ZonedDateTime flightInfoDepartureTimestamp, ZonedDateTime flightInfoArrivalTimestamp, String flightInfoCompanyName, String flightInfoAirplaneModel) {
        this.id = id;
        this.windowSeat = isWindowSeat;
        this.position = position;
        this.price = paymentDetailsPaymentAmount;
        this.seatClass = seatClass;
        this.flight = new FlightInfoView(flightInfoId, flightInfoSource, flightInfoDestination, flightInfoDepartureTimestamp,
                flightInfoArrivalTimestamp, flightInfoCompanyName, flightInfoAirplaneModel);
    }

    public record FlightInfoView(UUID id, City source, City destination, ZonedDateTime departureTimestamp,
                                     ZonedDateTime arrivalTimestamp, String companyName, String airplaneModel) {
    }
}
