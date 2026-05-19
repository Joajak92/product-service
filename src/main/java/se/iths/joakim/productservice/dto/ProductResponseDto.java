package se.iths.joakim.productservice.dto;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        Double price,
        String stock
) {
}
