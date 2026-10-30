package se.iths.joakim.productservice.dto;

/**
 * Produktdata som returneras från API:et.
 *
 * @param id          produktens id
 * @param name        produktens namn
 * @param description produktens beskrivning
 * @param price       produktens pris
 * @param stock       aktuellt lagersaldo
 * @param category    produktens kategori
 * @param imgUrl      URL till produktens bild
 */
public record ProductResponseDto(
        Long id,
        String name,
        String description,
        Double price,
        int stock,
        String category,
        String imgUrl
) {
}
