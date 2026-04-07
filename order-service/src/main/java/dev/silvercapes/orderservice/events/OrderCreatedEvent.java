package dev.silvercapes.orderservice.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.silvercapes.orderservice.model.Address;
import dev.silvercapes.orderservice.model.Customer;
import dev.silvercapes.orderservice.model.OrderItem;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record OrderCreatedEvent(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("order_number") String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address address,
        @JsonProperty("created-at") LocalDateTime createdAt
) {
}
