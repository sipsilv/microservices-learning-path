package dev.silvercapes.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq")
    private Long id;

    @NotEmpty(message = "Product code is required")
    @Column(nullable = false, unique = true)
    private String code;

    @NotEmpty(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    private String description;

    private String imageUrl;

    @NotNull(message = "Product price is required")
    @DecimalMin("0.1")
    @Column(nullable = false)
    private BigDecimal price;

}