package org.aviatrip.customerservice.repository;

import org.aviatrip.customerservice.dto.response.ReservationView;
import org.aviatrip.customerservice.entity.FlightSeatReservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightSeatReservationRepository extends JpaRepository<FlightSeatReservation, UUID> {
    <R> Optional<R> getReservationViewByIdAndCustomerId(UUID id, UUID customerId, Class<R> type);

    List<ReservationView> getReservationViewsByCustomerId(UUID customerId, Pageable pageable);

    @Modifying
    @Query("delete from FlightSeatReservation r where r.flightSeatId = :flightSeatId")
    int deleteReservationById(UUID flightSeatId);

    @Modifying
    @Query(value = "delete from flight_seat_reservations r where r.reserved_until < :timestamp returning r.flight_seat_id", nativeQuery = true)
    List<UUID> deleteAllByReservedUntilLessThan(ZonedDateTime timestamp);
}
