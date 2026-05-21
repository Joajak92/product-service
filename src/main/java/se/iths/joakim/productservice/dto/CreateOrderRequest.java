package se.iths.joakim.productservice.dto;

import java.util.List;

public record CreateOrderRequest(
        List<ProductStockRequest> items
) {
}
