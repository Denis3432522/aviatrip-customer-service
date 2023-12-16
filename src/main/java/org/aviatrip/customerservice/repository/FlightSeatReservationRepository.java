package org.aviatrip.customerservice.repository;

import org.aviatrip.customerservice.dto.response.ReservationView;
import org.aviatrip.customerservice.entity.FlightSeatReservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightSeatReservationRepository extends JpaRepository<FlightSeatReservation, UUID> {
    Optional<ReservationView> getReservationViewByIdAndCustomerId(UUID id, UUID customerId);

    List<ReservationView> getReservationViewsByCustomerId(UUID customerId, Pageable pageable);

    @Modifying
    @Query("delete from FlightSeatReservation r where r.flightSeatId = :flightSeatId")
    void deleteReservationById(UUID flightSeatId);
}
