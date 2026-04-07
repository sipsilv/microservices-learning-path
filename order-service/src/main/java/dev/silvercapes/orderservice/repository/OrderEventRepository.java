package dev.silvercapes.orderservice.repository;

import dev.silvercapes.orderservice.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
}
