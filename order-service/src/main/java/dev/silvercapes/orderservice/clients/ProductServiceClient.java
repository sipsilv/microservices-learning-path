package dev.silvercapes.orderservice.clients;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);
    private final RestClient restclient;

    public Optional<ProductDTO> getProductByCode(String code){
        log.info("Fetching product for code : {}", code);
        var product = restclient.get()
                .uri("/api/products/{code}", code)
                .retrieve()
                .body(ProductDTO.class);
        return Optional.ofNullable(product);
    }
}
