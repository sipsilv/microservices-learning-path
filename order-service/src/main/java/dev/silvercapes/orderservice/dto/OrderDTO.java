package dev.silvercapes.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.silvercapes.orderservice.enums.OrderStatus;
import dev.silvercapes.orderservice.model.Address;
import dev.silvercapes.orderservice.model.Customer;
import dev.silvercapes.orderservice.model.OrderItem;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record OrderDTO(
        @JsonProperty("order_number") String orderNumber,
        String user,
        Set<OrderItem> items,
        Customer customer,
        Address address,
        @JsonProperty("order_status") OrderStatus orderStatus,
        String comments,
        @JsonProperty("created_at") LocalDateTime createdAt
) {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
