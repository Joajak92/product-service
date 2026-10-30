package se.iths.joakim.productservice.exception;

/**
 * Anger att lagersaldot inte räcker för den begärda operationen.
 */
public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
