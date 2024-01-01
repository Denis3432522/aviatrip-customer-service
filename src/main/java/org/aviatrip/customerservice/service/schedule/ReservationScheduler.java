package org.aviatrip.customerservice.service.schedule;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.enumeration.FlightSeatReservationEventType;
import org.aviatrip.customerservice.kafka.event.FlightSeatReservationEvent;
import org.aviatrip.customerservice.kafka.producer.FlightSeatReservationProducer;
import org.aviatrip.customerservice.repository.FlightSeatReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduler {

    private final FlightSeatReservationRepository reservationRepository;
    private final FlightSeatReservationProducer producer;

    @Scheduled(initialDelay = 1000*5, fixedDelay = 1000*60)
    @Transactional
    public void deleteAllExpiredReservations() {
        reservationRepository.deleteAllByReservedUntilLessThan(ZonedDateTime.now(ZoneId.of("UTC")))
                .stream()
                .map(id -> FlightSeatReservationEvent.builder()
                        .flightSeatId(id)
                        .eventType(FlightSeatReservationEventType.EXPIRED)
                        .build())
                .forEach(producer::sendFlightSeatReservationTicket);
    }
}