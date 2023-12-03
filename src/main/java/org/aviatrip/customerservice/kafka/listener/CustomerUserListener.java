package org.aviatrip.customerservice.kafka.listener;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.customerservice.enumeration.CustomerUserEventType;
import org.aviatrip.customerservice.kafka.event.CustomerUserEvent;
import org.aviatrip.customerservice.service.CustomerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class CustomerUserListener {

    private final CustomerService customerService;

    @KafkaListener(topics = "${kafka.customer-user.main-topic}",
            groupId = "${kafka.customer-user.main-groupId}",
            containerFactory = "mainCustomerUserConsumerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.customerservice.kafka.event.CustomerUserEvent"
    )
    public void handleMainCustomerUserEvent(@Payload @Valid CustomerUserEvent event) {

        log.debug(">>> Customer creation started: {}", event);
        handleCustomerUserEvent(event);
        log.debug("<<< Customer created: {}", event);
    }

    @KafkaListener(topics = "${kafka.customer-user.retry-topic}",
            groupId = "${kafka.customer-user.retry-groupId}",
            containerFactory = "retryCustomerUserConsumerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.customerservice.kafka.event.CustomerUserEvent"
    )
    public void handleRetryCustomerUserEvent(@Payload @Valid CustomerUserEvent event) {

        log.debug(">>> RETRY Customer creation started: {}", event);
        handleCustomerUserEvent(event);
        log.debug(">>> RETRY Customer created: {}", event);
    }

    public void handleCustomerUserEvent(CustomerUserEvent event) {
        CustomerUserEventType type = event.getEventTypeEnum();
        if(CustomerUserEventType.CREATED.equals(type))
            customerService.createCustomer(event.getUserId());
        else if(CustomerUserEventType.DELETED.equals(type))
            customerService.deleteCustomer(event.getUserId());
    }
}
