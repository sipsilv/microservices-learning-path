package dev.silvercapes.orderservice.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.silvercapes.orderservice.model.Address;
import dev.silvercapes.orderservice.model.Customer;
import dev.silvercapes.orderservice.model.OrderItem;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderErrorEvent (
        @JsonProperty("event_id") String eventId,
        @JsonProperty("order_number") String orderNumber,
        Set<OrderItem> items,
        Customer customer,
        Address address,
        String reason,
        @JsonProperty("created-at") LocalDateTime createdAt
){
}
