package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight_seat_reservations")
@Immutable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class FlightSeatReservation implements Persistable<UUID> {

    @Column(name = "flight_seat_id")
    @Id
    private UUID flightSeatId;

    @Column(name = "reserved_until", nullable = false)
    private ZonedDateTime reservedUntil;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "flight_id", nullable = false)
    private UUID flightId;

    @Transient
    private boolean isNew;

    @Override
    public UUID getId() {
        return flightSeatId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
