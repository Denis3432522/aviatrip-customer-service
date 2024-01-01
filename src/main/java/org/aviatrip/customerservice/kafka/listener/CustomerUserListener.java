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

    @KafkaListener(topics = "${spring.kafka.custom.consumer.customer-user.topic}",
            groupId = "${spring.kafka.custom.consumer.customer-user.groupId}",
            containerFactory = "mainListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.customerservice.kafka.event.CustomerUserEvent"
    )
    public void handleCustomerUserEvent(@Payload @Valid CustomerUserEvent event) {

        log.debug(">>> Customer creation started: {}", event);
        dispatchCustomerUserEvent(event);
        log.debug("<<< Customer created: {}", event);
    }

    @KafkaListener(topics = "retry-" + "${spring.kafka.custom.consumer.customer-user.topic}",
            groupId = "retry-" + "${spring.kafka.custom.consumer.customer-user.groupId}",
            containerFactory = "retryListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.customerservice.kafka.event.CustomerUserEvent"
    )
    public void handleRetryCustomerUserEvent(@Payload @Valid CustomerUserEvent event) {

        log.debug(">>> RETRY Customer creation started: {}", event);
        dispatchCustomerUserEvent(event);
        log.debug(">>> RETRY Customer created: {}", event);
    }

    public void dispatchCustomerUserEvent(CustomerUserEvent event) {
        CustomerUserEventType type = event.getEventTypeEnum();
        if(CustomerUserEventType.CREATED.equals(type))
            customerService.createCustomer(event.getUserId());
        else if(CustomerUserEventType.DELETED.equals(type))
            customerService.deleteCustomer(event.getUserId());
    }
}
