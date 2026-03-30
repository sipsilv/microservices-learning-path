package dev.silvercapes.orderservice.clients;

import dev.silvercapes.orderservice.properties.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class CatalogServiceClientConfig {
    @Bean
    RestClient restClient(ApplicationProperties applicationProperties){
        return RestClient.builder()
                .baseUrl(applicationProperties.catalogServiceUrl()).build();
    }
}
