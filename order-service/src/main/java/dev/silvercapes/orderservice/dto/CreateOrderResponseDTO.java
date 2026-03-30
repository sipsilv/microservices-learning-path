package dev.silvercapes.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateOrderResponseDTO(
        @JsonProperty("order_number") String orderNum
        ) {
}
