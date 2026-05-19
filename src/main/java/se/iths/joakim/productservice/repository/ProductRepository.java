package se.iths.joakim.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.joakim.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
