package org.aviatrip.customerservice.repository;

import org.aviatrip.customerservice.dto.response.CustomerTicketView;
import org.aviatrip.customerservice.entity.CustomerTicket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<CustomerTicket, UUID> {

    @EntityGraph(attributePaths = {"flightInfo", "paymentDetails"})
    List<CustomerTicketView> findByCustomerId(UUID customerId, Pageable pageable);

    @EntityGraph(attributePaths = {"flightInfo", "paymentDetails"})
    Optional<CustomerTicketView> findByIdAndCustomerId(UUID id, UUID customerId);
}
