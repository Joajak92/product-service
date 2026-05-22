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
        List<ProductResponseDto> result = new ArrayList<>();
        if (requestedProducts == null || requestedProducts.isEmpty()) {
            return result;
        }

        for (ProductStockRequest request : requestedProducts) {
            Long productId = request.productId();
            int quantity = request.quantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            int currentStock;
            currentStock = product.getStock() == null ? 0 : Integer.parseInt(product.getStock());
            int newStock = currentStock - quantity;

            if (newStock < 0) {
                throw new NotEnoughStockException("Not enough stock for product id: " + productId);
            }

            product.setStock(String.valueOf(newStock));
            productRepository.save(product);

            ProductResponseDto responseDto = productMapper.toDto(product);
            result.add(responseDto);
        }
        return result;
    }
}
