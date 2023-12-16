package org.aviatrip.customerservice.service;

import jakarta.transaction.Transactional;
import org.aviatrip.customerservice.config.web.ReservationProperties;
import org.aviatrip.customerservice.dto.response.FlightSeatReservationView;
import org.aviatrip.customerservice.dto.response.ReservationView;
import org.aviatrip.customerservice.dto.response.error.ErrorResponse;
import org.aviatrip.customerservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.customerservice.entity.FlightSeatReservation;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationDuration;
import org.aviatrip.customerservice.exception.BadRequestException;
import org.aviatrip.customerservice.exception.ResourceNotFoundException;
import org.aviatrip.customerservice.repository.CustomerRepository;
import org.aviatrip.customerservice.repository.FlightSeatReservationRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class ReservationService {

    private final ReservationProperties reservationProps;
    private final FlightSeatReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;

    public ReservationService(ReservationProperties reservationProps, FlightSeatReservationRepository reservationRepository, CustomerRepository customerRepository) {
        this.reservationProps = reservationProps;
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
    }

    public ZonedDateTime calcTimestampReservationActiveUntil(FlightSeatReservationView reservationView, boolean payLater) {
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
        long secondsUntilFlightDeparture = Duration.between(currentDateTime, reservationView.getFlightDepartureTimestamp()).toSeconds();

        if(payLater) {
            if(reservationView.getFlightReservationDuration().equals(FlightSeatReservationDuration.NOT_SUPPORT))
                throw new BadRequestException(ErrorResponse.of("Current flight doesn't support pay-later option"));

            long flightReservationDuration = reservationView.getFlightReservationDuration().getSeconds();
            long remainingSecondsToReserveFor = secondsUntilFlightDeparture - reservationProps.getSecondsUntilFlightDepartureReservationAvailableWhenPayLaterOptionEnabled();

            if(remainingSecondsToReserveFor <= reservationProps.getMinimalFlightSeatReservationDurationInSeconds())
                throw new BadRequestException(ErrorResponse.of("It is too late to use pay-later option"));

            long actualReservationDuration = Math.min(remainingSecondsToReserveFor, flightReservationDuration);
            return currentDateTime.plusSeconds(actualReservationDuration);
        }

        if(secondsUntilFlightDeparture < reservationProps.getSecondsUntilFlightDepartureReservationAvailable())
            throw new BadRequestException(ErrorResponse.of("It is too late to reserve flight seat"));

        return currentDateTime.plusSeconds(reservationProps.getMinimalFlightSeatReservationDurationInSeconds());
    }

    @Transactional
    public FlightSeatReservation createReservation(UUID flightSeatId, ZonedDateTime reservedUntil, UUID customerId) {
        return reservationRepository.save(FlightSeatReservation.builder()
                .flightSeatId(flightSeatId)
                .reservedUntil(reservedUntil)
                .customer(customerRepository.getReferenceById(customerId))
                .build());
    }

    public List<ReservationView> getReservationsViews(UUID customerId, Pageable pageRequest) {
        return reservationRepository.getReservationViewsByCustomerId(customerId, pageRequest);
    }

    public ReservationView getReservationView(UUID reservationId, UUID customerId) {
        return reservationRepository.getReservationViewByIdAndCustomerId(reservationId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundResponse
                        .of("Reservation with ID " + reservationId))
                );
    }

    @Transactional
    public void deleteReservation(UUID flightSeatId) {
        reservationRepository.deleteReservationById(flightSeatId);
    }
}
