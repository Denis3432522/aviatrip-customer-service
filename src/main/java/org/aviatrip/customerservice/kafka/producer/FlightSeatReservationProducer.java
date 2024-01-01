package org.aviatrip.customerservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.aviatrip.customerservice.config.kafka.CustomKafkaProps;
import org.aviatrip.customerservice.kafka.event.FlightSeatReservationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightSeatReservationProducer {
    private final CustomKafkaProps props;
    private final KafkaTemplate<String, Object> template;

    public void sendFlightSeatReservationTicket(FlightSeatReservationEvent event) {
        template.send(props.getProducer().getFlightseatReservationTopic(), event.getFlightSeatId().toString(), event);
    }
}
