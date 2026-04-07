package dev.silvercapes.orderservice.util;

import dev.silvercapes.orderservice.dto.CreateOrderRequestDTO;
import dev.silvercapes.orderservice.dto.OrderDTO;
import dev.silvercapes.orderservice.dto.OrderItemDTO;
import dev.silvercapes.orderservice.enums.OrderStatus;
import dev.silvercapes.orderservice.model.Order;
import dev.silvercapes.orderservice.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public static Order convertToOrder(CreateOrderRequestDTO createOrderRequestDTO) {
        Set<OrderItem> items = new HashSet<>();
        Order newOrder = new Order();
        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setOrderStatus(OrderStatus.NEW);
        newOrder.setCustomer(createOrderRequestDTO.customer());
        newOrder.setAddress(createOrderRequestDTO.address());
        for (OrderItemDTO item : createOrderRequestDTO.items()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            items.add(orderItem);
        }
        newOrder.setItems(items);
        return newOrder;
    }

    public static OrderDTO convertToDTO(Order order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> OrderItem.builder()
                        .code(item.getCode()).name(item.getName()).price(item.getPrice()).quantity(item.getQuantity()).order(item.getOrder()).build())
                .collect(Collectors.toSet());

        return OrderDTO.builder()
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .user(order.getCustomer().name())
                .comments(order.getComments())
                .createdAt(order.getCreatedAt())
                .items(order.getItems()).customer(order.getCustomer()).address(order.getAddress()).build();
                
    }
}
