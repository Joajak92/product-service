package se.iths.joakim.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductResponseDto;
import se.iths.joakim.productservice.dto.ProductStockRequest;
import se.iths.joakim.productservice.exception.NotEnoughStockException;
import se.iths.joakim.productservice.exception.ProductNotFoundException;
import se.iths.joakim.productservice.mapper.ProductMapper;
import se.iths.joakim.productservice.model.Product;
import se.iths.joakim.productservice.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Hanterar produktrelaterade operationer, inklusive hämtning,
 * skapande, borttagning och lageruppdatering.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * Hämtar alla produkter.
     *
     * @return en lista med produkter som DTO-objekt
     */
    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    /**
     * Hämtar en produkt baserat på dess id.
     *
     * @param id produktens id
     * @return den efterfrågade produkten
     * @throws ProductNotFoundException om produkten inte finns
     */
    public ProductResponseDto findById(Long id) {
        Product product = getProduct(id);

        ProductResponseDto productResponseDto = productMapper.toDto(product);
        return productResponseDto;
    }

    /**
     * Hämtar den fullständiga produktentiteten baserat på dess id.
     *
     * @param id produktens id
     * @return den efterfrågade produktentiteten
     * @throws ProductNotFoundException om produkten inte finns
     */
    public Product getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return product;
    }

    /**
     * Skapar och sparar en ny produkt.
     *
     * @param productRequestDto data för den nya produkten
     * @return den skapade produkten
     */
    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = productMapper.toEntity(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    /**
     * Tar bort en produkt baserat på dess id.
     *
     * @param id id för produkten som ska tas bort
     * @throws ProductNotFoundException om produkten inte finns
     */
    public void delete(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    /**
     * Minskar lagersaldot för de angivna produkterna.
     * Operationen körs transaktionellt så att lagerändringar hanteras atomärt.
     *
     * @param requestedProducts produkter och kvantiteter som ska dras från lagret
     * @return de uppdaterade produkterna
     * @throws ProductNotFoundException om någon produkt inte finns
     * @throws NotEnoughStockException  om lagersaldot inte räcker
     */
    @Transactional
    public List<ProductResponseDto> decreaseStock(
            List<ProductStockRequest> requestedProducts) {
        List<ProductResponseDto> result = new ArrayList<>();

        if (requestedProducts == null || requestedProducts.isEmpty()) {
            return result;
        }

        for (ProductStockRequest request : requestedProducts) {
            Long productId = request.productId();
            int quantity = request.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            int currentStock = product.getStock();
            int newStock = currentStock - quantity;

            if (newStock < 0) {
                throw new NotEnoughStockException("Not enough stock for product id: " + productId);
            }

            product.setStock(newStock);
            productRepository.save(product);

            ProductResponseDto responseDto = productMapper.toDto(product);
            result.add(responseDto);
        }
        return result;
    }
}
