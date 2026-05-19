package se.iths.joakim.productservice;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with id: " + id + " not found");
    }
}
