package dev.silvercapes.catalogservice.repository;


import dev.silvercapes.catalogservice.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
                "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
                "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db",
        }
)

@Sql("/test-data.sql")
class ProductRepositoryTest {
        @Autowired
        private ProductRepository productRepository;

        @Test
        void shouldGetAllProducts(){
                List<Product> products = productRepository.findAll();
                assertThat(products).hasSize(15);
        }

        @Test
        void shouldGetProductByCode(){
                Product product = productRepository.findProductByCode("P100").orElseThrow();
                assertThat(product.getCode()).isEqualTo("P100");
                assertThat(product.getName()).isEqualTo("The Hunger Games");

        }

        @Test
        void shouldReturnEmptyWhenProductCodeNotFound(){
                assertThat(productRepository.findProductByCode("invalid")).isEmpty();
        }

}