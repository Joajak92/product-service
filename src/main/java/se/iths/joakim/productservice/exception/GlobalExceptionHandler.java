package se.iths.joakim.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Hanterar undantag som uppstår i produkt-service controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Omvandlar ett saknat produktundantag till ett HTTP 404-svar.
     *
     * @param ex undantaget som innehåller felinformationen
     * @return HTTP 404 med felmeddelandet
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Omvandlar ett otillräckligt lagerundantag till ett HTTP 400-svar.
     *
     * @param ex undantaget som innehåller felinformationen
     * @return HTTP 400 med felmeddelandet
     */
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<String> handleNotEnoughStockException(NotEnoughStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
