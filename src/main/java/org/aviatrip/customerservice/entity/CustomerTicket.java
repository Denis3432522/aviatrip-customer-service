package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aviatrip.customerservice.enumeration.FlightSeatClass;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "customer_tickets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CustomerTicket implements Persistable<UUID> {

    @Column(name = "ticket_id")
    @Id
    private UUID id;

    @Column(nullable = false)
    private String position;

    @Column(name = "is_window_seat", nullable = false)
    private boolean isWindowSeat;

    @Column(name = "seat_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightSeatClass seatClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightInfo flightInfo;

    @Setter
    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    CustomerTicketPaymentDetails paymentDetails;

    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return isNew;
    }
}
