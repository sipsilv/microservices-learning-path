package dev.silvercapes.orderservice.dto;


import dev.silvercapes.orderservice.model.Address;
import dev.silvercapes.orderservice.model.Customer;
import dev.silvercapes.orderservice.model.OrderItem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.beans.Customizer;
import java.util.Set;

public record CreateOrderRequestDTO(
        @Valid @NotEmpty(message = "Order items shouldn't be empty") Set<OrderItemDTO> items,
        @Valid Customer customer,
        @Valid Address address
        ) {
}
