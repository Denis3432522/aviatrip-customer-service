package org.aviatrip.customerservice.repository;

import org.aviatrip.customerservice.entity.FlightInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightInfoRepository extends JpaRepository<FlightInfo, UUID> {
}
