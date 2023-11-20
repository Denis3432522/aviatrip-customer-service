package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight_seat_reservations")
@Getter
public class FlightSeatReservation {

    @Column(name = "flight_seat_id")
    @Id
    private UUID flightSeatId;

    @Column(name = "reserved_until")
    private ZonedDateTime reservedUntil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    protected FlightSeatReservation() {}

    public FlightSeatReservation(UUID flightSeatId, ZonedDateTime reservedUntil, Customer customer) {
        this.flightSeatId = flightSeatId;
        this.reservedUntil = reservedUntil;
        this.customer = customer;
    }
}
