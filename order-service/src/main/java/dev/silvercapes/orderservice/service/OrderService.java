package dev.silvercapes.orderservice.service;

import dev.silvercapes.orderservice.dto.CreateOrderRequestDTO;
import dev.silvercapes.orderservice.dto.CreateOrderResponseDTO;
import dev.silvercapes.orderservice.model.Order;
import dev.silvercapes.orderservice.repository.OrderRepository;
import dev.silvercapes.orderservice.util.OrderMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

    public CreateOrderResponseDTO createOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        Order order = OrderMapper.convertToOrder(createOrderRequestDTO);
        orderRepository.save(order);
        return new CreateOrderResponseDTO(order.getOrderNumber());
    }
}
