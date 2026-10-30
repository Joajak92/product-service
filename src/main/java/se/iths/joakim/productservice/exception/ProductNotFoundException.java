package se.iths.joakim.productservice.exception;

/**
 * Anger att en efterfrågad produkt inte kunde hittas.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product with id: " + id + " not found");
    }
}
