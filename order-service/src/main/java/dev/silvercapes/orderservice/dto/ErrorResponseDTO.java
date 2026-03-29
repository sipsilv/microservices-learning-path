package dev.silvercapes.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    @JsonProperty("status_code")
    private int statusCode;
}