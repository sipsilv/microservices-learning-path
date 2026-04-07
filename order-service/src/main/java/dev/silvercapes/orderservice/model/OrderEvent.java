package dev.silvercapes.orderservice.model;

import dev.silvercapes.orderservice.enums.OrderEventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_events")
public class OrderEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_event_id_generator")
    @SequenceGenerator(name = "order_event_id_generator", sequenceName =
    "order_event_id_seq")
    private Long id;

    @Column(nullable = false, name = "order_number")
    private String orderNumber;

    @Column(nullable = false, unique = true, name = "event_id")
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private OrderEventType eventType;

    @Column(nullable = false)
    private String payload;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
