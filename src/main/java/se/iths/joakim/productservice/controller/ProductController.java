package se.iths.joakim.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductResponseDto;
import se.iths.joakim.productservice.dto.ProductStockRequest;
import se.iths.joakim.productservice.service.ProductService;

import java.util.List;

/**
 * REST-controller för produktrelaterade API-anrop.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Hämtar alla produkter.
     *
     * @return en lista av produkter
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    /**
     * Hämtar en produkt baserat på dess id.
     *
     * @param id produktens id
     * @return en produkt
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    /**
     * Skapar en ny produkt.
     *
     * @param productRequestDto data för den nya produkten
     * @return den skapade produkten
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productRequestDto));
    }

    /**
     * Tar bort en produkt baserat på dess id.
     *
     * @param id id för produkten som ska tas bort
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Minskar lagersaldot vid beställning som skickas från order-service.
     *
     * @param items produkter och kvantiteter som ska dras från lagret
     */
    @PostMapping("/stock/decrease")
    public ResponseEntity<List<ProductResponseDto>> decreaseStock(
            @RequestBody List<ProductStockRequest> items) {
        return ResponseEntity.ok(productService.decreaseStock(items));
    }
}
