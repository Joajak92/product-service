package se.iths.joakim.productservice.dto;

/**
 * Anger hur många exemplar av en produkt som ska påverka lagersaldot.
 *
 * @param productId produktens id
 * @param quantity  antal exemplar
 */
public record ProductStockRequest(
        Long productId,
        int quantity
) {
}
