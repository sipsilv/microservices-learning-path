package dev.silvercapes.orderservice.model;

import jakarta.validation.constraints.NotBlank;

public record Address(
        @NotBlank String addressLine1,
        @NotBlank String addressLine2,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String zipCode,
        @NotBlank String country
) {
}
