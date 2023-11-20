package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.domain.Persistable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
public class Customer implements Persistable<UUID> {

    @Column(name = "user_id")
    @Id
    private UUID id;

    @OneToMany(mappedBy = "customer")
    private Set<FlightSeatReservation> reservations = new HashSet<>();

    @Version
    private long version;

    protected Customer() {}

    public Customer(UUID id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return version == 0;
    }
}
