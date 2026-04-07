package dev.silvercapes.orderservice.model;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


public record Customer (
    @NotBlank(message = "Customer name is required")
    String name,
    @NotBlank(message = "Customer email is required")
    String email,
    @NotBlank(message = "Customer phone is required")
    String phone
){}
