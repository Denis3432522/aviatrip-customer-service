package org.aviatrip.customerservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aviatrip.customerservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Entity
@Table(name = "customer_ticket")
@Getter
public class CustomerTicket {

    @Column(name = "ticket_id")
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String position;

    @Column(name = "is_window_seat", nullable = false)
    private boolean isWindowSeat;

    @Column(name = "seat_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightSeatClass seatClass;

    @Column(name = "flight_id")
    private UUID flightId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Setter
    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    CustomerTicketPaymentDetails paymentDetails;

    protected CustomerTicket() {}

    public CustomerTicket(String position, boolean isWindowSeat, FlightSeatClass seatClass, UUID flightId, Customer customer) {
        this.position = position;
        this.isWindowSeat = isWindowSeat;
        this.seatClass = seatClass;
        this.flightId = flightId;
        this.customer = customer;
    }}
