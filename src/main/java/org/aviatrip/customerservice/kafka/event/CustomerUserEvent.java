package org.aviatrip.customerservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aviatrip.customerservice.enumeration.CustomerUserEventType;
import org.aviatrip.customerservice.validation.annotation.EnumString;

import java.util.UUID;



@Getter
@Setter
@ToString
public class CustomerUserEvent {

    @JsonProperty("user_id")
    @NotNull
    private UUID userId;

    @JsonProperty("event_type")
    @NotNull
    @EnumString(enumClazz = CustomerUserEventType.class)
    private String eventType;

    @JsonIgnore
    public CustomerUserEventType getEventTypeEnum() {
        return CustomerUserEventType.valueOf(eventType.toUpperCase());
    }
}
