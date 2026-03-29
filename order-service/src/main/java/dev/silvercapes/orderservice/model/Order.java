package dev.silvercapes.orderservice.model;

import dev.silvercapes.orderservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_generator")
    @SequenceGenerator(name = "order_id_generator", sequenceName = "order_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(name ="username", nullable = false)
    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> items;

    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "name", column = @Column(name = "customer_name")),
                    @AttributeOverride(name = "phone", column = @Column(name = "customer_phone")),
                    @AttributeOverride(name = "email", column = @Column(name = "customer_email")),
            }) private Customer customer;


    @Embedded
    @AttributeOverrides(
            value = {
                    @AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1")),
                    @AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
                    @AttributeOverride(name = "city", column = @Column(name = "delivery_address_city")),
                    @AttributeOverride(name = "state", column = @Column(name = "delivery_address_state")),
                    @AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code")),
                    @AttributeOverride(name = "country", column = @Column(name = "delivery_address_country")),
            }) private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String comments;

    @Column(name ="created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
