package org.aviatrip.customerservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FlightSeatClass {
    ECONOMY,
    COMFORT,
    BUSINESS,
    FIRST_CLASS;

    @JsonValue
    public String getSerializedFlightSeatClass() {
        return this.name().toLowerCase();
    }
}
