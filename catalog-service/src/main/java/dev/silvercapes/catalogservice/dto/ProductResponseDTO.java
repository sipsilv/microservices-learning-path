package dev.silvercapes.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private String code;
    private String name;
    private String description;

    @JsonProperty("image_url")
    private String imageUrl;
    private double price;
}
