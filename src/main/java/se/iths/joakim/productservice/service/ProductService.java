package se.iths.joakim.productservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.iths.joakim.productservice.dto.ProductRequestDto;
import se.iths.joakim.productservice.dto.ProductResponseDto;
import se.iths.joakim.productservice.exception.ProductNotFoundException;
import se.iths.joakim.productservice.mapper.ProductMapper;
import se.iths.joakim.productservice.model.Product;
import se.iths.joakim.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponseDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).toList();
    }

    public ProductResponseDto findById(Long id) {
        Product product = getProduct(id);

        ProductResponseDto productResponseDto = productMapper.toDto(product);
        return productResponseDto;
    }

    public Product getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return product;
    }

    public ProductResponseDto create(ProductRequestDto productRequestDto) {
        Product product = productMapper.toEntity(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public void delete(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
    }

    @Transactional
    public List<ProductResponseDto> decreaseStock(
            List<ProductStockRequest> requestedProducts) {
// hämta alla produkter
// kontrollera att varje produkt verkligen finns och att stock räcker
// kasta exception om inte en produkt finns eller stock inte räcker
// minska stock
// returnera produktinfo
    }
}
