package se.iths.joakim.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data som krävs för att skapa en produkt.
 *
 * @param name        produktens namn
 * @param description produktens beskrivning
 * @param price       produktens pris
 * @param stock       produktens initiala lagersaldo
 * @param category    produktens kategori
 * @param imgUrl      URL till produktens bild
 */
public record ProductRequestDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Price is required")
        Double price,
        int stock,
        String category,
        String imgUrl
) {
}
