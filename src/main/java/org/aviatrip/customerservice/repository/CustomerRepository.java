package org.aviatrip.customerservice.repository;


import org.aviatrip.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Modifying
    @Query(value = "DELETE FROM Customer c where c.id = ?1")
    int deleteCustomerById(UUID id);
}
