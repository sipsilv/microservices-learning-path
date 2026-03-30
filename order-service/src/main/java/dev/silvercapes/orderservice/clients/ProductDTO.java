package dev.silvercapes.orderservice.clients;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String code;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
}
