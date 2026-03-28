package dev.silvercapes.catalogservice.repository;

import dev.silvercapes.catalogservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductByCode(String code);
}
