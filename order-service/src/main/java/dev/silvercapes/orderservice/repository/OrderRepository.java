package dev.silvercapes.orderservice.repository;
import dev.silvercapes.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
