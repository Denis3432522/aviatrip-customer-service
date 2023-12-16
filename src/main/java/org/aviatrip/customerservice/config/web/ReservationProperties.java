package org.aviatrip.customerservice.config.web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "ticket")
@Validated
@Setter
@Getter
public class ReservationProperties {
    @NotNull
    private Integer minimalFlightSeatReservationDurationInSeconds;
    @NotNull
    private Integer secondsUntilFlightDepartureReservationAvailable;
    @NotNull
    private Integer secondsUntilFlightDepartureReservationAvailableWhenPayLaterOptionEnabled;
}
