package se.iths.joakim.productservice.dto;

public record ProductStockRequest(
        Long productId,
        int quantity
) {
}
