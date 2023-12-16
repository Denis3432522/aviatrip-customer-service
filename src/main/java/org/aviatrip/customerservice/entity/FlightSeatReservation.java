package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight_seat_reservations")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FlightSeatReservation implements Persistable<UUID> {

    @Column(name = "flight_seat_id")
    @Id
    private UUID flightSeatId;

    @Column(name = "reserved_until")
    private ZonedDateTime reservedUntil;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Override
    public UUID getId() {
        return flightSeatId;
    }

    @Override
    public boolean isNew() {
        return version == 0;
    }
}
