package dev.silvercapes.catalogservice.exceptions;

import dev.silvercapes.catalogservice.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(ProductNotFoundException ex){
        ErrorResponseDTO error =  ErrorResponseDTO.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .statusCode(404)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
