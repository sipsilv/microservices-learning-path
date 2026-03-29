package dev.silvercapes.orderservice.exceptions;

import dev.silvercapes.orderservice.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderNotFoundException(OrderNotFoundException ex){
        ErrorResponseDTO error =  ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(404).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
