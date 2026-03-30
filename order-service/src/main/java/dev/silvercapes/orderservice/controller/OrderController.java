package dev.silvercapes.orderservice.controller;

import dev.silvercapes.orderservice.dto.CreateOrderRequestDTO;
import dev.silvercapes.orderservice.dto.CreateOrderResponseDTO;
import dev.silvercapes.orderservice.security.SecurityService;
import dev.silvercapes.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;
    private final Logger log= LoggerFactory.getLogger(OrderController.class);

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponseDTO createOrder(@Valid @RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        String username = securityService.getLoginUsername();
        log.info("Creating order for user : {}", username);
        return orderService.createOrder(createOrderRequestDTO);
    }
}
