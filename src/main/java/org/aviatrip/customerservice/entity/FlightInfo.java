package org.aviatrip.customerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aviatrip.customerservice.enumeration.City;
import org.hibernate.annotations.Immutable;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight_informations")
@Immutable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightInfo {

    @Column(name = "flight_id")
    @Id
    private UUID id;

    @Column(name = "source", nullable = false)
    private City source;

    @Column(name = "destintation", nullable = false)
    private City destination;

    @Column(name = "departure_timestamp", nullable = false)
    private ZonedDateTime departureTimestamp;

    @Column(name = "arrival_timestamp", nullable = false)
    private ZonedDateTime arrivalTimestamp;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "airplane_model", nullable = false)
    private String airplaneModel;
}
