package dev.silvercapes.catalogservice.repository;

import dev.silvercapes.catalogservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
