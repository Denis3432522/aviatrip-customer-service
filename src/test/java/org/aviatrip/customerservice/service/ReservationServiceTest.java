package org.aviatrip.customerservice.service;

import lombok.Builder;
import lombok.Getter;
import org.aviatrip.customerservice.config.web.ReservationProperties;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationViewMock;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationDuration;
import org.aviatrip.customerservice.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationProperties reservationProps;

    @Test
    void calcTimestampReservationActiveUntil() {

        when(reservationProps.getMinimalFlightSeatReservationDurationInSeconds()).thenReturn(600);
        when(reservationProps.getSecondsUntilFlightDepartureReservationAvailableWhenPayLaterOptionEnabled()).thenReturn(7200);

        ZonedDateTime flightDepartureTimestamp = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(8);
        ZonedDateTime expectedReservedUntil = flightDepartureTimestamp.minusHours(2).withZoneSameInstant(ZoneId.of("Asia/Vladivostok"));

        FlightSeatReservationView reservationView = FlightSeatReservationViewMock.builder()
                .flightReservationDuration(FlightSeatReservationDuration.SIX_HOURS)
                .flightDepartureTimestamp(flightDepartureTimestamp)
                .build();

        ZonedDateTime reservedUntil = assertDoesNotThrow(() -> reservationService.calcTimestampReservationActiveUntil(reservationView, true));

        Duration duration = Duration.between(expectedReservedUntil, reservedUntil);

        assertThat(duration.toSeconds()).isBetween(-10L, 10L);
    }

    @ParameterizedTest
    @MethodSource("getValidTestDataStream")
    void testCalcTimestampReservationActiveUntilWhenDataValid(TestData testData) {
        lenient().when(reservationProps.getMinimalFlightSeatReservationDurationInSeconds()).thenReturn(600);
        lenient().when(reservationProps.getSecondsUntilFlightDepartureReservationAvailableWhenPayLaterOptionEnabled()).thenReturn(7200);
        lenient().when(reservationProps.getSecondsUntilFlightDepartureReservationAvailable()).thenReturn(4200);

        FlightSeatReservationView reservationView = FlightSeatReservationViewMock.builder()
                .flightReservationDuration(testData.getDuration())
                .flightDepartureTimestamp(testData.getFlightDeparture())
                .build();

        ZonedDateTime reservedUntil = assertDoesNotThrow(() -> reservationService.calcTimestampReservationActiveUntil(reservationView, testData.payLater));

        Duration duration = Duration.between(testData.expectedReservedUntil, reservedUntil);

        assertThat(duration.toSeconds()).isBetween(-10L, 10L);
    }

    @ParameterizedTest
    @MethodSource("getInvalidTestDataStream")
    void testCalcTimestampReservationActiveUntilWhenDataInvalid(TestData testData) {
        lenient().when(reservationProps.getMinimalFlightSeatReservationDurationInSeconds()).thenReturn(600);
        lenient().when(reservationProps.getSecondsUntilFlightDepartureReservationAvailableWhenPayLaterOptionEnabled()).thenReturn(7200);
        lenient().when(reservationProps.getSecondsUntilFlightDepartureReservationAvailable()).thenReturn(4200);

        FlightSeatReservationView reservationView = FlightSeatReservationViewMock.builder()
                .flightReservationDuration(testData.getDuration())
                .flightDepartureTimestamp(testData.getFlightDeparture())
                .build();

        assertThrows(BadRequestException.class ,() -> reservationService.calcTimestampReservationActiveUntil(reservationView, testData.payLater));

    }

    static Stream<TestData> getValidTestDataStream() {
        return Stream.of(
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(20))
                        .expectedReservedUntil(ZonedDateTime.now().plusMinutes(10))
                        .duration(FlightSeatReservationDuration.NOT_SUPPORT)
                        .payLater(false)
                        .build(),
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(70))
                        .expectedReservedUntil(ZonedDateTime.now().plusHours(12))
                        .duration(FlightSeatReservationDuration.TWELVE_HOURS)
                        .payLater(true)
                        .build(),
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(8))
                        .expectedReservedUntil(ZonedDateTime.now().plusHours(6))
                        .duration(FlightSeatReservationDuration.TWELVE_HOURS)
                        .payLater(true)
                        .build(),
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(2).plusMinutes(11))
                        .expectedReservedUntil(ZonedDateTime.now().plusMinutes(11))
                        .duration(FlightSeatReservationDuration.SIX_HOURS)
                        .payLater(true)
                        .build()
        );
    }

    static Stream<TestData> getInvalidTestDataStream() {
        return Stream.of(
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(20))
                        .expectedReservedUntil(ZonedDateTime.now().plusHours(10))
                        .duration(FlightSeatReservationDuration.NOT_SUPPORT)
                        .payLater(true)
                        .build(),
                TestData.builder()
                        .flightDeparture(ZonedDateTime.now().plusHours(2))
                        .expectedReservedUntil(ZonedDateTime.now().plusHours(1))
                        .duration(FlightSeatReservationDuration.SIX_HOURS)
                        .payLater(true)
                        .build()
        );
    }

    @Builder
    @Getter
    static class TestData {
        private ZonedDateTime flightDeparture;
        private ZonedDateTime expectedReservedUntil;
        private boolean payLater;
        private FlightSeatReservationDuration duration;
    }
}