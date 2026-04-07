package dev.silvercapes.orderservice.publishers;
import dev.silvercapes.orderservice.events.OrderCancelledEvent;
import dev.silvercapes.orderservice.events.OrderCreatedEvent;
import dev.silvercapes.orderservice.events.OrderDeliveredEvent;
import dev.silvercapes.orderservice.events.OrderErrorEvent;
import dev.silvercapes.orderservice.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    public void publish(OrderCreatedEvent event){
        this.send(applicationProperties.newOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event){
        this.send(applicationProperties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event){
        this.send(applicationProperties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event){
        this.send(applicationProperties.deliveredOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(), routingKey, payload);
    }
}
