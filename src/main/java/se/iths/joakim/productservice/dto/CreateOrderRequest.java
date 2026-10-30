package se.iths.joakim.productservice.dto;

import java.util.List;

/**
 * Förfrågan som innehåller produkter för en order.
 *
 * @param items produkter som ingår i ordern
 */
public record CreateOrderRequest(
        List<ProductStockRequest> items
) {
}
