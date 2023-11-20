package org.aviatrip.customerservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.entity.Customer;
import org.aviatrip.customerservice.exception.FatalKafkaException;
import org.aviatrip.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public void createCustomer(UUID userId) {
        if(repository.existsById(userId))
            throw new FatalKafkaException("Customer with ID " + userId + " already exists");

        repository.save(new Customer(userId));
    }

    @Transactional
    public void deleteCustomer(UUID userId) {
        int rowCount = repository.deleteCustomerById(userId);
        if(rowCount == 0)
            throw new FatalKafkaException("Customer with ID " + userId + " not found");
    }
}

