package dev.silvercapes.orderservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.silvercapes.orderservice.enums.OrderEventType;
import dev.silvercapes.orderservice.events.OrderCancelledEvent;
import dev.silvercapes.orderservice.events.OrderCreatedEvent;
import dev.silvercapes.orderservice.events.OrderDeliveredEvent;
import dev.silvercapes.orderservice.events.OrderErrorEvent;
import dev.silvercapes.orderservice.model.OrderEvent;
import dev.silvercapes.orderservice.publishers.OrderEventPublisher;
import dev.silvercapes.orderservice.repository.OrderEventRepository;
import dev.silvercapes.orderservice.util.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.math.raw.Mod;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor

public class OrderEventService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;

    void save(OrderCreatedEvent event){
        OrderEvent orderEvent = OrderEvent.builder()
                .orderNumber(event.orderNumber()).eventId(event.eventId()).eventType(OrderEventType.ORDER_CREATED).createdAt(event.createdAt()).payload(toJsonPayload(event)).build();
        orderEventRepository.save(orderEvent);
    }
    
    void save(OrderDeliveredEvent event){
        OrderEvent orderEvent = OrderEvent.builder()
                .orderNumber(event.orderNumber()).eventId(event.eventId()).eventType(OrderEventType.ORDER_DELIVERED).createdAt(event.createdAt()).payload(toJsonPayload(event)).build();
        orderEventRepository.save(orderEvent);

    }

    void save(OrderCancelledEvent event){
        OrderEvent orderEvent = OrderEvent.builder()
                .orderNumber(event.orderNumber()).eventId(event.eventId()).eventType(OrderEventType.ORDER_CANCELLED).createdAt(event.createdAt()).payload(toJsonPayload(event)).build();
        orderEventRepository.save(orderEvent);

    }

    void save(OrderErrorEvent event){
        OrderEvent orderEvent = OrderEvent.builder()
                .orderNumber(event.orderNumber()).eventId(event.eventId()).eventType(OrderEventType.ORDER_PROCESSING_FAILED).createdAt(event.createdAt()).payload(toJsonPayload(event)).build();
        orderEventRepository.save(orderEvent);

    }
    
    public void publishOrderEvents(){
        Sort sort = Sort.by("createdAt").ascending();
        List<OrderEvent> events = orderEventRepository.findAll(sort);
        log.info("Found {} Order events to be published", events.size());
        
        for(var event: events){
            publishEvent(event);
            orderEventRepository.delete(event);
        }
    }

    private void publishEvent(OrderEvent event) {
        OrderEventType eventType = event.getEventType();
        switch (eventType) {
            case ORDER_CREATED:
                OrderCreatedEvent orderCreatedEvent = fromJsonPayload(event.getPayload(), OrderCreatedEvent.class);
                orderEventPublisher.publish(orderCreatedEvent);
                break;
            case ORDER_DELIVERED:
                OrderDeliveredEvent orderDeliveredEvent =
                        fromJsonPayload(event.getPayload(), OrderDeliveredEvent.class);
                orderEventPublisher.publish(orderDeliveredEvent);
                break;
            case ORDER_CANCELLED:
                OrderCancelledEvent orderCancelledEvent =
                        fromJsonPayload(event.getPayload(), OrderCancelledEvent.class);
                orderEventPublisher.publish(orderCancelledEvent);
                break;
            case ORDER_PROCESSING_FAILED:
                OrderErrorEvent orderErrorEvent = fromJsonPayload(event.getPayload(), OrderErrorEvent.class);
                orderEventPublisher.publish(orderErrorEvent);
                break;
            default:
                log.warn("Unsupported OrderEventType: {}", eventType);
        }
    }

    private String toJsonPayload(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    private <T> T fromJsonPayload(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }




}
